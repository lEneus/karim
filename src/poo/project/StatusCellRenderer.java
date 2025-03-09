package poo.project;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

class StatusCellRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        
        if (value != null) {
            String status = value.toString();
            switch (status) {
                case "Not Started":
                    cell.setBackground(Color.RED);
                    cell.setForeground(Color.WHITE);
                    break;
                case "In Progress":
                    cell.setBackground(Color.YELLOW);
                    cell.setForeground(Color.BLACK);
                    break;
                case "Completed":
                    cell.setBackground(Color.GREEN);
                    cell.setForeground(Color.WHITE);
                    break;
                default:
                    cell.setBackground(Color.WHITE);
                    cell.setForeground(Color.BLACK);
                    break;
            }
        }
        return cell;
    }
}

