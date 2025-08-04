package com.after_life.after_life_mod.db;

import com.after_life.after_life_mod.currency.CurrencyManager;
import java.sql.*;
import java.util.UUID;

public class DatabaseCurrencyManager implements CurrencyManager {
    private final Connection connection;

    public DatabaseCurrencyManager() throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:sqlite:currency.db");
        initializeDatabase();
    }

    private void initializeDatabase() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS player_balances (" +
                    "uuid TEXT PRIMARY KEY, " +
                    "balance INTEGER NOT NULL DEFAULT 0)");
        }
    }

    @Override
    public int getBalance(UUID playerId) {
        final String sql = "SELECT balance FROM player_balances WHERE uuid = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, playerId.toString());
            ResultSet rs = pstmt.executeQuery();
            return rs.next() ? rs.getInt("balance") : 0;
        } catch (SQLException e) {
            throw new RuntimeException("Database error", e);
        }
    }

    @Override
    public void addBalance(UUID playerId, int amount) {
        final String sql = """
            INSERT INTO player_balances(uuid, balance) 
            VALUES(?, ?)
            ON CONFLICT(uuid) DO UPDATE SET 
            balance = balance + excluded.balance""";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, playerId.toString());
            pstmt.setInt(2, amount);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Database error", e);
        }
    }

    @Override
    public boolean removeBalance(UUID playerId, int amount) {
        if (getBalance(playerId) < amount) {
            return false;
        }

        final String sql = "UPDATE player_balances SET balance = balance - ? WHERE uuid = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, amount);
            pstmt.setString(2, playerId.toString());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Database error", e);
        }
    }
}