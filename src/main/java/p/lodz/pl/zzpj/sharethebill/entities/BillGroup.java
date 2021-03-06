package p.lodz.pl.zzpj.sharethebill.entities;

import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class BillGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String name;

    private String currencyCode = "EUR";

    @NotNull
    private Boolean isActive;

    @ManyToMany(
            cascade = CascadeType.ALL)
//    @LazyCollection(LazyCollectionOption.FALSE)
    private List<User> members = new ArrayList<>();

    @OneToMany(
            mappedBy = "billGroup",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
//    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Purchase> purchases = new ArrayList<>();

    public BillGroup(String name,String currencyCode, Boolean isActive) {
        this.name = name;
        this.currencyCode = currencyCode;
        this.isActive = isActive;
    }

    public BillGroup() {

    }

    public BillGroup(Long id, String name,String currencyCode, Boolean isActive) {
        this.id = id;
        this.name = name;
        this.currencyCode = currencyCode;
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
