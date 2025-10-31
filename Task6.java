import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class Task6
{
    public static void main(String[] args) {
        JFrame frame = new JFrame("To-Do List");
        DefaultListModel<String> tasks = new DefaultListModel<>();
        JList<String> taskList = new JList<>(tasks);
        JScrollPane scroll = new JScrollPane(taskList);
        JTextField taskInput = new JTextField(12);
        JButton addButton = new JButton("Add");
        JButton deleteButton = new JButton("Delete");
        JPanel panel = new JPanel();
        panel.add(taskInput);
        panel.add(addButton);
        panel.add(deleteButton);
        frame.add(scroll,BorderLayout.CENTER);
        frame.add(panel,BorderLayout.SOUTH);
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String task = taskInput.getText().trim();
                if (!task.isEmpty()) {
                    tasks.addElement(task);
                    taskInput.setText("");
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int index = taskList.getSelectedIndex();
                if (index != -1) {
                    tasks.remove(index);
                }
            }
        });
        frame.setSize(350, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}