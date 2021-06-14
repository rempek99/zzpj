package p.lodz.pl.zzpj.sharethebill.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
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
//    @LazyCollection(LazyCollectionOption.FALSE)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!this.getPurchases().containsAll(user.getPurchases()))
            return false;

        return new EqualsBuilder()
                .append(getId(), user.getId())
                .append(getLogin(), user.getLogin())
                .append(getEmail(), user.getEmail())
                .append(getRole(), user.getRole())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getId()).append(getLogin()).append(getEmail()).append(getRole()).toHashCode();
    }

}
