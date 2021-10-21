package soulintec.com.tmsclient.Services;

import javafx.collections.FXCollections;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import soulintec.com.tmsclient.Entities.Authorization.RoleDTO;
import soulintec.com.tmsclient.Entities.Authorization.RoleRef;
import soulintec.com.tmsclient.Entities.LogDTO;
import soulintec.com.tmsclient.Entities.MaterialDTO;
import soulintec.com.tmsclient.Graphics.Controls.Utilities;
import soulintec.com.tmsclient.Graphics.Windows.LogsWindow.LogIdentifier;
import soulintec.com.tmsclient.Graphics.Windows.MainWindow.MainWindow;
import soulintec.com.tmsclient.Services.GeneralServices.LoggingService.LoginService;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class RoleRefService {

    @Autowired
    private LogsService logsService;

    private final RestTemplate restTemplate = new RestTemplate();

    public String save(RoleRef roleRef) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + LoginService.getToken());
        HttpEntity request = new HttpEntity(roleRef, headers);

        try {
            ResponseEntity<String> saveResponseEntity = restTemplate.exchange(Utilities.iP + "/saveRoleRef", HttpMethod.POST, request, String.class);
            return saveResponseEntity.getBody();
        } catch (Exception e) {
            return e.getMessage();
        }
    }


    public List<RoleRef> findAll() {
//        // create headers
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "Bearer " + LoginService.getToken());
//
//        // create request
//        HttpEntity request = new HttpEntity(headers);
//
//        // make a request
//        ResponseEntity<RoleRefs> response = new RestTemplate().exchange(Utilities.iP + "/roleRefs", HttpMethod.GET, request, RoleRefs.class);
//
//        return response.getBody().getRoleRefs();

        RoleRefs body = new RoleRefs();
        List<RoleRef> appUserDTOS = FXCollections.observableArrayList();
        try {
            ResponseEntity<RoleRefs> forEntity = restTemplate.getForEntity(Utilities.iP + "/roleRefs", RoleRefs.class);
            body = forEntity.getBody();

        } catch (Exception e) {
            MainWindow.showErrorWindow("Error loading data", e.getMessage());
        }
        if (body.getException() == null) {
            return body.getRoleRefs();
        } else {
            MainWindow.showErrorWindow("Error loading data", body.getException().getMessage());
            return appUserDTOS;
        }

    }

//    //TODO-- add exception
//    public Optional<RoleDTO> findById(Long id) {
//        ResponseEntity<RoleDTO> forEntity = restTemplate.getForEntity(Utilities.iP + "/roleById/" + id, RoleDTO.class);
//        return Optional.ofNullable(forEntity.getBody());
//    }
//
//    public Optional<RoleDTO> findByUsername(String name) {
//        ResponseEntity<RoleDTO> forEntity = restTemplate.getForEntity(Utilities.iP + "/roleByUserName/" + name, RoleDTO.class);
//        return Optional.ofNullable(forEntity.getBody());
//    }

    public Optional<RoleDTO> findByName(String name) {
        ResponseEntity<RoleDTO> forEntity = restTemplate.getForEntity(Utilities.iP + "/roleByName/" + name, RoleDTO.class);

        return Optional.ofNullable(forEntity.getBody());
    }

    public String deleteById(Long id) {
        ResponseEntity<String> deleteResponseEntity = restTemplate.postForEntity(Utilities.iP + "/deleteRoleById", id, String.class);
        return deleteResponseEntity.getBody();
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RoleRefs {
        private List<RoleRef> roleRefs;
        private Exception exception;
    }

    @Override
    public String toString() {
        return "Roles Service";
    }
}
