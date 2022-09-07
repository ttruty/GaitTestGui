package models;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;

public class CheckMarkerFiles {
	@SuppressWarnings("null")
	public List<File> listFilesForFolder(final File folder) {
		List<File> listOfFiles = new ArrayList<File>();
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.getName().toLowerCase().endsWith(".csv")) {
	            listOfFiles.add(fileEntry);
	        }
	    }
	    return listOfFiles;
	}
	
	public List<MarkerFile> readMarkerDirectory() {
		File studyDir = new File("C:\\studies\\dynaport");
		List<MarkerFile> markerFiles = new ArrayList<MarkerFile>();

		String proj_id = "";
		String date = "";
		String fu_year = "";
		List<File> markerfiles = listFilesForFolder(studyDir);
		for (File file : markerfiles) 
		{
			System.out.println(file.getName());
			String[] filename = file.getName().split("_");
			MarkerFile mf = new MarkerFile(filename[0], filename[1], filename[2], file);
	        markerFiles.add(mf);
		} 
		return markerFiles;
	}
	
	private boolean containsName(final List<MarkerFileData> list, final String name){
	    return list.stream().anyMatch(o -> o.getName().equals(name));
	}
	
	public List<MarkerFileData> readDataFromMarkerFile(File file) {
		List<MarkerFileData> markerFileDataList = new ArrayList<MarkerFileData>();
		try {
	        FileReader filereader = new FileReader(file);
	        CSVReader csvReader = new CSVReaderBuilder(filereader)
	        		.withSkipLines(8)
                    .build();
	        
	        List<String[]> allData = csvReader.readAll();
	        
	        int rowCount = allData.size();
	        if (rowCount % 2 != 0) {
	        	rowCount = rowCount - 1;
	        }
	        
	        // print Data
	        for (int row = 0; row < rowCount - 2; row++) {
	        	if (row % 2 == 0) {
	        		int repeat = 0;
	        		String trialName = allData.get(row)[0];
	        		Long startTime =  Long.parseLong(allData.get(row)[2]);
	        		Long endTime = Long.parseLong(allData.get(row + 1)[2]);
	        		if (containsName(markerFileDataList, trialName)) {
	        			repeat += 1;
	        		}
	        		MarkerFileData mfd = new MarkerFileData(trialName, repeat, startTime, endTime);
	        		markerFileDataList.add(mfd);	        		
	        	}
	        }
	        
		} catch (IOException | CsvException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return markerFileDataList;
	}
	
	
}
