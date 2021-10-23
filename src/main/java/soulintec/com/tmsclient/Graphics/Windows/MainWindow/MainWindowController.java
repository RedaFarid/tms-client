package soulintec.com.tmsclient.Graphics.Windows.MainWindow;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import soulintec.com.tmsclient.Entities.Authorization.RoleDTO;
import soulintec.com.tmsclient.Services.RolesService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@Log4j2
public class MainWindowController {
    @Autowired
    private RolesService rolesService;

    public void createWindowAuthorities(List<RoleDTO> authList) {
        authList.forEach(roleDTO -> {
            boolean contains = rolesService.findAll().stream().map(RoleDTO::getName).collect(Collectors.toList()).contains(roleDTO.getName());
            if(!contains){
                rolesService.save(roleDTO);
            }
        });
    }

}
