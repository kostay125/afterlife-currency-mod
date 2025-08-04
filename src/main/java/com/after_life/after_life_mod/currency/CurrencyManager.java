package com.after_life.after_life_mod.currency;

import java.util.UUID;

public interface CurrencyManager {
    int getBalance(UUID playerId);
    void addBalance(UUID playerId, int amount);
    boolean removeBalance(UUID playerId, int amount);
}