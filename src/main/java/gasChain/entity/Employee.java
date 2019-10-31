package gasChain.entity;

import javax.persistence.*;

@Entity
@Inheritance
public abstract class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String username;
    private String password;

    Employee() {
    }

    public Employee(String _username, String password) {
        this.username = _username;
        this.password = password;
    }

    public Long getId() { return id; }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) { username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { password = password; }

    public String getAuth() {
        return "generic";
    }

    public boolean isAuth(String authority) {
        return getAuth().equals(authority);
    }
}
