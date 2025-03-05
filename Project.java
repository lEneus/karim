package poo.project;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Date;

public class Project{
	private static final long serialVersionUID = 1L;
	private String projectname;
	private Date projectstart,projectend;
	private int numberoftasks = 0;
	
	private LinkedList<Task> mytasks = new LinkedList<Task>();
	
	public Project(String name) {
	   this.projectname = name;
	}
	
	public void addtask(Task newtask) {
		
		boolean add;
		add = false;
		add = mytasks.add(newtask);
		if(add == true) {
			System.out.println("task has been added successfully");
			numberoftasks ++;
		}
	}
	public void deletetask(Task thistask) {
		boolean remove = false;
		remove = mytasks.remove(thistask);
		if(remove == true) {
			numberoftasks --;
			System.out.println("task has been removed successfully");
		}
	}
	public void displaytasks() {
		Task temp;
		
		System.out.println("Name   | start Date |  End Date  |  Statue  | Assigned to");
		for(int i = 0 ; i < numberoftasks ; i++) {
			temp = mytasks.get(i);
			
			System.out.println(temp.getname() + "  " + temp.getstatue()+"   " + temp.getenddate() + "  "+ temp.getstatue() + "  " + temp.getassignedperson() + " ");
		
		}
		
		
	}
	
	
	public void savetoafile() {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(projectname + ".dat"))) {
            oos.writeObject(this);
            System.out.println("Project saved successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
		
	}

	
	public void loadfromafile(String string) {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(projectname))) {
            Project loadedProject = (Project) ois.readObject();
            this.projectname = loadedProject.projectname;
            this.mytasks = loadedProject.mytasks;
            System.out.println("Project loaded successfully!");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
	}
	

    public String getProjectName() {
        return projectname;
    }

    public LinkedList<Task> getTasks() {
        return mytasks;
    }
	
	
	
}
