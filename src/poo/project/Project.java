package poo.project;

import java.io.Serializable;
import java.util.ArrayList;

class Project implements Serializable {
    String title, description;
    ArrayList<Task> tasks = new ArrayList<>();
    
    public Project(String title, String description) {
        this.title = title;
        this.description = description;
    }
    
    void addTask(Task task) { tasks.add(task); }
}

