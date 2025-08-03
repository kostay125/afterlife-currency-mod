// AfterLife Currency Mod (NeoForge 1.21.1, Java 21)

// ===========================
// CurrencyDatabaseManager.java
// ===========================
package com.after_life.after_life_mod.db;

import java.sql.*;
import java.util.UUID;

public class CurrencyDatabaseManager {
    private static Connection connection;

    public static void init() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:afterlife_currency.db");
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS currency (
                    uuid TEXT PRIMARY KEY,
                    balance INTEGER NOT NULL DEFAULT 0
                )
            """);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getBalance(UUID uuid) {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT balance FROM currency WHERE uuid = ?");
            stmt.setString(1, uuid.toString());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("balance");
            } else {
                insertPlayer(uuid);
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static void setBalance(UUID uuid, int amount) {
        try {
            PreparedStatement stmt = connection.prepareStatement("REPLACE INTO currency (uuid, balance) VALUES (?, ?)");
            stmt.setString(1, uuid.toString());
            stmt.setInt(2, amount);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addBalance(UUID uuid, int amount) {
        setBalance(uuid, getBalance(uuid) + amount);
    }

    public static boolean subtractBalance(UUID uuid, int amount) {
        int current = getBalance(uuid);
        if (current >= amount) {
            setBalance(uuid, current - amount);
            return true;
        }
        return false;
    }

    private static void insertPlayer(UUID uuid) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO currency (uuid, balance) VALUES (?, 0)");
        stmt.setString(1, uuid.toString());
        stmt.executeUpdate();
    }
}