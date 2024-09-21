package entity;
import java.math.BigDecimal;
import java.util.UUID;

public class BankAccount {

    private UUID uuid;
    private BigDecimal b;

    public BankAccount(UUID uuid, BigDecimal balance) {
        this.uuid = uuid;
        this.b = balance;
    }

    public BigDecimal withdraw(BigDecimal amount){
        synchronized (b) {
        if (b.subtract(amount).compareTo(BigDecimal.ZERO)<0) throw new IllegalArgumentException();
            b=b.subtract(amount);
            System.out.println("w " + " " + b + " " + this.uuid);
            return b;
        }
    }

    public BigDecimal deposite(BigDecimal amount) {
        synchronized (b) {
            b=b.add(amount);
            System.out.println("d " + " " + b + " " + this.uuid);
            return b;
        }
    }

    public BigDecimal getBalance() {
        synchronized (b) {
            return this.b;
        }
    }

    public UUID getUUID() {
        return this.uuid;
    }
}
