package entity;

import java.math.BigDecimal;
import java.util.UUID;

public class BankAccount {

    private UUID uuid;
    private Integer b;

    public BankAccount(UUID uuid, int balance) {
        this.uuid = uuid;
        this.b = balance;
    }

    public Integer withdraw(int amount){
        synchronized (b) {
        if (b-amount<0) throw new IllegalArgumentException();
        System.out.println("w " + " " + amount + " " + this.uuid);
            return b-=amount;
        }
    }

    public Integer deposite(Integer amount) {
        synchronized (b) {
            System.out.println("d " + " " + amount + " " + this.uuid);
            return b+=amount;
        }
    }

    public Integer getBalance() {
        synchronized (b) {
            return this.b;
        }
    }

    public UUID getUUID() {
        return this.uuid;
    }
}
