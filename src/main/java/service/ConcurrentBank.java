package service;

import entity.BankAccount;


public interface ConcurrentBank {
    Boolean transfer(BankAccount from, BankAccount to, int amount);
    BankAccount createAccount(int balance);
    int getTotalBalance();
}
