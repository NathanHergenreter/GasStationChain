package gasChain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Availability {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "cashier_id")
	private Cashier cashier;

	private int sunStart;
	private int sunEnd;
	private int monStart;
	private int monEnd;
	private int tueStart;
	private int tueEnd;
	private int wedStart;
	private int wedEnd;
	private int thrStart;
	private int thrEnd;
	private int friStart;
	private int friEnd;
	private int satStart;
	private int satEnd;

	protected Availability() {
	}

	public Availability(Cashier cashier, int sunStart, int sunEnd, int monStart, int monEnd, int tueStart, int tueEnd,
			int wedStart, int wedEnd, int thrStart, int thrEnd, int friStart, int friEnd, int satStart, int satEnd) {
		this.cashier = cashier;
		this.sunStart = sunStart;
		this.sunEnd = sunEnd;
		this.monStart = monStart;
		this.monEnd = monEnd;
		this.tueStart = tueStart;
		this.tueEnd = tueEnd;
		this.wedStart = wedStart;
		this.wedEnd = wedEnd;
		this.thrStart = thrStart;
		this.thrEnd = thrEnd;
		this.friStart = friStart;
		this.friEnd = friEnd;
		this.satStart = satStart;
		this.satEnd = satEnd;
	}

	public int getSunStart() {
		return sunStart;
	}

	public int getSunEnd() {
		return sunEnd;
	}

	public int getMonStart() {
		return monStart;
	}

	public int getMonEnd() {
		return monEnd;
	}

	public int getTueStart() {
		return tueStart;
	}

	public int getTueEnd() {
		return tueEnd;
	}

	public int getWedStart() {
		return wedStart;
	}

	public int getWedEnd() {
		return wedEnd;
	}

	public int getThrStart() {
		return thrStart;
	}

	public int getThrEnd() {
		return thrEnd;
	}

	public int getFriStart() {
		return friStart;
	}

	public int getFriEnd() {
		return friEnd;
	}

	public int getSatStart() {
		return satStart;
	}

	public int getSatEnd() {
		return satEnd;
	}

}
