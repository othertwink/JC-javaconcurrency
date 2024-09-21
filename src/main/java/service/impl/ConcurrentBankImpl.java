package service.impl;
import entity.BankAccount;
import service.ConcurrentBank;

import java.math.BigDecimal;
import java.util.*;

public class ConcurrentBankImpl implements ConcurrentBank {

    private Map<UUID, BankAccount> accounts = new HashMap<>();
    private Object firstLock;
    private Object secondLock;

    @Override
    public Boolean transfer(BankAccount from, BankAccount to, BigDecimal amount) {

        if (amount.compareTo(BigDecimal.ZERO) <= 0 || from.getBalance().compareTo(amount) < 0) {
            return false;
        }

        firstLock=from;
        secondLock=to;
        if (from.getUUID().compareTo(to.getUUID())>0) {
            firstLock=to;
            secondLock=from;
        }

        synchronized (firstLock) {
            synchronized (secondLock) {
                from.withdraw(amount);
                to.deposite(amount);

                updateAccount(from);
                updateAccount(to);
            }
        }
        return true;
    }

    @Override
    public BankAccount createAccount(BigDecimal balance) {
        UUID u = UUID.randomUUID();
        BankAccount a = new BankAccount(u, balance);
        System.out.println(a.getUUID());
        synchronized (accounts) {
            accounts.put(u, a);
            return a;
        }

    }

    @Override
    public BigDecimal getTotalBalance() {
        synchronized (accounts) { // потоки, пытающиеся изменить accounts уйдут в wait на время выполнения
            int c = 0;
            BigDecimal tb = BigDecimal.ZERO;
            for (Map.Entry<UUID, BankAccount> entry : accounts.entrySet()) {
                c++;
                BankAccount a = entry.getValue();
                tb = tb.add(a.getBalance());
            }
            return tb;
        }
    }

    private BankAccount updateAccount(BankAccount account) {
        synchronized (accounts) {
            return accounts.put(account.getUUID(), account);
        }
    }

}
