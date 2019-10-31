package gasChain.entity;

import javax.persistence.*;

@Entity
@Inheritance
public abstract class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long _id;

    private String _username;
    private String _password;

    Employee() {
    }

    public Employee(String _username, String password) {
        this._username = _username;
        this._password = password;
    }

    public long getId() { return _id; }

    public String getUsername() {
        return _username;
    }
    public void setUsername(String username) { _username = username; }

    public String getPassword() { return _password; }
    public void setPassword(String password) { _password = password; }

    public String getAuth() {
        return "generic";
    }

    public boolean isAuth(String authority) {
        return getAuth().equals(authority);
    }
}
