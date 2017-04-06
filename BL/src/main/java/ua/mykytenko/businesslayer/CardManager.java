package ua.mykytenko.businesslayer;

import java.math.BigDecimal;

public interface CardManager {
    boolean doMoneyTransaction(BankAccount to, BankAccount from, BigDecimal amount);
}
