package p.lodz.pl.zzpj.sharethebill.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class BillGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Boolean isActive;

    @ManyToMany(
            cascade = CascadeType.ALL)
    private List<User> members = new ArrayList<>();

    @OneToMany(
            mappedBy = "billGroup",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Purchase> purchases = new ArrayList<>();

    public BillGroup(String name, Boolean isActive) {
        this.name = name;
        this.isActive = isActive;
    }

    public BillGroup() {

    }

    public BillGroup(Long id, String name, Boolean isActive) {
        this.id = id;
        this.name = name;
        this.isActive = isActive;
    }

    public void addMember(User newMember) {
        members.add(newMember);
    }

    public void removeMember(Long userId) {
        members.removeIf(x -> x.getId().equals(userId));
    }

    public void registerPurchase(Purchase newPurchase) {
        newPurchase.setBillGroup(this);
        purchases.add(newPurchase);
    }
}
