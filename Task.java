package poo.project;
import java.time.*;
import java.time.Duration;
import java.time.temporal.ChronoUnit;


public class Task {
	private String name;
	private LocalDate start_date,end_date;
	private String statue;
	private String assignedto;
	
	public Task(String name,LocalDate start,LocalDate end , String statue , String assignedto) {
		this.name = name;
		start_date = start;
		end_date = end;
		
		this.statue = statue;
		this.assignedto = assignedto;
		
	}
	public Task() {
		
	}
	
	public void setstatue(String newstatue) {
		this.statue = newstatue;
	}
	public long calculateduration(LocalDate passedtime) {
			LocalDate current = LocalDate.now();
			
			long numofdays =  ChronoUnit.DAYS.between(current, passedtime);
			
		
		return numofdays;
	}
	public void settaskname(String name) {
		this.name = name;
	}
	
	public void settask(String assignedto) {
		this.assignedto = assignedto;
		
	}
	
	public String getname() {
		return name;
	}
	
	public String getstatue() {
		return statue;
	}
	public String getassignedperson() {
		return assignedto;
	}
	
	public LocalDate getstartdate() {
		return start_date;
	}
	public LocalDate getenddate() {
		return end_date;
	}
	
	
	public void setstartdate(LocalDate start) {
		start_date = start;
	}
	
	public void setenddate(LocalDate end) {
		end_date = end;
	}
	public void displaytask() {
		System.out.println("information is " + name + " " + statue + " " + start_date + " " + end_date + " " + assignedto + "");
	}
	
	
}
