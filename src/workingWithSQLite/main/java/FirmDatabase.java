package workingWithSQLite.main.java;

import java.sql.*;
import java.util.Locale;

public class FirmDatabase {

    private static final String DB_URL = "jdbc:sqlite:firms_db.db";
    private Connection connection;

    public FirmDatabase() {
        try {
            connection = DriverManager.getConnection(DB_URL);
            System.out.println("[DB] Успішно підключено до бази даних SQLite.");
            createTable();
        } catch (SQLException e) {
            System.err.println("[DB Помилка] Не вдалося підключитися до БД: " + e.getMessage());
        }
    }

    public void createTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS firms (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "employeeCount INTEGER, " +
                "annualRevenue REAL, " +
                "industry TEXT" +
                ")";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createTableSQL);
            System.out.println("[DB] Схему таблиці 'firms' перевірено/створено.");
        } catch (SQLException e) {
            System.err.println("[DB Помилка] Не вдалося створити таблицю: " + e.getMessage());
        }
    }

    public void viewAllFirms() {
        System.out.println("\n--- [1] Перегляд всіх фірм ---");
        String selectSQL = "SELECT * FROM firms";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(selectSQL)) {

            boolean found = false;
            while (rs.next()) {
                found = true;

                String output = String.format(Locale.US,
                        "ID: %d | Фірма [Назва: %s, Співробітники: %d, Дохід: $%,.0f, Галузь: %s]\n",
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("employeeCount"),
                        rs.getDouble("annualRevenue"),
                        rs.getString("industry"));
                System.out.print(output);
            }
            if (!found) {
                System.out.println("База даних порожня.");
            }
        } catch (SQLException e) {
            System.err.println("[DB Помилка] Не вдалося виконати вибірку: " + e.getMessage());
        }
    }

    public void addFirm(Firm firm) {
        String insertSQL = "INSERT INTO firms (name, employeeCount, annualRevenue, industry) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
            pstmt.setString(1, firm.getName());
            pstmt.setInt(2, firm.getEmployeeCount());
            pstmt.setDouble(3, firm.getAnnualRevenue());
            pstmt.setString(4, firm.getIndustry());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("\n[Успіх] Нову фірму '" + firm.getName() + "' додано до БД.");
            }
        } catch (SQLException e) {
            System.err.println("[DB Помилка] Не вдалося додати фірму: " + e.getMessage());
        }
    }

    public void viewFirmsByIndustry(String industry) {
        System.out.println("\n--- [3] Пошук фірм у галузі '" + industry + "' ---");
        String selectSQL = "SELECT * FROM firms WHERE industry = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(selectSQL)) {
            pstmt.setString(1, industry);

            try (ResultSet rs = pstmt.executeQuery()) {
                boolean found = false;
                while (rs.next()) {
                    found = true;
                    String output = String.format(Locale.US,
                            "ID: %d | Фірма [Назва: %s, Співробітники: %d, Дохід: $%,.0f, Галузь: %s]\n",
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("employeeCount"),
                            rs.getDouble("annualRevenue"),
                            rs.getString("industry"));
                    System.out.print(output);
                }
                if (!found) {
                    System.out.println("Фірм у цій галузі не знайдено.");
                }
            }
        } catch (SQLException e) {
            System.err.println("[DB Помилка] Помилка пошуку: " + e.getMessage());
        }
    }


    public void deleteFirm(int id) {
        String deleteSQL = "DELETE FROM firms WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(deleteSQL)) {
            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("\n[Успіх] Фірму з ID=" + id + " видалено.");
            } else {
                System.out.println("\n[Помилка] Фірму з ID=" + id + " не знайдено.");
            }
        } catch (SQLException e) {
            System.err.println("[DB Помилка] Помилка видалення: " + e.getMessage());
        }
    }

    public void editFirm(int id, Firm updatedFirm) {
        String updateSQL = "UPDATE firms SET name = ?, employeeCount = ?, annualRevenue = ?, industry = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(updateSQL)) {
            pstmt.setString(1, updatedFirm.getName());
            pstmt.setInt(2, updatedFirm.getEmployeeCount());
            pstmt.setDouble(3, updatedFirm.getAnnualRevenue());
            pstmt.setString(4, updatedFirm.getIndustry());
            pstmt.setInt(5, id);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("\n[Успіх] Дані фірми з ID=" + id + " оновлено.");
            } else {
                System.out.println("\n[Помилка] Фірму з ID=" + id + " не знайдено для оновлення.");
            }
        } catch (SQLException e) {
            System.err.println("[DB Помилка] Помилка оновлення: " + e.getMessage());
        }
    }

    public void deleteAllAndReset() {
        String deleteFirmsSQL = "DELETE FROM firms";
        String resetSequenceSQL = "DELETE FROM sqlite_sequence WHERE name = 'firms'";

        try (Statement stmt = connection.createStatement()) {
            int affectedRows = stmt.executeUpdate(deleteFirmsSQL);
            System.out.println("\n[Успіх] Видалено " + affectedRows + " записів.");
            stmt.executeUpdate(resetSequenceSQL);
            System.out.println("[Успіх] Лічильник ID для таблиці 'firms' скинуто.");
        } catch (SQLException e) {
            System.err.println("[DB Помилка] Помилка очищення таблиці: " + e.getMessage());
        }
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("[DB] З'єднання з базою даних закрито.");
            }
        } catch (SQLException e) {
            System.err.println("[DB Помилка] Не вдалося закрити з'єднання: " + e.getMessage());
        }
    }
}