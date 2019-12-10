package gasChain.entity;

import javax.persistence.*;

@Entity
@Inheritance
public abstract class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn
    private Tax tax;

    private String username;

    private String password;

    Employee() {
    }

    public Employee(String username, String password) {
        this.username = username;
        this.password = password;
        this.tax = null;
    }

    public Tax getTax() {
        return tax;
    }

    public void setTax(Tax tax) {
        this.tax = tax;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAuth() {
        return "generic";
    }

    public boolean isAuth(String authority) {
        return getAuth().equals(authority);
    }
}
