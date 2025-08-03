package com.after_life.after_life_mod.db;

import com.after_life.after_life_mod.currency.CurrencyStorage;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InMemoryCurrencyStorage implements CurrencyStorage {
    private final Map<UUID, Integer> balances = new HashMap<>();

    @Override
    public int getBalance(UUID uuid) {
        return balances.getOrDefault(uuid, 0);
    }

    @Override
    public void setBalance(UUID uuid, int amount) {
        balances.put(uuid, amount);
    }

    @Override
    public void addBalance(UUID uuid, int amount) {
        balances.put(uuid, getBalance(uuid) + amount);
    }

    @Override
    public boolean subtractBalance(UUID uuid, int amount) {
        int current = getBalance(uuid);
        if (current >= amount) {
            balances.put(uuid, current - amount);
            return true;
        }
        return false;
    }
}
