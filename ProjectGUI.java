package poo.project;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class ProjectGUI extends JFrame implements ItemListener {
    private Project project;
    private JTable taskTable;
    private DefaultTableModel tableModel;
    private JOptionPane options ;
    private String[] columnNames = {"Name", "Start Date", "End Date", "Status", "Assigned To"};
    private String[] statueNames = {"To Do" ,"In Progress" , "Done"};
    private JComboBox statuebox;
  
    public ProjectGUI(Project project) {
        this.project = project;
        setTitle("Project Manager - " + project.getProjectName());
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Table for tasks
        statuebox = new JComboBox(statueNames);
        tableModel = new DefaultTableModel(columnNames, 0);
        taskTable = new JTable(tableModel);
        TableColumn statuecolumn = taskTable.getColumnModel().getColumn(3);
        statuecolumn.setCellEditor(new DefaultCellEditor(statuebox));
        
        
        JScrollPane scrollPane = new JScrollPane(taskTable);
        scrollPane.setBackground(Color.gray);
        add(scrollPane, BorderLayout.CENTER);
        
        JPanel buttonpanel = new JPanel() ;
        
        JButton addtask = new JButton("Add Task");
        addtask.setSize(100,50);
        buttonpanel.add(addtask);
        
        JButton deletetask = new JButton("Delete Task");
        deletetask.setSize(100,50);
        buttonpanel.add(deletetask);
        
        JButton savetasks = new JButton("Save Tasks");
        savetasks.setSize(100,50);
        buttonpanel.add(savetasks);
        add(buttonpanel , BorderLayout.SOUTH);
        
        addtask.addActionListener(e -> addTask());
        deletetask.addActionListener(e -> deleteTask());
        savetasks.addActionListener(e -> saveTask());
        
        setVisible(true);
    }

    private void loadTasksIntoTable() {
        tableModel.setRowCount(0);
        for (Task task : project.getTasks()) {
            tableModel.addRow(new Object[]{task.getname(), task.getstartdate(), task.getenddate(), task.getstatue(), task.getassignedperson()});
        }
        repaint();
    }
    private void rowcolor() {
    	int numofrow = taskTable.getSelectedRow();
    	
    	switch((String)taskTable.getValueAt(numofrow, 3)) {
    	case "To Do":
    		taskTable.setGridColor(Color.yellow);
    		break;
    	case "Done":
    		break;
    	case "In Progress":
    		break;
		default:
			break;
    	
    	}

    	
    }

    private void addTask() {
    	tableModel.addRow(new Object[] {"" , "","","",""});
    	Task temp = new Task();
    }


    private void deleteTask() {
    	
    	int row = taskTable.getSelectedRow();
    	if(row >= 0 && options.showConfirmDialog(taskTable, "Please Confirm if you want to delete this task") == 0 ){
    		tableModel.removeRow(row);
    		
    	}
    	
    }
    private void saveTask() {
    	int numboftasks = taskTable.getRowCount();
    	Task temp = new Task();
    	for(int i = 0 ; i < numboftasks ; i++) {
    		
    		temp.settaskname((String)taskTable.getValueAt(i, 0));
    		
    		String start = (String)taskTable.getValueAt(i, 1);
    		String end = (String)taskTable.getValueAt(i, 2);
    		
    		temp.setstartdate(LocalDate.parse(start));
    		temp.setenddate(LocalDate.parse(end));
    		
    		temp.setstatue((String) taskTable.getValueAt(i, 3));
    		temp.settask((String)taskTable.getValueAt(i, 4));
    		temp.displaytask();
    	}
    	
    }
    

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		
	}
}
