package models;

import java.io.File;

public class MarkerFile {
	private String proj_id;
	private String fu_year;
	private String date;
	private File file;
	
	public MarkerFile() {}
	
	public MarkerFile(String proj_id, String fu_year, String date, File file) {
		this.proj_id = proj_id;
		this.fu_year = fu_year;
		this.date = date;
		this.file = file;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getProj_id() {
		return proj_id;
	}

	public void setProj_id(String proj_id) {
		this.proj_id = proj_id;
	}

	public String getFu_year() {
		return fu_year;
	}

	public void setFu_year(String fu_year) {
		this.fu_year = fu_year;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
} 
