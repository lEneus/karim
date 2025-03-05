package poo.project;


//all of these for time
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Scanner;

import javax.swing.SwingUtilities;

public class main {

	public static void main(String[] args)  {
		boolean run = true;
		LinkedList<Project> myprojects = new LinkedList<Project>();
		
		
	    SwingUtilities.invokeLater(() -> new ProjectGUI(new Project("My Project")));

		
		
	}
}
