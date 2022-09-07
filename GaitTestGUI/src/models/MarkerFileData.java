package models;

public class MarkerFileData {
	String name;
	int repeat;
	Long startTime;
	Long endTime;
	Long duration;
	
	
	public MarkerFileData(String name, int repeat, Long startTime, Long endTime) {
		super();
		this.name = name;
		this.repeat = repeat;
		this.startTime = startTime;
		this.endTime = endTime;
		this.duration = endTime - startTime;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getTrial() {
		return repeat;
	}
	public void setTrial(int trial) {
		this.repeat = trial;
	}
	public Long getStartTime() {
		return startTime;
	}
	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}
	public Long getEndTime() {
		return endTime;
	}
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}
	public Long getDuration() {
		return duration;
	}
	public void setDuration(Long duration) {
		this.duration = duration;
	}

	@Override
	public String toString() {
		return "MarkerFileData [name=" + name + ", trial=" + repeat + ", startTime=" + startTime + ", endTime=" + endTime
				+ ", duration=" + duration + "]";
	}
	
	


}
