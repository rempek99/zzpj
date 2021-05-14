package p.lodz.pl.zzpj.sharethebill.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import p.lodz.pl.zzpj.sharethebill.dtos.BillGroupDto;
import p.lodz.pl.zzpj.sharethebill.services.GroupService;
import p.lodz.pl.zzpj.sharethebill.utils.BillGroupConverter;

import java.util.List;

@RestController
@RequestMapping("group")
public class BillGroupEndpoint {

    @Autowired
    GroupService groupService;

    @GetMapping("example")
    public String createExample(){
        groupService.createExampleGroup();
        return "Example group created";
    }
    @GetMapping("all")
    public List<BillGroupDto> getAll(){
        return BillGroupConverter.toDtoList(groupService.findAll());
    }
}
