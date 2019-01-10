/**
 * Model for writing the output file
 * 
 * @author Tim Truty
 *
 */
package models;


import java.io.*;
import java.lang.String;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.filechooser.FileSystemView;

public class WriteCSV {
	String filename;
	String baseFilename;
	
    public void write() throws IOException {

    	DateFormat saveCsvFormat = new SimpleDateFormat( "yyyyMMdd_HHmmss" );
    	final long CsvDate = System.currentTimeMillis();
    	setBaseFilename(Recording.getRecordingId() + "_"+ Recording.getFuYear() + "_"+ saveCsvFormat.format( CsvDate ));
    	
    	
    	String docDir = FileSystemView.getFileSystemView().getDefaultDirectory().getPath() ;
		String saveDir = docDir.concat("/GaitFiles");
		File directory = new File(saveDir);
	    if (! directory.exists()){
	        directory.mkdir();
	    }
	    
	    filename =  getBaseFilename() + ".csv"; // returns "txt"
	    File saveFile = new File(saveDir + "/" + filename);

        
        //Delimiter used in CSV file
        final String COMMA_DELIMITER = ",";
        final String NEW_LINE_SEPARATOR = "\n";

        //CSV file header
        final String FILE_HEADER = "Perf_Label, timeStamp, UnixTimeStamp, count, markerType, delay";
        System.out.println("Print to csv");


        FileWriter fileWriter = null;
        try {

        	DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        	LocalDateTime time = LocalDateTime.now();        	       	    
	        String timeString = time.format(timeFormat);
       	    
            fileWriter = new FileWriter(saveFile);
            //Write the CSV file header
            fileWriter.append("PROJID");
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append("'" + Recording.getRecordingId()); // need ' to not trim out leading zeros
            fileWriter.append(NEW_LINE_SEPARATOR);
            
            fileWriter.append("Filename");
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(filename);
            fileWriter.append(NEW_LINE_SEPARATOR);
            
            fileWriter.append("Recording Started");
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(String.valueOf(Recording.getUnixRecordingTimeStamp()));
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(timeFormat.format(Recording.getRecordingStartTimeStamp()));
            fileWriter.append(NEW_LINE_SEPARATOR);
            
            fileWriter.append("Recording Stopped");
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(String.valueOf(Recording.getReconnectTime()));
            fileWriter.append(NEW_LINE_SEPARATOR);
            
            fileWriter.append("CSV Save TIme");
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append( timeString );
            fileWriter.append(NEW_LINE_SEPARATOR);
            
            fileWriter.append("Start Button Pressed");
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append( String.valueOf(Recording.getStartButtonPressed()));
            

            fileWriter.append(NEW_LINE_SEPARATOR);
            fileWriter.append(NEW_LINE_SEPARATOR);
            
            fileWriter.append(FILE_HEADER.toString());

            //Add a new line separator after the header

            fileWriter.append(NEW_LINE_SEPARATOR);

            //Write a new student object list to the CSV file
            //long end = 0;
            for (Marker marker : Recording.getMarkerList())
            {
                
                fileWriter.append(marker.getLabel()); //stroke number
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(marker.getTimeStamp()));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(marker.getUnixTimeStamp()));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(marker.getCount()));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(marker.getMarkerType()));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(marker.getRandomDelay()));
                fileWriter.append(NEW_LINE_SEPARATOR);
             }

           
            fileWriter.append(NEW_LINE_SEPARATOR);
            fileWriter.append(NEW_LINE_SEPARATOR);

            System.out.println("CSV file was created successfully !!!");
        } catch (Exception e) {

            System.out.println("Error in CsvFileWriter !!!");

            e.printStackTrace();

        } finally {

            try {

                fileWriter.flush();

                fileWriter.close();

            } catch (IOException e) {

                System.out.println("Error while flushing/closing fileWriter !!!");

                e.printStackTrace();

            }

        }

     }

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getBaseFilename() {
		return baseFilename;
	}

	public void setBaseFilename(String baseFilename) {
		this.baseFilename = baseFilename;
	}
    
    
}