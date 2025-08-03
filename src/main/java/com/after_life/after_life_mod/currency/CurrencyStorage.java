package com.after_life.after_life_mod.currency;

import java.util.UUID;

public interface CurrencyStorage {
    int getBalance(UUID uuid);

    void setBalance(UUID uuid, int amount);

    void addBalance(UUID uuid, int amount);

    boolean subtractBalance(UUID uuid, int amount);
}
