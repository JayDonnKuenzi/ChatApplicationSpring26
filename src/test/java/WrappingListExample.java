/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author lukew
 */
import javax.swing.*;
import java.awt.*;

public class WrappingListExample {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            String[] items = {
                "Short text",
                "This is a much longer text that should wrap into multiple lines in the list cell. " +
                "It will adjust the height automatically so all text is visible.",
                "Another short one",
                "Yet another very long text that demonstrates how wrapping works in a JList with a custom cell renderer."
            };

            JList<String> list = new JList<>(items);
            list.setFixedCellHeight(-1); // Allow variable heights

            list.setCellRenderer((jList, value, index, isSelected, cellHasFocus)-> {
                JTextArea textArea = new JTextArea(value);
                textArea.setLineWrap(true);
                textArea.setWrapStyleWord(true);
                textArea.setOpaque(true);
            
                // Match selection colors
                if (isSelected) {
                    textArea.setBackground(jList.getSelectionBackground());
                    textArea.setForeground(jList.getSelectionForeground());
                } else {
                    textArea.setBackground(jList.getBackground());
                    textArea.setForeground(jList.getForeground());
                }

                // Set width so wrapping works
                int width = jList.getWidth();
                if (width > 0) {
                    textArea.setSize(width, Short.MAX_VALUE);
                }

                return textArea;
            });

            JFrame frame = new JFrame("Wrapping JList Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new JScrollPane(list));
            frame.setSize(400, 300);
            frame.setVisible(true);
        });
    }
}
