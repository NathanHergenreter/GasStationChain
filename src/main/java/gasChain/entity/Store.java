package gasChain.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Inheritance
public abstract class Store {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

//    @Column(columnDefinition = "Decimal(11,8)")
//    @NotNull
//    private double longitude;
//
//    @Column(columnDefinition = "Decimal(10,8)")
//    @NotNull
//    private double latitude;
//
//	@NotNull
	private String name;
	private String location;
	private String state;
	private String region;

	protected Store() {
	}

//    public Store(@NotNull double longitude, @NotNull double latitude, String name) {
//        this.longitude = longitude;
//        this.latitude = latitude;
//        this.name = name;
//    }

	public Store(String location, String state, String region) {
		this.location = location;
		this.state = state;
		this.region = region;
	}

	public Long getId() {
		return id;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

//    public double getLongitude() {
//        return longitude;
//    }
//
//    public void setLongitude(double longitude) {
//        this.longitude = longitude;
//    }
//
//    public double getLatitude() {
//        return latitude;
//    }
//
//    public void setLatitude(double latitude) {
//        this.latitude = latitude;
//    }
//
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
