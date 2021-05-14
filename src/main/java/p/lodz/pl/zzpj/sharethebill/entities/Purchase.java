package p.lodz.pl.zzpj.sharethebill.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Double value;

    @Column(nullable = true)
    private String description;

    @ManyToMany(
            cascade = CascadeType.ALL
    )
    private List<User> participants = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "bill_group_id")
    BillGroup billGroup;

    public Purchase(String title, Double value) {
        this.title = title;
        this.value = value;
    }

    public Purchase(String title, Double value, String description) {
        this.title = title;
        this.value = value;
        this.description = description;
    }

    public void addParticipant(User newParticipant) {
        participants.add(newParticipant);
    }

    public void removeParticipant(Long userId) {
        participants.removeIf(x -> x.getId().equals(userId));
    }
}
