package ua.mykytenko.dto;

import java.time.LocalDateTime;
import java.util.List;

public class OrderDto {
    private int id;
    private AccountDto account;
    private String address;
    private LocalDateTime received;
    private List<OrderPositionDto> orderPositions;
    private boolean paid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AccountDto getAccount() {
        return account;
    }

    public void setAccount(AccountDto account) {
        this.account = account;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDateTime getReceived() {
        return received;
    }

    public void setReceived(LocalDateTime received) {
        this.received = received;
    }

    public List<OrderPositionDto> getOrderPositions() {
        return orderPositions;
    }

    public void setOrderPositions(List<OrderPositionDto> orderPositions) {
        this.orderPositions = orderPositions;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }
}
