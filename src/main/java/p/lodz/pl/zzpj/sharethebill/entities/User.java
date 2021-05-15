package p.lodz.pl.zzpj.sharethebill.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String login;
    private String role;

    public User(String login, String role) {
        this.login = login;
        this.role = role;
    }
    @OneToMany(
            mappedBy = "sponsor",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Purchase> purchases = new ArrayList<>();

}
