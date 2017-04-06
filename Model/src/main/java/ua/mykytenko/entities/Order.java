package ua.mykytenko.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@NamedQuery(name = Order.GET_ALL, query = "SELECT o FROM Order o ORDER BY o.id")
public class Order {
    public static final String GET_ALL = "orders.getAll";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "received", nullable = false)
    private LocalDateTime received = LocalDateTime.now();

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<OrderPosition> orderPositions;

    @Column(name = "paid", nullable = false)
    private boolean paid = false;

    public Order() {
    }

    public Order(Account account, String address, List<OrderPosition> orderPositions) {
        this.account = account;
        this.address = address;
        this.orderPositions = orderPositions;
    }

    public List<OrderPosition> getOrderPositions() {
        return orderPositions;
    }

    public void setOrderPositions(List<OrderPosition> itemList) {
        this.orderPositions = itemList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
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

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public boolean isNew(){
        return id == 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (account != null ? !account.equals(order.account) : order.account != null) return false;
        if (address != null ? !address.equals(order.address) : order.address != null) return false;
        return received != null ? received.equals(order.received) : order.received == null;
    }

    @Override
    public int hashCode() {
        int result = account != null ? account.hashCode() : 0;
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (received != null ? received.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", account=" + account.getName() +
                ", address='" + address + '\'' +
                ", received=" + received +
                ", paid=" + paid + "\n" +
                orderPositions +
                '}';
    }

    public BigDecimal getTotalCost() {
        return orderPositions.stream()
                .map(op -> op.getLaptop().getPrice().multiply(BigDecimal.valueOf(op.getQuantity())))
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }
}
