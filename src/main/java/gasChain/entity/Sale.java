package gasChain.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Entity
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull
    private float price;

    private Date sellDate;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "gas_station_id")
    private GasStation sellLocation;










}
