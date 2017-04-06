package ua.mykytenko.dto;

public class OrderPositionDto {
    private int id;
    private int orderId;
    private LaptopDto laptop;
    private int quantity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public LaptopDto getLaptop() {
        return laptop;
    }

    public void setLaptop(LaptopDto laptop) {
        this.laptop = laptop;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
