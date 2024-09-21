package service;

import entity.BankAccount;

import java.math.BigDecimal;


public interface ConcurrentBank {
    Boolean transfer(BankAccount from, BankAccount to, BigDecimal amount);
    BankAccount createAccount(BigDecimal balance);
    BigDecimal getTotalBalance();
}
