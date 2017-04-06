package ua.mykytenko.businesslayer.impl;

import org.springframework.stereotype.Service;
import ua.mykytenko.businesslayer.BankAccount;
import ua.mykytenko.businesslayer.CardManager;

import java.math.BigDecimal;

@Service
public class CardManagerMockImpl implements CardManager{
    @Override
    public boolean doMoneyTransaction(BankAccount to, BankAccount from, BigDecimal amount) {
        if(to.getId() == 0 || from.getId() == 0 || from.getId() == to.getId() ||
                amount.equals(BigDecimal.ZERO)){
            throw new RuntimeException("transaction failed");
        }
        return true;
    }
}
