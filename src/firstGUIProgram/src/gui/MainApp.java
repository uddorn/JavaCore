package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.URL; // Додано для перевірки ресурсу

import math.ExpressionCalculator;
import oop.*;

public class MainApp {

    public static void main(String[] args) {
        // Використовуємо потік диспетчеризації подій Swing для потокобезпеки
        SwingUtilities.invokeLater(MainApp::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Інтегровані ЛР 1-7, виконав Ткаченко Д. О.");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("ЛР 1-5: Обчислення виразу", createLab1to5Panel());
        tabbedPane.addTab("ЛР 6-7: Демонстрація ООП", createLab6to7Panel());
        tabbedPane.addTab("Про автора", createAboutPanel());

        frame.add(tabbedPane);
        frame.setVisible(true);
    }

    private static JPanel createAboutPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel authorLabel = new JLabel("<html><b>Автор:</b> Ткаченко Д. О.<br><b>Група:</b> ІПЗ-23006б</html>", SwingConstants.CENTER);
        authorLabel.setFont(new Font("Serif", Font.BOLD, 18));

        JLabel photoLabel = new JLabel();
        photoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        try {
            URL imageURL = MainApp.class.getResource("/my_photo.jpg");

            if (imageURL != null) {
                ImageIcon icon = new ImageIcon(imageURL);
                Image image = icon.getImage().getScaledInstance(128, 128, Image.SCALE_SMOOTH);
                photoLabel.setIcon(new ImageIcon(image));
            } else {
                photoLabel.setText("[Файл /my_photo.png не знайдено в папці resources]");
            }
        } catch (Exception e) {
            photoLabel.setText("[Помилка завантаження зображення]");
            e.printStackTrace();
        }

        panel.add(authorLabel, BorderLayout.NORTH);
        panel.add(photoLabel, BorderLayout.CENTER);

        return panel;
    }

    private static JPanel createLab1to5Panel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Панель вводу
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        inputPanel.add(new JLabel("Введіть x:"));
        JTextField xField = new JTextField(5);
        inputPanel.add(xField);

        inputPanel.add(new JLabel("Введіть варіант N:"));
        JTextField nVarField = new JTextField(5);
        inputPanel.add(nVarField);

        // Панель кнопок
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        JButton calcSingleButton = new JButton("Обчислити одне значення (ЛР 1-3)");
        JButton calcForButton = new JButton("Обчислити для діапазону (цикл for)");
        JButton calcWhileButton = new JButton("Обчислити для діапазону (цикл while)");
        JButton calcLab5Button = new JButton("Продемонструвати ЛР 5");

        // Вирівнювання кнопок по центру
        calcSingleButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        calcForButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        calcWhileButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        calcLab5Button.setAlignmentX(Component.CENTER_ALIGNMENT);

