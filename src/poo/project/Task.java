package poo.project;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

class Task implements Serializable {
    String title, description, status, assign;
    LocalDate startDate, endDate;

    private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Task(String title, String description, String startDate, String endDate, String status, String assign) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.assign = assign;

        // Convert input Strings to LocalDate
        this.startDate = LocalDate.parse(startDate, dateFormat);
        this.endDate = LocalDate.parse(endDate, dateFormat);
    }

    public String getStartDateAsString() {
        return startDate.format(dateFormat);
    }

    public String getEndDateAsString() {
        return endDate.format(dateFormat);
    }
    
    public String getDurationAsString() {
        long duration = ChronoUnit.DAYS.between(startDate, endDate);
        return duration + (duration == 1 ? " day" : " days");
    }
}
