package ua.mykytenko.entities;

import javax.persistence.*;

@Entity
@Table(name = "order_to_laptop_join")
public class OrderPosition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "laptop_id")
    private Laptop laptop;

   @Column(name = "quantity")
    private int quantity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderPosition that = (OrderPosition) o;

        if (order != null ? !order.equals(that.order) : that.order != null) return false;
        return laptop != null ? laptop.equals(that.laptop) : that.laptop == null;
    }

    @Override
    public int hashCode() {
        int result = order != null ? order.hashCode() : 0;
        result = 31 * result + (laptop != null ? laptop.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "OrderPosition " + order.getId() + "-" + id +
                '|' + laptop.getModel() +
                "| qty=" + quantity +
                "| cost =" + quantity * laptop.getPrice().doubleValue() +"\n";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Laptop getLaptop() {
        return laptop;
    }

    public void setLaptop(Laptop laptop) {
        this.laptop = laptop;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
