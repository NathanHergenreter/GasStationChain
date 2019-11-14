package gasChain.entity;

import gasChain.util.Luhn;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
public class RewardMembershipAccount {

    @Id
    private String Id;
    private String email;
    private String name;
    private String phoneNumber;
    private int rewardsBalance;
    private Date createdOn;

//    @OneToMany(mappedBy = "receipt")
//    private List<Receipt> receipts = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "employee")
    private Employee createBy;

    RewardMembershipAccount() {
    }

    public RewardMembershipAccount(String email, String name, String phoneNumber, Employee createBy) {
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.createBy = createBy;
        this.rewardsBalance = 0;
        this.createdOn = new Date();
        this.Id = Luhn.generateLuhn(15);
        System.out.println(Id);
    }

    public String getId() {
        return Id;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Employee getCreateBy() {
        return createBy;
    }

    public int getRewardsBalance() {
        return rewardsBalance;
    }

    public int addRewardBalance(int addToReward) {
        this.rewardsBalance += addToReward;
        return rewardsBalance;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    @Override
    public String toString() {
        return "RewardMembershipAccount{" +
                "Id='" + Id + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", rewardsBalance=" + rewardsBalance +
                ", createdOn=" + createdOn +
                ", createBy=" + createBy +
                '}';
    }
}
