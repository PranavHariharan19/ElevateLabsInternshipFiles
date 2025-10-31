import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class AdmissionHomePage {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdmissionHomePage().createAndShowGUI());
    }
    public void createAndShowGUI() 
    {
        JFrame frame = new JFrame("College Admission System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        JLabel title = new JLabel("College Admission System", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        JButton viewSeatsButton = new JButton("View Seat Matrix");
        JButton checkCutoffButton = new JButton("Check Cutoffs");
        JButton applyButton = new JButton("Apply Now");
        JButton checkStatusButton = new JButton("Check Application Status");
        JButton exportButton = new JButton("Export Admission List");
        viewSeatsButton.addActionListener(e -> new SeatStatusViewer());
        checkCutoffButton.addActionListener(e -> showCutoffs());
        applyButton.addActionListener(e -> {new StudentApplicationForm();});
        checkStatusButton.addActionListener(e -> new CheckStatusForm());
        exportButton.addActionListener(e -> AdmissionListExporter.generateCSV("admission_list.csv"));
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        buttonPanel.add(viewSeatsButton);
        buttonPanel.add(checkCutoffButton);
        buttonPanel.add(applyButton);
        buttonPanel.add(checkStatusButton);
        buttonPanel.add(exportButton);
        frame.setLayout(new BorderLayout(10, 10));
        frame.add(title, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
    private void showCutoffs() 
    {
        JFrame tableFrame = new JFrame("Course Cutoffs");
        tableFrame.setSize(900, 400);
        tableFrame.setLocationRelativeTo(null);
        String[] columns = {"Course Name", "General", "OBC", "SC", "ST", "PWD", "Foreign"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        try 
        {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/college_admission_project", "root", "admin123");
            String query = "SELECT * FROM courses";
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) 
            {
                String course = rs.getString("course_name");
                int gen = rs.getInt("cutoff_general");
                int obc = rs.getInt("cutoff_obc");
                int sc = rs.getInt("cutoff_sc");
                int st = rs.getInt("cutoff_st");
                int pwd = rs.getInt("cutoff_pwd");
                int foreign = rs.getInt("cutoff_foreign");
                model.addRow(new Object[]{course, gen, obc, sc, st, pwd, foreign});
            }
            rs.close();
            pst.close();
            con.close();
        } 
        catch (Exception ex) 
        {
            JOptionPane.showMessageDialog(null, "Error loading cutoffs: " + ex.getMessage());
            ex.printStackTrace();
        }
        JScrollPane scrollPane = new JScrollPane(table);
        tableFrame.add(scrollPane);
        tableFrame.setVisible(true);
    }

    class StudentApplicationForm extends JFrame 
    {
        JTextField nameField, emailField, phoneField, dobField, twelfthMarksField, entranceRankField;
        JComboBox<String> courseBox, nationalityBox,categoryBox;
        JTextField addressField, cityField;
        JComboBox<String> stateBox;
        JRadioButton maleButton, femaleButton, otherButton;
        ButtonGroup genderGroup;
        public StudentApplicationForm() 
        {
            setTitle("Student Application Form");
            setSize(500, 500);
            JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10));
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            formPanel.add(new JLabel("Full Name:"));
            nameField = new JTextField();
            formPanel.add(nameField);
            formPanel.add(new JLabel("Email:"));
            emailField = new JTextField();
            formPanel.add(emailField);
            formPanel.add(new JLabel("Phone:"));
            phoneField = new JTextField();
            formPanel.add(phoneField);
            formPanel.add(new JLabel("Date of Birth (YYYY-MM-DD):"));
            dobField = new JTextField();
            formPanel.add(dobField);
            formPanel.add(new JLabel("Gender:"));
            maleButton = new JRadioButton("Male");
            femaleButton = new JRadioButton("Female");
            otherButton = new JRadioButton("Other");
            genderGroup = new ButtonGroup();
            genderGroup.add(maleButton);
            genderGroup.add(femaleButton);
            genderGroup.add(otherButton);
            JPanel genderPanel = new JPanel();
            genderPanel.add(maleButton);
            genderPanel.add(femaleButton);
            genderPanel.add(otherButton);
            formPanel.add(genderPanel);
            formPanel.add(new JLabel("Category:"));
            String[] categories = {"General", "OBC", "SC", "ST", "PWD", "Foreign"};
            categoryBox = new JComboBox<>(categories);
            formPanel.add(categoryBox);
            formPanel.add(new JLabel("12th Marks (%):"));
            twelfthMarksField = new JTextField();
            formPanel.add(twelfthMarksField);
            formPanel.add(new JLabel("Entrance Exam Rank:"));
            entranceRankField = new JTextField();
            formPanel.add(entranceRankField);
            formPanel.add(new JLabel("Course Required:"));
            courseBox = new JComboBox<>(new String[]
            {
                "computer science engineering",
                "electronics and communication engineering",
                "electrical and electronics engineering",
                "mechanical engineering",
                "civil engineering",
                "aerospace engineering",
                "information technology",
                "chemical engineering",
                "biotechnology",
                "artificial intelligence"
            });
            formPanel.add(courseBox);
            formPanel.add(new JLabel("Nationality:"));
            nationalityBox = new JComboBox<>(new String[]{"Indian", "Other"});
            formPanel.add(nationalityBox);
            formPanel.add(new JLabel("Address:"));
            addressField = new JTextField();
            formPanel.add(addressField);
            formPanel.add(new JLabel("City:"));
            cityField = new JTextField();
            formPanel.add(cityField);
            formPanel.add(new JLabel("State:"));
            String[] states = 
            {
            "Andhra Pradesh", "Arunachal Pradesh", "Assam", "Bihar", "Chhattisgarh","Goa", "Gujarat", "Haryana", "Himachal Pradesh", "Jharkhand", "Karnataka",
            "Kerala", "Madhya Pradesh", "Maharashtra", "Manipur", "Meghalaya", "Mizoram","Nagaland", "Odisha", "Punjab", "Rajasthan", "Sikkim", "Tamil Nadu", "Telangana",
            "Tripura", "Uttar Pradesh", "Uttarakhand", "West Bengal","Andaman and Nicobar Islands", "Chandigarh", "Dadra and Nagar Haveli and Daman and Diu",
            "Delhi", "Jammu and Kashmir", "Ladakh", "Lakshadweep", "Puducherry"
            };
            stateBox = new JComboBox<>(states);
            formPanel.add(stateBox);
            JButton submitButton = new JButton("Submit Application");
            formPanel.add(submitButton);
            JButton cancelButton = new JButton("Cancel");
            formPanel.add(cancelButton);
            add(formPanel);
            submitButton.addActionListener(e -> submitForm());
            cancelButton.addActionListener(e -> dispose());
            setVisible(true);
        }
        void submitForm() 
        {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String phone = phoneField.getText().trim();
            String dob = dobField.getText().trim();
            String marks = twelfthMarksField.getText().trim();
            String rank = entranceRankField.getText().trim();
            String category = (String) categoryBox.getSelectedItem();
            String course = (String) courseBox.getSelectedItem();
            String nationality = (String) nationalityBox.getSelectedItem();
            String address = addressField.getText().trim();
            String city = cityField.getText().trim();
            String state = (String) stateBox.getSelectedItem();
            String gender = "";
            if (maleButton.isSelected()) gender = "Male";
            else if (femaleButton.isSelected()) gender = "Female";
            else if (otherButton.isSelected()) gender = "Other";
            if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || dob.isEmpty()|| marks.isEmpty() || rank.isEmpty() || address.isEmpty() || city.isEmpty()||gender.isEmpty()) 
            {
                JOptionPane.showMessageDialog(this, "Please fill all fields.");
                return;
            }
            try 
            {
                double twelfthMarks = Double.parseDouble(marks);
                int entranceRank = Integer.parseInt(rank);
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/college_admission_project", "root", "admin123");
                String insertStudent = "INSERT INTO students (full_name,phone_number,email, date_of_birth,gender,category,nationality,course_required,entrance_marks, twelfth_marks,address,city,state) " +"VALUES (?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?,?)";
                PreparedStatement ps1 = con.prepareStatement(insertStudent,Statement.RETURN_GENERATED_KEYS);
                ps1.setString(1, name);
                ps1.setString(2, phone);
                ps1.setString(3, email);
                ps1.setString(4, dob);
                ps1.setString(5, gender);
                ps1.setString(6,category);
                ps1.setString(7, nationality);
                ps1.setString(8, course);
                ps1.setInt(9, entranceRank);
                ps1.setDouble(10, twelfthMarks);
                ps1.setString(11, address);
                ps1.setString(12, city);
                ps1.setString(13, state);
                ps1.executeUpdate();
                ResultSet rsStudent = ps1.getGeneratedKeys();
                int studentId = -1;
                if (rsStudent.next()) 
                {
                studentId = rsStudent.getInt(1);
                }
                String getCourseId = "SELECT course_id FROM courses WHERE course_name = ?";
                PreparedStatement psCourse = con.prepareStatement(getCourseId);
                psCourse.setString(1, course);
                ResultSet rsCourse = psCourse.executeQuery();
                int courseId = -1;
                if (rsCourse.next()) 
                {
                    courseId = rsCourse.getInt("course_id");
                }
                String insertApp = "INSERT INTO applications (student_id, course_id, entrance_rank,status) VALUES (?, ?, ?, ?)";
                PreparedStatement ps2 = con.prepareStatement(insertApp);
                ps2.setInt(1, studentId);
                ps2.setInt(2, courseId);
                ps2.setInt(3, entranceRank);
                ps2.setString(4, "Pending");
                ps2.executeUpdate();
                con.close();
                JOptionPane.showMessageDialog(this, "Application submitted successfully!");
                dispose();
            } 
            catch (NumberFormatException ex) 
            {
                JOptionPane.showMessageDialog(this, "Marks and rank must be valid numbers.");
            }
            catch (SQLException ex) 
            {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
            }
        }
    }
    public class CheckStatusForm extends JFrame 
    {
        private JTextField emailField, dobField;
        private JButton checkStatusButton;
        public CheckStatusForm() 
        {
            setTitle("Check Admission Status");
            setSize(400, 250);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLayout(new GridLayout(4, 2, 10, 10));
            add(new JLabel("Email:"));
            emailField = new JTextField();
            add(emailField);
            add(new JLabel("Date of Birth (YYYY-MM-DD):"));
            dobField = new JTextField();
            add(dobField);
            checkStatusButton = new JButton("Check Status");
            add(new JLabel()); 
            add(checkStatusButton);
            checkStatusButton.addActionListener(e -> checkAdmissionStatus());
            setVisible(true);
        }
        private void checkAdmissionStatus() 
        {
            String email = emailField.getText().trim();
            String dob = dobField.getText().trim();
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/college_admission_project", "root", "admin123")) 
            {
                String studentQuery = "SELECT s.id, s.entrance_marks, s.twelfth_marks, s.category, s.course_required FROM students s WHERE s.email = ? AND s.date_of_birth = ?";
                PreparedStatement ps = con.prepareStatement(studentQuery);
                ps.setString(1, email);
                ps.setString(2, dob);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) 
                {
                    JOptionPane.showMessageDialog(this, "Student not found.");
                    return;
                }
                int studentId = rs.getInt("id");
                int rank = rs.getInt("entrance_marks");
                double marks = rs.getDouble("twelfth_marks");
                String category = rs.getString("category").toLowerCase();
                String course = rs.getString("course_required");
                rs.close();
                ps.close();
                String categoryColumn = switch (category) 
                {
                    case "general" -> "cutoff_general";
                    case "obc" -> "cutoff_obc";
                    case "sc" -> "cutoff_sc";
                    case "st" -> "cutoff_st";
                    case "pwd" -> "cutoff_pwd";
                    case "foreign" -> "cutoff_foreign";
                    default -> null;
                };
                if (categoryColumn == null) 
                {
                    JOptionPane.showMessageDialog(this, "Invalid category.");
                    return;
                }
                String cutoffQuery = "SELECT " + categoryColumn + " FROM courses WHERE course_name = ?";
                PreparedStatement ps2 = con.prepareStatement(cutoffQuery);
                ps2.setString(1, course);
                ResultSet rs2 = ps2.executeQuery();
                if (!rs2.next()) 
                {
                    JOptionPane.showMessageDialog(this, "Course not found.");
                    return;
                }
                int cutoff = rs2.getInt(1);
                rs2.close();
                ps2.close();
                String status = (marks >= 75 && rank <= cutoff) ? "Approved" : "Rejected";
                String updateStatus = "UPDATE applications SET status = ? WHERE student_id = ?";
                PreparedStatement ps3 = con.prepareStatement(updateStatus);
                ps3.setString(1, status);
                ps3.setInt(2, studentId);
                ps3.executeUpdate();
                ps3.close();
                if (status.equals("Approved")) 
                {
                String updateSeats = "UPDATE courses SET available_seats = available_seats - 1 WHERE course_name = ? AND available_seats > 0";
                PreparedStatement ps4 = con.prepareStatement(updateSeats);
                ps4.setString(1, course);
                ps4.executeUpdate();
                ps4.close();
                }
                JOptionPane.showMessageDialog(this, "Your application status: " + status);
            } 
            catch (Exception ex) 
            {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error checking status: " + ex.getMessage());
            }
        }
    }
    public class SeatStatusViewer extends JFrame 
    {
        public SeatStatusViewer() 
        {
            setTitle("Seat Availability");
            setSize(500, 400);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            String[] columns = {"Course Name", "Total Seats", "Available Seats"};
            DefaultTableModel model = new DefaultTableModel(columns, 0);
            JTable table = new JTable(model);
            JScrollPane scrollPane = new JScrollPane(table);
            add(scrollPane, BorderLayout.CENTER);
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/college_admission_project", "root", "admin123")) {
                String query = "SELECT course_name, total_seats, available_seats FROM courses";
                PreparedStatement ps = con.prepareStatement(query);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) 
                {
                    String course = rs.getString("course_name");
                    int total = rs.getInt("total_seats");
                    int available = rs.getInt("available_seats");
                    model.addRow(new Object[]{course, total, available});
                }
                rs.close();
                ps.close();
            } 
            catch (Exception ex) 
            {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error loading seat data: " + ex.getMessage());
            }
            setVisible(true);
        }
    }
    public class AdmissionListExporter 
    {
        public static void generateCSV(String filePath) 
        {
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/college_admission_project", "root", "admin123");
                 Statement stmt = con.createStatement();
                 FileWriter writer = new FileWriter(filePath)) 
                 {
                    String query = """
                    SELECT s.full_name, s.course_required, s.category, s.entrance_marks, a.status
                    FROM students s
                    JOIN applications a ON s.id = a.student_id
                    WHERE a.status = 'Approved'
                    """;
                ResultSet rs = stmt.executeQuery(query);
                writer.write("Name,Course,Category,Entrance Marks,Status\n");
                while (rs.next()) 
                {
                    writer.write(rs.getString("full_name") + "," +rs.getString("course_required") + "," +rs.getString("category") + "," +rs.getInt("entrance_marks") + "," +rs.getString("status") + "\n");
                }
                writer.flush();
                System.out.println("CSV file generated at: " + filePath);
                JOptionPane.showMessageDialog(null, "Admission list exported successfully!");
            } 
            catch (IOException | SQLException e) 
            {
                e.printStackTrace();
            }
        }
    }    
}