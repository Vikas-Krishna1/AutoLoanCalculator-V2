package ui.table;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class StatusRenderer
        extends DefaultTableCellRenderer
{
    @Override
    public Component getTableCellRendererComponent(
            JTable table,
            Object value,
            boolean isSelected,
            boolean hasFocus,
            int row,
            int column)
    {
        Component c =
                super.getTableCellRendererComponent(
                        table,
                        value,
                        isSelected,
                        hasFocus,
                        row,
                        column);

       String status =
        value == null
                ? ""
                : value.toString();

        if(status.equalsIgnoreCase("APPROVED"))
        {
            c.setBackground(Color.GREEN);
            c.setForeground(Color.WHITE);
        }
        else if(status.equalsIgnoreCase("DENIED"))
        {
            c.setBackground(Color.RED);
            c.setForeground(Color.WHITE);
        }
        else if(status.equalsIgnoreCase("PENDING"))
        {
            c.setBackground(Color.ORANGE);
            c.setForeground(Color.WHITE);
        }
        else
        {
            c.setForeground(Color.BLACK);
        }

        return c;
    }
}