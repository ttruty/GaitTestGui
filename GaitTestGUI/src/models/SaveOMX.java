/**
 * Data model for saved file object
 * 
 * @author Tim Truty
 *
 */
package models;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.io.FileUtils;
import javax.swing.filechooser.FileSystemView;

public class SaveOMX {
	
	private String saveDriveName;
	private String saveFileName;
	private String rawOMXFileName;
	private String saveDriveLetter;
	
	public String find(File dir) 
    {
        String pattern = ".OMX";

        File listFile[] = dir.listFiles();
        if (listFile != null) 
        {
            for (int i=0; i<listFile.length; i++) 
            {
                if (listFile[i].isDirectory()) 
                {
                    find(listFile[i]);
                } else 
                { 
                    if (listFile[i].getName().endsWith(pattern)) 
                    {
                    	System.out.println("FOUND FILE:");
                        System.out.println(listFile[i].getPath());
                        return listFile[i].getPath();
                    }
                }
            }
        }
        return null;
    }
	
	public File fileSearch(String drive)
	{
		if(drive != null && !drive.isEmpty()) {
        // look for a file in that drive
			System.out.println("Searching Drive: " + drive);
			
			return new File(find(new File(drive+":")));
		}
		return null;
    }
	
	public void saveFile(File file) throws NoSuchAlgorithmException, IOException {
		
		String docDir = FileSystemView.getFileSystemView().getDefaultDirectory().getPath() ;
		String saveDir = docDir.concat("/GaitFiles");
		File directory = new File(saveDir);
	    if (! directory.exists()){
	        directory.mkdir();
	    }
	    
	    
	    File saveFile = new File(saveDir + "/" + getSaveFileName());
	    
	    try{
	    	FileUtils.copyFile(file, saveFile);
	    }
	    catch (IOException e){
	        e.printStackTrace();
	    }
		System.out.println("Saved: " +  file.toString() + "to: " +saveFile.toString());
		
		// check file with 
		if (CheckFiles.isIdentical(file.getAbsolutePath(), saveFile.getAbsolutePath())) {
			System.out.println("File Copy passed MD5 Checksum test");
			Recording.setSaved(true);
			Recording.setRecordingState(false);
		}
		else {
			System.out.println("File did not copy correctly try to copy again");
			saveFile(file);
		}
	}

	public String getSaveDriveLetter() {
		return saveDriveLetter;
	}

	public void setSaveDriveLetter(String saveDriveLetter) {
		 this.saveDriveLetter = saveDriveLetter;
	}

	public String getSaveDriveName() {
		return saveDriveName;
	}

	public void setSaveDriveName(String saveDriveName) {
		this.saveDriveName = saveDriveName;
	}

	public String getSaveFileName() {
		return saveFileName;
	}

	public void setSaveFileName(String saveFileName) {
		this.saveFileName = saveFileName;
	}

	public String getRawOMXFileName() {
		return rawOMXFileName;
	}

	public void setRawOMXFileName(String rawOMXFileName) {
		this.rawOMXFileName = rawOMXFileName;
	}
	
	
}
