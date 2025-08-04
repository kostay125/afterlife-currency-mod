package com.after_life.after_life_mod.db;

import com.after_life.after_life_mod.currency.CurrencyManager;
import java.util.UUID;
import java.util.HashMap;
import java.util.Map;

public class InMemoryCurrencyManager implements CurrencyManager {
    private final Map<UUID, Integer> balances = new HashMap<>();

    @Override
    public int getBalance(UUID playerId) {
        return balances.getOrDefault(playerId, 0);
    }

    @Override
    public void addBalance(UUID playerId, int amount) {
        balances.put(playerId, getBalance(playerId) + amount);
    }

    @Override
    public boolean removeBalance(UUID playerId, int amount) {
        int currentBalance = getBalance(playerId);
        if (currentBalance < amount) {
            return false;
        }
        balances.put(playerId, currentBalance - amount);
        return true;
    }
}