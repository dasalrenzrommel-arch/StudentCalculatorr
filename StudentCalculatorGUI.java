 
import javax.swing.*;
import java.awt.*;

public class StudentCalculatorGUI extends JFrame {

    private final JTextField nameField = new JTextField(18);
    private final JTextField mathField = new JTextField(10);
    private final JTextField englishField = new JTextField(10);
    private final JTextField scienceField = new JTextField(10);
    private final JTextField filipinoField = new JTextField(10);
    private final JTextField computerField = new JTextField(10);

    private final JTextArea resultArea = new JTextArea(12, 34);

    public StudentCalculatorGUI() {
        setTitle("Student Calculator System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 570);
        setLocationRelativeTo(null);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(
            BorderFactory.createEmptyBorder(15, 15, 10, 15)
        );

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 6, 6, 6);
        c.anchor = GridBagConstraints.WEST;

        addRow(form, c, 0, "Student Name:", nameField);
        addRow(form, c, 1, "Mathematics (0-100):", mathField);
        addRow(form, c, 2, "English (0-100):", englishField);
        addRow(form, c, 3, "Science (0-100):", scienceField);
        addRow(form, c, 4, "Filipino (0-100):", filipinoField);
        addRow(form, c, 5, "Computer (0-100):", computerField);

        JButton calculateButton = new JButton("Calculate Result");
        JButton clearButton = new JButton("Clear");

        calculateButton.addActionListener(e -> calculateResult());
        clearButton.addActionListener(e -> clearForm());

        JPanel buttons = new JPanel(
            new FlowLayout(FlowLayout.CENTER)
        );

        buttons.add(calculateButton);
        buttons.add(clearButton);

        resultArea.setEditable(false);
        resultArea.setFont(
            new Font(Font.MONOSPACED, Font.PLAIN, 13)
        );

        resultArea.setText(
            "Enter student details and click Calculate Result."
        );

        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setBorder(
            BorderFactory.createTitledBorder("Student Report")
        );

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(form, BorderLayout.CENTER);
        topPanel.add(buttons, BorderLayout.SOUTH);

        setLayout(new BorderLayout(8, 8));
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void addRow(
        JPanel panel,
        GridBagConstraints c,
        int row,
        String label,
        JTextField field
    ) {
        c.gridx = 0;
        c.gridy = row;
        c.weightx = 0;
        c.fill = GridBagConstraints.NONE;
        panel.add(new JLabel(label), c);

        c.gridx = 1;
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        panel.add(field, c);
    }

    private double readGrade(String text, String subject) {
        double grade;

        try {
            grade = Double.parseDouble(text.trim());
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException(
                subject + " grade must be a number."
            );
        }

        if (!Double.isFinite(grade) || grade < 0 || grade > 100) {
            throw new IllegalArgumentException(
                subject + " grade must be from 0 to 100."
            );
        }

        return grade;
    }

    private void calculateResult() {
        String studentName = nameField.getText().trim();

        if (studentName.isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "Please enter the student name.",
                "Missing Information",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        try {
            double math = readGrade(
                mathField.getText(), "Mathematics"
            );

            double english = readGrade(
                englishField.getText(), "English"
            );

            double science = readGrade(
                scienceField.getText(), "Science"
            );

            double filipino = readGrade(
                filipinoField.getText(), "Filipino"
            );

            double computer = readGrade(
                computerField.getText(), "Computer"
            );

            // Calculate the total of all five subjects.
            double total = math + english + science
                         + filipino + computer;

            // Divide the total by five to get the average.
            double average = total / 5.0;

            // 75 or higher means PASS.
            String result = average >= 75 ? "PASS" : "FAIL";

            // Display the student report.
            resultArea.setText(
                "========== STUDENT REPORT ==========\n" +
                "Student Name : " + studentName + "\n" +
                String.format("Mathematics  : %.2f%n", math) +
                String.format("English      : %.2f%n", english) +
                String.format("Science      : %.2f%n", science) +
                String.format("Filipino     : %.2f%n", filipino) +
                String.format("Computer     : %.2f%n", computer) +
                "------------------------------------\n" +
                String.format("Total        : %.2f%n", total) +
                String.format("Average      : %.2f%n", average) +
                "Final Result : " + result + "\n" +
                "===================================="
            );

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(
                this,
                ex.getMessage(),
                "Invalid Input",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void clearForm() {
        nameField.setText("");
        mathField.setText("");
        englishField.setText("");
        scienceField.setText("");
        filipinoField.setText("");
        computerField.setText("");

        resultArea.setText(
            "Enter student details and click Calculate Result."
        );

        nameField.requestFocus();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StudentCalculatorGUI app =
                new StudentCalculatorGUI();

            app.setVisible(true);
        });
    }
}
