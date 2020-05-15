package me.caiorocha.viciocraft.balancevip.cache;

import java.util.UUID;

public final class BaltopCache {
    private static UUID uuid;
    private static double balance;

    public static void formatCache(){
        uuid = null;
        balance = 0;
    }

    public static UUID getUuid() {
        return uuid;
    }

    public static void setUuid(UUID uuid) {
        BaltopCache.uuid = uuid;
    }

    public static double getBalance() {
        return balance;
    }

    public static void setBalance(double balance) {
        BaltopCache.balance = balance;
    }
}
