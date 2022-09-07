package models;

import java.io.File;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MarkerFilerChecks {
	
	private StringProperty markerIdLabelProperty;
	private StringProperty markerFuYearProperty;
	private StringProperty markerDateProperty;
	private File file;

	public MarkerFilerChecks(String idLabel, String fuYearLabel, String dateLabel, File file) {
	    this.markerIdLabelProperty = new SimpleStringProperty(idLabel);
	    this.markerFuYearProperty = new SimpleStringProperty(fuYearLabel);
	    this.markerDateProperty = new SimpleStringProperty(dateLabel);
	    this.file = file;
	}

    public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public void setIDLabel( String idLabel ) {
        this.markerIdLabelProperty.set( idLabel );
    }

    public String getIDLabel() {
        return this.markerIdLabelProperty.get();
    }

    public StringProperty idLabelProperty() {
        return this.markerIdLabelProperty;
    }
    
    public void setFUYearLabel( String fuYear ) {
        this.markerFuYearProperty.set( fuYear );
    }

    public String getFUYearLabel() {
        return this.markerFuYearProperty.get();
    }

    public StringProperty fuYearProperty() {
        return this.markerFuYearProperty;
    }
    
    public void setDateLabel( String dateLabel ) {
        this.markerDateProperty.set( dateLabel );
    }

    public String getDateLabe() {
        return this.markerDateProperty.get();
    }

    public StringProperty dateLabelProperty() {
        return this.markerDateProperty;
    }
}