        buttonPanel.add(calcSingleButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        buttonPanel.add(calcForButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        buttonPanel.add(calcWhileButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        buttonPanel.add(calcLab5Button);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(inputPanel, BorderLayout.NORTH);
        topPanel.add(buttonPanel, BorderLayout.CENTER);

        // Область виводу
        JTextArea outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(outputArea);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Обробники подій
        calcSingleButton.addActionListener(e -> {
            outputArea.setText("");
            try {
                double x = Double.parseDouble(xField.getText());
                ExpressionCalculator calc = new ExpressionCalculator();
                double result = calc.calculate(x);
                outputArea.setText("F = " + result);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "Некоректне введення для x. Введіть число.", "Помилка вводу", JOptionPane.ERROR_MESSAGE);
            } catch (ArithmeticException ex) {
                outputArea.setText("Помилка: " + ex.getMessage());
            }
        });

        ActionListener rangeListener = e -> {
            outputArea.setText("");
            try {
                int N = Integer.parseInt(nVarField.getText());
                double start = -10 - 2.5 * N;
                double end = 5 + 1.2 * N;
                double step = 0.5 + N / 20.0;

                StringBuilder sb = new StringBuilder();
                ExpressionCalculator calc = new ExpressionCalculator();

                if (e.getSource() == calcForButton) {
                    sb.append("=== Обчислення циклом for ===\n");
                    for (double x = start; x <= end; x += step) {
                        try {
                            double F = calc.calculate(x);
                            sb.append(String.format("x = %.3f  ->  F = %.6f%n", x, F));
                        } catch (ArithmeticException ex) {
                            sb.append(String.format("x = %.3f  ->  %s%n", x, ex.getMessage()));
                        }
                    }
                } else {
                    sb.append("=== Обчислення циклом while ===\n");
                    double x = start;
                    while (x <= end) {
                        try {
                            double F = calc.calculate(x);
                            sb.append(String.format("x = %.3f  ->  F = %.6f%n", x, F));
                        } catch (ArithmeticException ex) {
                            sb.append(String.format("x = %.3f  ->  %s%n", x, ex.getMessage()));
                        }
                        x += step;
                    }
                }
                outputArea.setText(sb.toString());

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "Некоректне введення для N. Введіть ціле число.", "Помилка вводу", JOptionPane.ERROR_MESSAGE);
            }
        };
        calcForButton.addActionListener(rangeListener);
        calcWhileButton.addActionListener(rangeListener);

        calcLab5Button.addActionListener(e -> {
            outputArea.setText("");
            try {
                int N = Integer.parseInt(nVarField.getText());
                double start = -10 - 2.5 * N;
                double end = 5 + 1.2 * N;
                double step = 0.5 + N / 20.0;

                StringBuilder sb = new StringBuilder();

                ExpressionCalculator calcDefault = new ExpressionCalculator();
                sb.append("=== Нестатичний метод (конструктор за замовчуванням) ===\n");
                for (double x = start; x <= end; x += step) {
                    try {
                        double F = calcDefault.calculate(x);
                        sb.append(String.format("x = %.3f  ->  F = %.6f%n", x, F));
                    } catch (ArithmeticException ex) {
                        sb.append(String.format("x = %.3f  ->  %s%n", x, ex.getMessage()));
                    }
                }

                ExpressionCalculator calcCustom = new ExpressionCalculator(1.0, 0.7, 2.0);
                sb.append("\n=== Статичний метод  ===\n");
                double x_while = start;
                while (x_while <= end) {
                    try {
                        double F = ExpressionCalculator.calculateStatic(x_while, calcCustom.getA(), calcCustom.getB(), calcCustom.getZ());
                        sb.append(String.format("x = %.3f  ->  F = %.6f%n", x_while, F));
                    } catch (ArithmeticException ex) {
                        sb.append(String.format("x = %.3f  ->  %s%n", x_while, ex.getMessage()));
                    }
                    x_while += step;
                }
                outputArea.setText(sb.toString());

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "Некоректне введення для N. Введіть ціле число.", "Помилка вводу", JOptionPane.ERROR_MESSAGE);
            }
        });

        return panel;
    }

    private static JPanel createLab6to7Panel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton showInfoButton = new JButton("Показати інформацію про фірму (ЛР 6-7)");
        JTextArea outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(outputArea);

        panel.add(showInfoButton, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        showInfoButton.addActionListener(e -> {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(baos);
            PrintStream old = System.out;
            System.setOut(ps);

            Employee e1 = new Employee("Ігор", "Програміст", 30000);
            Employee e2 = new Employee("Олександр", "Тестувальник", 20000);
            Employee e3 = new Employee("Вадим", "Менеджер", 25000);

            ITFirm firm = new ITFirm("SoftWare", "Київ, пр. М. Руденка", "розробка веб-додатків");
            firm.addEmployee(e1);
            firm.addEmployee(e2);
            firm.addEmployee(e3);

            firm.companyInfo();
            System.out.println("---------------------------------");
            firm.showInfo();
            System.out.println("---------------------------------");
            firm.developSoftware();
            System.out.println("---------------------------------");
            System.out.println("Бонус для працівника " + e2.getName() + ": " + e2.calculateBonus());

            System.out.flush();
            System.setOut(old);

            outputArea.setText(baos.toString());
        });

        return panel;
    }
}