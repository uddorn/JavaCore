package org.example;

import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.text.Collator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainForm extends JFrame {
    private List<Firm> firmList = new ArrayList<>();
    private DatabaseHandler dbHandler = new DatabaseHandler();
    private FileHandler fileHandler = new FileHandler();
    private boolean isAscending = true;

    private final Color COLOR_BACKGROUND = new Color(18, 18, 18);
    private final Color COLOR_PANEL = new Color(30, 30, 30);
    private final Color COLOR_ACCENT = new Color(58, 110, 165);
    private final Color COLOR_BTN_NEUTRAL = new Color(50, 50, 50);
    private final Color COLOR_TEXT = new Color(220, 220, 220);

    private final Font FONT_MAIN = new Font("Segoe UI", Font.PLAIN, 14);
    private final Font FONT_BOLD = new Font("Segoe UI", Font.BOLD, 14);

    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField searchField;

    public MainForm() {
        setTitle("Firm Manager");
        setSize(1100, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().setBackground(COLOR_BACKGROUND);

        dbHandler.initDB();
        initUI();
    }

    private void initUI() {
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(COLOR_BACKGROUND);
        mainPanel.setBorder(new EmptyBorder(25, 25, 25, 25));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(COLOR_BACKGROUND);

        JLabel titleLabel = new JLabel("База Даних Фірм");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);

        JPanel searchCard = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        searchCard.setBackground(COLOR_BACKGROUND);

        searchField = new JTextField(20);
        searchField.putClientProperty("JTextField.placeholderText", "Пошук методом Regex");
        searchField.putClientProperty("JComponent.roundRect", true);
        searchField.setFont(FONT_MAIN);
        searchField.setPreferredSize(new Dimension(200, 35));

        JButton searchBtn = createButton("Пошук", COLOR_ACCENT, Color.WHITE);
        JButton resetBtn = createButton("Скинути", COLOR_BTN_NEUTRAL, Color.WHITE);

        searchBtn.setPreferredSize(new Dimension(100, 35));
        resetBtn.setPreferredSize(new Dimension(100, 35));

        searchBtn.addActionListener(e -> filterByRegex());
        resetBtn.addActionListener(e -> updateTable(firmList));

        searchCard.add(searchField);
        searchCard.add(searchBtn);
        searchCard.add(resetBtn);

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(searchCard, BorderLayout.EAST);

        String[] columnNames = {"ID", "Назва", "Співробітники", "Дохід", "Галузь"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        table = new JTable(tableModel);
        styleTable(table);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(COLOR_PANEL);
        scrollPane.setBorder(BorderFactory.createLineBorder(COLOR_PANEL, 1));

        JPanel footerPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        footerPanel.setBackground(COLOR_BACKGROUND);

        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        row1.setBackground(COLOR_BACKGROUND);

        JButton addBtn = createButton("Додати запис", new Color(39, 174, 96), Color.WHITE);
        JButton delBtn = createButton("Видалити", new Color(192, 57, 43), Color.WHITE);

        Component spacer1 = Box.createHorizontalStrut(30);
        JButton sortName = createButton("Сортування за назвою компанії", COLOR_BTN_NEUTRAL, Color.WHITE);
        JButton sortEmpl = createButton("Сорт по к-сті штату", COLOR_BTN_NEUTRAL, Color.WHITE);

        addBtn.addActionListener(e -> showAddDialog());
        delBtn.addActionListener(e -> deleteSelectedFirm());
        sortName.addActionListener(e -> sortFirms(true));
        sortEmpl.addActionListener(e -> sortFirms(false));

        row1.add(addBtn);
        row1.add(delBtn);
        row1.add(spacer1);
        row1.add(sortName);
        row1.add(sortEmpl);

        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        row2.setBackground(COLOR_BACKGROUND);

        JButton loadDb = createButton("Завантажити БД", COLOR_BTN_NEUTRAL, Color.WHITE);
        JButton saveDb = createButton("Зберегти в БД", COLOR_BTN_NEUTRAL, Color.WHITE);

        Component spacer2 = Box.createHorizontalStrut(30);

        JButton saveFile = createButton("Експорт", COLOR_BTN_NEUTRAL, Color.WHITE);
        JButton loadFile = createButton("Імпорт", COLOR_BTN_NEUTRAL, Color.WHITE);

        loadDb.addActionListener(e -> loadFromDB());
        saveDb.addActionListener(e -> saveToDB());
        saveFile.addActionListener(e -> saveToFile());
        loadFile.addActionListener(e -> loadFromFile());

        row2.add(loadDb);
        row2.add(saveDb);
        row2.add(spacer2);
        row2.add(saveFile);
        row2.add(loadFile);

        footerPanel.add(row1);
        footerPanel.add(row2);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void styleTable(JTable table) {
        table.setFont(FONT_MAIN);
        table.setRowHeight(40);
        table.setBackground(COLOR_PANEL);
        table.setForeground(COLOR_TEXT);
        table.setShowVerticalLines(false);
        table.setIntercellSpacing(new Dimension(0, 0));

        JTableHeader header = table.getTableHeader();
        header.setFont(FONT_BOLD);
        header.setBackground(new Color(45, 45, 45));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(0, 45));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setHorizontalAlignment(JLabel.CENTER);
                setBorder(noFocusBorder);
                if (isSelected) {
                    c.setBackground(COLOR_ACCENT);
                    c.setForeground(Color.WHITE);
                } else {
                    c.setBackground(row % 2 == 0 ? COLOR_PANEL : new Color(38, 38, 38));
                    c.setForeground(COLOR_TEXT);
                }
                return c;
            }
        });
    }

    private JButton createButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text);
        btn.setFont(FONT_BOLD);
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.putClientProperty("JButton.buttonType", "roundRect");

        btn.setPreferredSize(new Dimension(160, 40));
        return btn;
    }

    private void updateTable(List<Firm> listToDisplay) {
        tableModel.setRowCount(0);
        for (Firm f : listToDisplay) {
            Object[] row = { f.getId() == 0 ? "-" : f.getId(), f.getName(), f.getEmployeeCount(), f.getAnnualRevenue(), f.getIndustry() };
            tableModel.addRow(row);
        }
    }

    private void sortFirms(boolean byName) {
        Collator uaCollator = Collator.getInstance(new Locale("uk", "UA"));
        uaCollator.setStrength(Collator.PRIMARY);
        if (byName) {
            firmList.sort((f1, f2) -> isAscending ? uaCollator.compare(f1.getName(), f2.getName()) : uaCollator.compare(f2.getName(), f1.getName()));
        } else {
            firmList.sort((f1, f2) -> isAscending ? Integer.compare(f1.getEmployeeCount(), f2.getEmployeeCount()) : Integer.compare(f2.getEmployeeCount(), f1.getEmployeeCount()));
        }
        isAscending = !isAscending;
        updateTable(firmList);
    }

    private void filterByRegex() {
        String input = searchField.getText();
        if (input.isEmpty()) return;
        List<Firm> filtered = new ArrayList<>();
        try {
            for (Firm f : firmList) {
                String pattern;
                if (input.contains("*") || input.contains("$") || input.contains("^")) {
                    pattern = "(?i)" + input;
                } else {
                    pattern = "(?i).*" + input + "$";
                }
                if (f.getName().matches(pattern)) filtered.add(f);
            }
            updateTable(filtered);
        } catch (Exception e) { JOptionPane.showMessageDialog(this, "Помилка Regex: " + e.getMessage()); }
    }

    private void showAddDialog() {
        JTextField nameField = new JTextField();
        JTextField empField = new JTextField();
        JTextField revField = new JTextField();
        JTextField indField = new JTextField();
        Object[] message = { "Назва:", nameField, "Співробітники:", empField, "Дохід:", revField, "Галузь:", indField };
        int option = JOptionPane.showConfirmDialog(this, message, "Додати фірму", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                firmList.add(new Firm(nameField.getText(), Integer.parseInt(empField.getText()), Double.parseDouble(revField.getText()), indField.getText()));
                updateTable(firmList);
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Помилка вводу!"); }
        }
    }

    private void deleteSelectedFirm() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            Firm firmToDelete = firmList.get(row);
            if (firmToDelete.getId() > 0) dbHandler.deleteFirm(firmToDelete.getId());
            firmList.remove(row);
            updateTable(firmList);
            try { fileHandler.saveToBinary(firmList, "firms.dat"); } catch (Exception e) {}
            JOptionPane.showMessageDialog(this, "Видалено!");
        } else { JOptionPane.showMessageDialog(this, "Виберіть рядок!"); }
    }

    private void loadFromDB() {
        firmList = dbHandler.getAllFirms();
        updateTable(firmList);
        JOptionPane.showMessageDialog(this, "Завантажено з БД");
    }

    private void saveToDB() {
        for (Firm f : firmList) if (f.getId() == 0) dbHandler.addFirm(f);
        loadFromDB();
        JOptionPane.showMessageDialog(this, "Збережено нові в БД");
    }

    private void saveToFile() {
        try { fileHandler.saveToBinary(firmList, "firms.dat"); JOptionPane.showMessageDialog(this, "Експорт ОК"); } catch (Exception e) {}
    }

    private void loadFromFile() {
        try { firmList = fileHandler.loadFromBinary("firms.dat"); updateTable(firmList); JOptionPane.showMessageDialog(this, "Імпорт ОК"); } catch (Exception e) {}
    }

    public static void main(String[] args) {
        try {
            UIManager.put("Component.arc", 15);
            UIManager.put("Button.arc", 15);
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }
        SwingUtilities.invokeLater(() -> new MainForm().setVisible(true));
    }
}