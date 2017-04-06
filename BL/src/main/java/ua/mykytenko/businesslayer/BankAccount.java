package ua.mykytenko.businesslayer;

public class BankAccount {
    private final long id;

    public BankAccount(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
