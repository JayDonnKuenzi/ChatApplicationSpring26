
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class WrappingPanelsExample extends JFrame {

    private JPanel messagesPanel;
    private JScrollPane scrollPane;
    private JTextField inputField;
    private JButton sendButton;

    public WrappingPanelsExample() {
        setTitle("Swing Chat Demo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        messagesPanel = new JPanel();
        messagesPanel.setLayout(new BoxLayout(messagesPanel, BoxLayout.Y_AXIS));
        messagesPanel.setBackground(new Color(245, 245, 245));
        messagesPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        scrollPane = new JScrollPane(messagesPanel);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new BorderLayout(8, 8));
        inputPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        inputField = new JTextField();
        sendButton = new JButton("Send");

        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        add(inputPanel, BorderLayout.SOUTH);

        sendButton.addActionListener(e -> sendMessage());
        inputField.addActionListener(e -> sendMessage());

        addMessage("Hello. This is a short message.", true);
        addMessage("This is a much longer message so you can see the wrapping behavior. It should automatically move onto the next line instead of stretching the window horizontally or getting cut off.", false);
        addMessage("Each message is its own JPanel, so you can delete them one at a time.", true);
    }

    private void sendMessage() {
        String text = inputField.getText().trim();
        if (!text.isEmpty()) {
            addMessage(text, true);
            inputField.setText("");
        }
    }

    private void addMessage(String text, boolean fromMe) {
        JPanel row = new JPanel(new FlowLayout(fromMe ? FlowLayout.RIGHT : FlowLayout.LEFT));
        row.setOpaque(false);
        row.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel bubble = new JPanel(new BorderLayout());
        bubble.setBackground(fromMe ? new Color(180, 230, 180) : Color.WHITE);
        bubble.setBorder(new EmptyBorder(8, 10, 8, 10));

        JTextArea textArea = new JTextArea(text);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        textArea.setFocusable(false);
        textArea.setOpaque(false);
        textArea.setBorder(null);
        textArea.setFont(new Font("SansSerif", Font.PLAIN, 14));

        textArea.setColumns(22);

        JButton deleteButton = new JButton("X");
        deleteButton.setMargin(new Insets(2, 6, 2, 6));
        deleteButton.addActionListener(e -> {
            messagesPanel.remove(row);
            messagesPanel.revalidate();
            messagesPanel.repaint();
        });

        bubble.add(textArea, BorderLayout.CENTER);
        bubble.add(deleteButton, BorderLayout.EAST);

        row.add(bubble);
        messagesPanel.add(row);
        messagesPanel.add(Box.createVerticalStrut(8));

        messagesPanel.revalidate();
        SwingUtilities.invokeLater(() -> {
            JScrollBar vertical = scrollPane.getVerticalScrollBar();
            vertical.setValue(vertical.getMaximum());
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new WrappingPanelsExample().setVisible(true);
        });
    }
}
