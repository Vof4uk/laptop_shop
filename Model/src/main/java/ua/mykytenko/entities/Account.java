package ua.mykytenko.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
@SecondaryTable(name = "user_roles",
        pkJoinColumns = @PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id"))
@NamedQueries({
        @NamedQuery(name = Account.FIND_BY_NAME, query = "SELECT u FROM Account u WHERE u.name=:name"),
        @NamedQuery(name = Account.GET_ALL, query = "SELECT u FROM Account  u ORDER BY u.id")
})
public class Account {

    public static final String GET_ALL = "accounts.getAll";
    public static final String FIND_BY_NAME = "accounts.findByName";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(table = "user_roles", name = "name", updatable = false)
    private UserRole role;

    @Column(name = "name", nullable = false, unique = true, updatable = false)
    private String name;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany(mappedBy = "account")
    @OrderBy("received DESC")
    private Set<Order> orders;

    public Account() {
    }

    public Account(String name, String password, UserRole role) {
        this.role = role;
        this.name = name;
        this.password = password;
    }

    public boolean isNew() {
        return id == 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        return name != null ? name.equals(account.name) : account.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", role=" + role +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
