package poo.project;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import java.io.*;
import java.text.SimpleDateFormat;
import java.awt.*;
import java.awt.event.*;
import java.awt.im.InputContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class ProjectManagerApp {
    private JFrame frame;
    private JTable table;
    private DefaultTableModel model;
    private DefaultListModel<String> projectListModel;
    private JList<String> projectList;
    private ArrayList<Project> projects;
    private Project currentProject;
    private JLabel projectTitleLabel, projectDescriptionLabel;

    public ProjectManagerApp() {
    	try {
    	    UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel"); // Modern theme
    	} catch (Exception e) {
    	    e.printStackTrace();
    	}

    	
        projects = new ArrayList<>();
        frame = new JFrame("Project Management");
        frame.setSize(800, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Project List (West Side)
        projectListModel = new DefaultListModel<>();
        projectList = new JList<>(projectListModel);
        projectList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        projectList.addListSelectionListener(e -> openSelectedProject());
        JScrollPane projectScrollPane = new JScrollPane(projectList);
        projectScrollPane.setPreferredSize(new Dimension(200, 0));

        JButton addProjectButton = new JButton("Add Project");
        addProjectButton.setIcon(getScaledIcon("C:\\Users\\Admin\\eclipse-workspace\\realJavaProject\\src\\add.png", 20, 20));
        addProjectButton.setBackground(new Color(30, 144, 255));
        addProjectButton.setForeground(Color.WHITE);
        addProjectButton.setFont(new Font("Arial", Font.BOLD, 14));
        JButton editProjectButton = new JButton("Edit Project");
        editProjectButton.setBackground(new Color(255, 165, 0));
        editProjectButton.setForeground(Color.WHITE);
        editProjectButton.setFont(new Font("Arial", Font.BOLD, 14));
        editProjectButton.setIcon(getScaledIcon("C:\\Users\\Admin\\eclipse-workspace\\realJavaProject\\src\\edit.png", 20, 20));
        JButton deleteProjectButton = new JButton("Delete Project");
        deleteProjectButton.setBackground(new Color(220, 20, 60));  // Crimson Red
        deleteProjectButton.setForeground(Color.WHITE);
        deleteProjectButton.setFont(new Font("Arial", Font.BOLD, 14));
        deleteProjectButton.setIcon(getScaledIcon("C:\\Users\\Admin\\eclipse-workspace\\realJavaProject\\src\\delete.png", 20, 20));
        
        addProjectButton.addActionListener(e -> addProject());
        editProjectButton.addActionListener(e -> editProject());
        deleteProjectButton.addActionListener(e -> deleteProject());
        JPanel projectPanel = new JPanel(new BorderLayout());
        projectPanel.add(projectScrollPane, BorderLayout.CENTER);
        
        JPanel pbuttonPanel = new JPanel();
        pbuttonPanel.add(addProjectButton);
        pbuttonPanel.add(editProjectButton);
        pbuttonPanel.add(deleteProjectButton);
        
        projectPanel.add(pbuttonPanel, BorderLayout.SOUTH);

        // Project Details (North Side)
        JPanel projectInfoPanel = new JPanel(new GridLayout(4, 1));
        projectTitleLabel = new JLabel("Project Name: ");
        projectDescriptionLabel = new JLabel("Description: ");
        projectInfoPanel.add(projectTitleLabel);
        projectInfoPanel.add(projectDescriptionLabel);

        // Task Table (Center)
        model = new DefaultTableModel(new String[]{"Title", "Start Date", "End Date","Duration", "Status", "Assign To"}, 0);
        table = new JTable(model);
        table.getColumnModel().getColumn(4).setCellRenderer(new StatusCellRenderer());
        table.setRowHeight(30);  // Increase row height
        table.setFont(new Font("Arial", Font.PLAIN, 14));  // Set font
        table.getTableHeader().setBackground(Color.DARK_GRAY);
        table.getTableHeader().setForeground(Color.BLACK);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));


        // Buttons (South)
        JButton addButton = new JButton("Add Task");
        addButton.setIcon(getScaledIcon("C:\\Users\\Admin\\eclipse-workspace\\realJavaProject\\src\\add.png", 20, 20));
        addButton.setBackground(new Color(30, 144, 255));
        addButton.setForeground(Color.WHITE);
        addButton.setFont(new Font("Arial", Font.BOLD, 14));
        JButton editButton = new JButton("Edit Task");
        editButton.setIcon(getScaledIcon("C:\\Users\\Admin\\eclipse-workspace\\realJavaProject\\src\\edit.png", 20, 20));
        editButton.setBackground(new Color(255, 165, 0));
        editButton.setForeground(Color.WHITE);
        editButton.setFont(new Font("Arial", Font.BOLD, 14));
        JButton deleteButton = new JButton("Delete Task");
        deleteButton.setIcon(getScaledIcon("C:\\Users\\Admin\\eclipse-workspace\\realJavaProject\\src\\delete.png", 20, 20));
        deleteButton.setBackground(new Color(220, 20, 60));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFont(new Font("Arial", Font.BOLD, 14));
        JButton saveButton = new JButton("Save Projects");
        saveButton.setIcon(getScaledIcon("C:\\Users\\Admin\\eclipse-workspace\\realJavaProject\\src\\save.png", 20, 20));
        saveButton.setFont(new Font("Arial", Font.BOLD, 14));
        JButton loadButton = new JButton("Load Projects");
        loadButton.setIcon(getScaledIcon("C:\\Users\\Admin\\eclipse-workspace\\realJavaProject\\src\\load.png", 20, 20));
        loadButton.setFont(new Font("Arial", Font.BOLD, 14));

        addButton.addActionListener(e -> addTask());
        editButton.addActionListener(e -> editTask());
        deleteButton.addActionListener(e -> deleteTask());
        saveButton.addActionListener(e -> saveProjects());
        loadButton.addActionListener(e -> loadProjects());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);
        
        projectPanel.setBackground(new Color(0x123456));
        projectInfoPanel.setBackground(new Color(0x415d78));
        buttonPanel.setBackground(new Color(0x123456));
        pbuttonPanel.setBackground(new Color(0x123456));

        frame.add(projectPanel, BorderLayout.WEST);
        frame.add(projectInfoPanel, BorderLayout.NORTH);
        frame.add(new JScrollPane(table), BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
    
    private ImageIcon getScaledIcon(String path, int width, int height) {
    	ImageIcon icon = new ImageIcon("C:\\Users\\Admin\\eclipse-workspace\\realJavaProject");
        Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }


    private void addProject() {
        JTextField nameField = new JTextField();
        JTextField descField = new JTextField();
        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("Project Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Description:"));
        panel.add(descField);
        
        
        
        int result = JOptionPane.showConfirmDialog(frame, panel, "Create New Project", JOptionPane.OK_CANCEL_OPTION);
        
        if (result == JOptionPane.OK_OPTION) {
        	if(nameField.getText().length() > 20) {
        		
        		JOptionPane.showMessageDialog(frame, "Please try a shorter name ", "Invalid Name", JOptionPane.ERROR_MESSAGE);
        		return;
        	}
            Project newProject = new Project(nameField.getText(), descField.getText());
            projects.add(newProject);
            projectListModel.addElement(newProject.title);
        }
    }

    /*private void openSelectedProject() {
        int index = projectList.getSelectedIndex();
        if (index >= 0) {
            currentProject = projects.get(index);
            projectTitleLabel.setText("Project Name: " + currentProject.title);
            projectDescriptionLabel.setText("Description: " + currentProject.description);
            loadProjectTasks();
        }
    }*/
    private void openSelectedProject() {
        int index = projectList.getSelectedIndex();
        if (index >= 0) {
            currentProject = projects.get(index);
            projectTitleLabel.setText("Project Name: " + currentProject.title);
            projectDescriptionLabel.setText("Description: " + currentProject.description);

            // Clear existing tasks and reload from the selected project
            model.setRowCount(0);
            for (Task task : currentProject.tasks) {
                model.addRow(new Object[]{task.title, task.getStartDateAsString(), task.getEndDateAsString(), task.getDurationAsString(), task.status, task.assign});
            }
        }else {
        	currentProject = null;
        }
    }

    
    private void addTask() {
    	if(currentProject == null) {
    		JOptionPane.showMessageDialog(frame, "Please chose a projct");
    		return;
    	}
        JTextField titleField = new JTextField();
        JDateChooser startDateChooser = new JDateChooser();
        JDateChooser endDateChooser = new JDateChooser();

        String[] statuses = {"Not Started"};
        JComboBox<String> statusBox = new JComboBox<>(statuses);
        
        String[] person = {"Anis", "Karim", "Djalel", "Brahim"};
        JComboBox<String> personBox = new JComboBox<>(person);

        JPanel panel = new JPanel(new GridLayout(8, 3));
        panel.add(new JLabel("Task Title:"));
        panel.add(titleField);
        panel.add(new JLabel("Start Date:"));
        panel.add(startDateChooser);
        panel.add(new JLabel("End Date:"));
        panel.add(endDateChooser);

        panel.add(new JLabel("Assign to:"));
        panel.add(personBox);
        panel.add(new JLabel("Status:"));
        panel.add(statusBox);

        int result = JOptionPane.showConfirmDialog(frame, panel, "Enter Task Details",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String title = titleField.getText();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String startDateStr = dateFormat.format(startDateChooser.getDate());
                String endDateStr = dateFormat.format(endDateChooser.getDate());
                String status = (String) statusBox.getSelectedItem();
                String assign = (String) personBox.getSelectedItem();
                LocalDate startDate = LocalDate.parse(startDateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                LocalDate endDate = LocalDate.parse(endDateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                
                if(title.length() > 15 ) {
                	JOptionPane.showMessageDialog(frame, "the entered title is too long");
                	return;
                }
                
                if (startDate.isAfter(endDate)) {
                    JOptionPane.showMessageDialog(frame, "Error: Start date cannot be after End date!", "Invalid Date", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                Task task = new Task(title, "", startDateStr, endDateStr, status, assign);
                currentProject.addTask(task);
                long duration = ChronoUnit.DAYS.between(startDate, endDate);
                String durationText = duration + (duration == 1 ? " day" : " days");
                model.addRow(new Object[]{title, task.getStartDateAsString(), task.getEndDateAsString(), durationText, status, assign});

            } catch (Exception e) {
                JOptionPane.showMessageDialog(frame, "Invalid date format! Use YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void editTask() {
        int selectedRow = table.getSelectedRow(); // Get selected task
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select a task to edit!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Get current task data
        String currentTitle = (String) model.getValueAt(selectedRow, 0);
        String currentStartDate = (String) model.getValueAt(selectedRow, 1);
        String currentEndDate = (String) model.getValueAt(selectedRow, 2);
        String currentStatus = (String) model.getValueAt(selectedRow, 3);
        String currentDuration = (String) model.getValueAt(selectedRow, 4);
        String currentAssign = (String) model.getValueAt(selectedRow, 5);

        // Input fields
        JTextField titleField = new JTextField(currentTitle);
        JDateChooser startDateChooser = new JDateChooser();
        JDateChooser endDateChooser = new JDateChooser();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            startDateChooser.setDate(dateFormat.parse(currentStartDate));
            endDateChooser.setDate(dateFormat.parse(currentEndDate));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        String[] statuses = {"Not Started", "In Progress", "Completed"};
        JComboBox<String> statusBox = new JComboBox<>(statuses);
        statusBox.setSelectedItem(currentStatus);
        
        String[] person = {"Anis", "Karim", "Djalel", "Brahim"};
        JComboBox<String> personBox = new JComboBox<>(person);
        personBox.setSelectedItem(currentAssign);

        JPanel panel = new JPanel(new GridLayout(5, 2));
        panel.add(new JLabel("Task Title:"));
        panel.add(titleField);
        panel.add(new JLabel("Start Date:"));
        panel.add(startDateChooser);
        panel.add(new JLabel("End Date:"));
        panel.add(endDateChooser);
        panel.add(new JLabel("Assign to:"));
        panel.add(personBox);
        panel.add(new JLabel("Status:"));
        panel.add(statusBox);

        int result = JOptionPane.showConfirmDialog(frame, panel, "Edit Task Details",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String newTitle = titleField.getText();
                String newStartDateStr = dateFormat.format(startDateChooser.getDate());
                String newEndDateStr = dateFormat.format(endDateChooser.getDate());
                LocalDate newStartDate = LocalDate.parse(newStartDateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                LocalDate newEndDate = LocalDate.parse(newEndDateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String newStatus = (String) statusBox.getSelectedItem();
                String newAssign = (String) personBox.getSelectedItem();
                
                if(newTitle.length() > 15 ) {
                	JOptionPane.showMessageDialog(frame, "the entered title is too long");
                	return;
                }
                
                if (newStartDate.isAfter(newEndDate)) {
                    JOptionPane.showMessageDialog(frame, "Error: Start date cannot be after End date!", "Invalid Date", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (selectedRow > 0) {
                    String prevTaskStatus = (String) model.getValueAt(selectedRow - 1, 4);  // Get previous task status
                    if (!prevTaskStatus.equals("Completed") && !newStatus.equals("Not Started")) {
                        JOptionPane.showMessageDialog(frame, "Error: The previous task must be completed first!", "Dependency Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                // Update task object in project
                Task task = currentProject.tasks.get(selectedRow);
                task.title = newTitle;
                task.startDate = newStartDate;
                task.endDate = newEndDate;
                task.status = newStatus;
                task.assign = newAssign;

                // Update table row
                model.setValueAt(newTitle, selectedRow, 0);
                model.setValueAt(task.getStartDateAsString(), selectedRow, 1);
                model.setValueAt(task.getEndDateAsString(), selectedRow, 2);
                long duration = ChronoUnit.DAYS.between(newStartDate, newEndDate);
                String durationText = duration + (duration == 1 ? " day" : " days");
                model.setValueAt(durationText, selectedRow, 3);
                model.setValueAt(newStatus, selectedRow, 4);
                model.setValueAt(newAssign, selectedRow, 5);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(frame, "Invalid date format! Use YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void deleteTask() {
        int selectedRow = table.getSelectedRow(); // Get selected row
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select a task to delete!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete this task?",
                "Confirm Deletion", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            currentProject.tasks.remove(selectedRow); // Remove from list
            model.removeRow(selectedRow); // Remove from table
        }
    }

    
    private void loadProjectTasks() {
        model.setRowCount(0);
        for (Task task : currentProject.tasks) {
        	model.addRow(new Object[]{task.title, task.getStartDateAsString(), task.getEndDateAsString(), task.getDurationAsString(), task.status, task.assign});
        }
        
    }
    
    private void editProject() {
        int index = projectList.getSelectedIndex();
        if (index >= 0) {
            Project project = projects.get(index);
            JTextField nameField = new JTextField(project.title);
            JTextField descField = new JTextField(project.description);
            JPanel panel = new JPanel(new GridLayout(2, 2));
            panel.add(new JLabel("Project Name:"));
            panel.add(nameField);
            panel.add(new JLabel("Description:"));
            panel.add(descField);

            int result = JOptionPane.showConfirmDialog(frame, panel, "Edit Project", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
            	if(nameField.getText().length() > 20) {
            		
            		JOptionPane.showMessageDialog(frame, "Please try a shorter name ", "Invalid Name", JOptionPane.ERROR_MESSAGE);
            		return;
            	}
                project.title = nameField.getText();
                project.description = descField.getText();
                projectListModel.set(index, project.title); // Update displayed name
                openSelectedProject(); // Refresh project details
            }
        }
    }
    
    private void deleteProject() {
        int index = projectList.getSelectedIndex();
        if (index >= 0) {
            projects.remove(index);
            projectListModel.remove(index);
            currentProject = null;
            projectTitleLabel.setText("Project Name: ");
            projectDescriptionLabel.setText("Description: ");
            model.setRowCount(0); // Clear task table
        }
    }

    
    private void saveProjects() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("projects.dat"))) {
            out.writeObject(projects);
            JOptionPane.showMessageDialog(frame, "Projects saved successfully!");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void loadProjects() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("projects.dat"))) {
            projects = (ArrayList<Project>) in.readObject();
            projectListModel.clear();
            for (Project p : projects) {
                projectListModel.addElement(p.title);
            }
            JOptionPane.showMessageDialog(frame, "Projects loaded successfully!");
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ProjectManagerApp::new);
    }
}

