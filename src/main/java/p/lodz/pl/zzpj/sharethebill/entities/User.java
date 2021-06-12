package p.lodz.pl.zzpj.sharethebill.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import p.lodz.pl.zzpj.sharethebill.model.UserRole;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(unique = true)
    private String login;

    @NotNull
    @Column(unique = true)
    private String email;

    @NotNull
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToMany(
            mappedBy = "sponsor",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Purchase> purchases = new ArrayList<>();


    public User(Long id, String login, String email, UserRole role) {
        this.id = id;
        this.login = login;
        this.email = email;
        this.role = role;
    }
    public User(String login, String email, UserRole role) {
        this.login = login;
        this.email = email;
        this.role = role;
    }



}
