package p.lodz.pl.zzpj.sharethebill.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private Double value;


    private String description;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User sponsor;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bill_group_id")
    BillGroup billGroup;

    public Purchase(String title, Double value, User sponsor) {
        this.title = title;
        this.value = value;
        this.sponsor = sponsor;
    }

    public Purchase(String title, Double value, String description) {
        this.title = title;
        this.value = value;
        this.description = description;
    }

    public Purchase() {

    }
}
