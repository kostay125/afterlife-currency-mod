package com.after_life.after_life_mod.currency;

import java.sql.*;
import java.util.UUID;

public class DatabaseCurrencyStorage implements CurrencyStorage {
    private Connection connection;

    public DatabaseCurrencyStorage() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:afterlife_currency.db");
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS currency (
                    uuid TEXT PRIMARY KEY,
                    balance INTEGER NOT NULL DEFAULT 0
                )
            """);
        }
    }

    @Override
    public int getBalance(UUID uuid) {
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

    @Override
    public void setBalance(UUID uuid, int amount) {
        try {
            PreparedStatement stmt = connection.prepareStatement("REPLACE INTO currency (uuid, balance) VALUES (?, ?)");
            stmt.setString(1, uuid.toString());
            stmt.setInt(2, amount);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addBalance(UUID uuid, int amount) {
        setBalance(uuid, getBalance(uuid) + amount);
    }

    @Override
    public boolean subtractBalance(UUID uuid, int amount) {
        int current = getBalance(uuid);
        if (current >= amount) {
            setBalance(uuid, current - amount);
            return true;
        }
        return false;
    }

    private void insertPlayer(UUID uuid) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO currency (uuid, balance) VALUES (?, 0)");
        stmt.setString(1, uuid.toString());
        stmt.executeUpdate();
    }
}
