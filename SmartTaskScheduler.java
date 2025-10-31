import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

class Task implements Comparable<Task> {
    String title;
    int priority;
    LocalDate deadline;
    LocalTime time;
    boolean completed;
    public Task(String title, int priority, LocalDate deadline, LocalTime time, boolean completed) {
        this.title = title;
        this.priority = priority;
        this.deadline = deadline;
        this.time = time;
        this.completed = completed;
    }
    public int compareTo(Task other) 
    {
        if (!this.deadline.equals(other.deadline))
            return this.deadline.compareTo(other.deadline);
        if (this.priority != other.priority)
            return Integer.compare(this.priority, other.priority);
        return this.time.compareTo(other.time);
    }
}

public class SmartTaskScheduler extends JFrame {
    private DefaultTableModel tableModel;
    private JTable table;
    private PriorityQueue<Task> taskQueue = new PriorityQueue<>();
    private Set<Task> remindedTasks = new HashSet<>();
    private Set<Task> reminded10Min = new HashSet<>();
    private Set<Task> reminded1Hour = new HashSet<>();
    private Set<Task> reminded24Hour = new HashSet<>();
    public SmartTaskScheduler() 
    {
        setTitle("Smart Task Scheduler");
        setSize(1000,600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        tableModel = new DefaultTableModel(new Object[]{"Task","Priority","Deadline","Time","Completed"},0) 
        {
            public Class<?> getColumnClass(int column) 
            {
                return column == 4 ? Boolean.class : String.class;
            }
        };
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        JTextField taskField = new JTextField(10); 
        JComboBox<String> priorityBox = new JComboBox<>(new String[]{"1 - High", "2 - Medium", "3 - Low"});
        JTextField deadlineField = new JTextField(10);
        JTextField timeField = new JTextField(5);
        deadlineField.setText(LocalDate.now().toString());
        timeField.setText("12:00");
        JButton addBtn = new JButton("Add Task");
        JButton editBtn = new JButton("Edit Task");
        JButton deleteBtn = new JButton("Delete Selected");
        JButton deleteAllBtn = new JButton("Delete All");
        JButton saveButton = new JButton("Save");
        JButton loadButton = new JButton("Load");
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(new JLabel("Task:"));
        inputPanel.add(taskField);
        inputPanel.add(new JLabel("Priority:"));
        inputPanel.add(priorityBox);
        inputPanel.add(new JLabel("Deadline (yyyy-MM-dd):"));
        inputPanel.add(deadlineField);
        inputPanel.add(new JLabel("Time (HH:mm):"));
        inputPanel.add(timeField);
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(addBtn);
        buttonPanel.add(editBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(deleteAllBtn);
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.add(inputPanel);
        bottomPanel.add(buttonPanel);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        saveButton.addActionListener(e ->saveTasksToFile());
        loadButton.addActionListener(e ->loadTasksFromFile());
        addBtn.addActionListener(e -> {
            String title = taskField.getText().trim();
            String priorityStr = (String) priorityBox.getSelectedItem();
            String deadlineStr = deadlineField.getText().trim();
            String timeStr = timeField.getText().trim();
            if (!title.isEmpty() && !deadlineStr.isEmpty() && !timeStr.isEmpty()) {
                try {
                    int priority = Integer.parseInt(((String) priorityBox.getSelectedItem()).split(" ")[0]);
                    LocalDate deadline = LocalDate.parse(deadlineStr);
                    LocalTime time = LocalTime.parse(timeStr);
                    LocalDate today = LocalDate.now();
                    LocalTime now = LocalTime.now();
                    if (deadline.isBefore(today) || (deadline.equals(today) && time.isBefore(now))) 
                    {
                        JOptionPane.showMessageDialog(this, "Cannot add task in the past.");
                        return;
                    }
                    Task task = new Task(title, priority, deadline, time, false);
                    taskQueue.add(task);
                    updateTable();
                    taskField.setText("");
                    deadlineField.setText(LocalDate.now().toString());
                    timeField.setText("12:00");
                } 
                catch (Exception ex) 
                {
                    JOptionPane.showMessageDialog(this, "Invalid input.");
                }
            } 
            else 
            {
                JOptionPane.showMessageDialog(this, "All fields required.");
            }
        });
        editBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) 
            {
                JOptionPane.showMessageDialog(this, "Select a task to edit.");
                return;
            }
            try {
                String title = taskField.getText().trim();
                int priority = Integer.parseInt(((String) priorityBox.getSelectedItem()).split(" ")[0]);
                LocalDate deadline = LocalDate.parse(deadlineField.getText().trim());
                LocalTime time = LocalTime.parse(timeField.getText().trim());
                List<Task> temp = new ArrayList<>(taskQueue);
                taskQueue.clear();
                for (int i = 0; i < temp.size(); i++) 
                {
                    if (i == row) {
                        taskQueue.add(new Task(title, priority, deadline, time, false));
                    }
                    else 
                    {
                        taskQueue.add(temp.get(i));
                    }
                }
                updateTable();
            } 
            catch (Exception ex) 
            {
                JOptionPane.showMessageDialog(this, "Invalid input.");
            }
        });
        deleteBtn.addActionListener(e -> {
            int[] rows = table.getSelectedRows();
            if (rows.length == 0) 
            {
                JOptionPane.showMessageDialog(this, "Select tasks to delete.");
                return;
            }
            List<Task> all = new ArrayList<>(taskQueue);
            taskQueue.clear();
            for (int i = 0; i < all.size(); i++) {
                if (!isIndexSelected(i, rows)) {
                    taskQueue.add(all.get(i));
                }
            }
            updateTable();
        });
        deleteAllBtn.addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(this, "Delete all tasks?", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) 
            {
                taskQueue.clear();
                updateTable();
            }
        });
        startReminderChecker();
        setVisible(true);
    }
    private void saveTasksToFile() 
    {
        try (PrintWriter writer = new PrintWriter("tasks.txt")) 
        {
            for (Task task : taskQueue) 
            {
                writer.println(task.title + "," +task.priority + "," +task.deadline + "," +task.time + "," +task.completed);
            }
            JOptionPane.showMessageDialog(this, "Tasks saved successfully.");
        }    
        catch (IOException e) 
        {
            JOptionPane.showMessageDialog(this, "Error saving tasks.");
            e.printStackTrace();
        }
    }
    private void loadTasksFromFile() 
    {
        try 
        {
            List<String> lines = java.nio.file.Files.readAllLines(java.nio.file.Paths.get("tasks.txt"));
            taskQueue.clear();
            remindedTasks.clear();
            for (String line : lines) 
            {
                String[] parts = line.split(",");
                if (parts.length >= 5) 
                {
                    String title = parts[0];
                    int priority = Integer.parseInt(parts[1]);
                    LocalDate deadline = LocalDate.parse(parts[2]);
                    LocalTime time = LocalTime.parse(parts[3]);
                    boolean completed = Boolean.parseBoolean(parts[4]);
                    Task task = new Task(title, priority, deadline, time, completed);
                    taskQueue.add(task);
                }
            }
            updateTable();
            JOptionPane.showMessageDialog(this, "Tasks loaded successfully.");
        } 
        catch (IOException e) 
        {
            JOptionPane.showMessageDialog(this, "Error loading tasks.");
            e.printStackTrace();
        }
    }
    private void updateTable() {
        tableModel.setRowCount(0);
        List<Task> sorted = new ArrayList<>(taskQueue);
        Collections.sort(sorted);
        for (Task task : sorted) {
            tableModel.addRow(new Object[]{
                task.title, task.priority, task.deadline.toString(), task.time.toString(), task.completed
            });
        }
    }
    private boolean isIndexSelected(int index, int[] selected) 
    {
        for (int sel : selected) if (sel == index) return true;
        return false;
    }
    private void showReminder(Task task, String message) 
    {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(this, message, "Task Reminder", JOptionPane.INFORMATION_MESSAGE);
        });
    }
    private void startReminderChecker() 
    {
    Timer timer = new Timer(60 * 1000, e -> {
        LocalDateTime now = LocalDateTime.now();
        for (Task task : taskQueue) 
        {
            if (task.completed) continue;
            LocalDateTime taskDateTime = LocalDateTime.of(task.deadline, task.time);
            if (!reminded24Hour.contains(task) &&now.isAfter(taskDateTime.minusHours(24)) &&now.isBefore(taskDateTime.minusHours(23).minusMinutes(59))) 
            {
                reminded24Hour.add(task);
                showReminder(task," Reminder: 24 hours left for task \"" + task.title + "\" due at " + task.time);
            }
            if (!reminded1Hour.contains(task) &&now.isAfter(taskDateTime.minusHours(1)) &&now.isBefore(taskDateTime.minusMinutes(59))) 
            {
                reminded1Hour.add(task);
                showReminder(task," Reminder: 1 hour left for task \"" + task.title + "\" due at " + task.time);
            }
            if (!reminded10Min.contains(task) &&now.isAfter(taskDateTime.minusMinutes(10)) &&now.isBefore(taskDateTime)) 
            {
                reminded10Min.add(task);
                showReminder(task," Reminder: 10 minutes left for task \"" + task.title + "\" due at " + task.time);
            }
        }
    });
    timer.start();
}
    public static void main(String[] args) 
    {
        SwingUtilities.invokeLater(SmartTaskScheduler::new);
    }
}