package p.lodz.pl.zzpj.sharethebill.model;

import lombok.Data;
import p.lodz.pl.zzpj.sharethebill.entities.User;

@Data
public class BillResult {

    private User user;
    private Double charge;
    private User sponsor;

    public BillResult(User user, double paid, User sponsor) {
        this.user = user;
        this.charge = paid;
        this.sponsor = sponsor;
    }
}
