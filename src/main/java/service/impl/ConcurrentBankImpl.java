package service.impl;
import entity.BankAccount;
import service.ConcurrentBank;

import java.util.*;

public class ConcurrentBankImpl implements ConcurrentBank {

    private Map<UUID, BankAccount> accounts = new HashMap<>();

    @Override
    public Boolean transfer(BankAccount from, BankAccount to, int amount) {

        if (from == null || to == null || amount <= 0 || from.getBalance() < amount) {
            return false;
        }

        from.withdraw(amount);
        to.deposite(amount);

        updateAccount(from);
        updateAccount(to);
        return true;
    }

    @Override
    public BankAccount createAccount(int balance) {
        UUID r = UUID.randomUUID();
        BankAccount a = new BankAccount(r, balance);
        System.out.println(a.getUUID());
        synchronized (accounts) {
            accounts.put(r, a);
            return a;
        }

    }

    @Override
    public int getTotalBalance() {
        int c = 0;
        Integer tb = 0;
        for (Map.Entry<UUID, BankAccount> entry : accounts.entrySet()) {
            c++;
            BankAccount a = entry.getValue();
            tb+=a.getBalance();
        }
        return tb;
    }

    private BankAccount updateAccount(BankAccount account) {
        synchronized (accounts) {
            return accounts.put(account.getUUID(), account);
        }
    }

}
