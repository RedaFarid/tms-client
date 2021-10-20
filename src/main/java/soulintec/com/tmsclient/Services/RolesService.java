package soulintec.com.tmsclient.Services;

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
import soulintec.com.tmsclient.Entities.Authorization.AppUserDTO;
import soulintec.com.tmsclient.Entities.Authorization.RoleDTO;
import soulintec.com.tmsclient.Graphics.Controls.Utilities;
import soulintec.com.tmsclient.Services.GeneralServices.LoggingService.LoginService;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class RolesService {

    @Autowired
    private LogsService logsService;

    private final RestTemplate restTemplate = new RestTemplate();

    public String save(RoleDTO roleDTO) {

        ResponseEntity<String> saveResponseEntity = restTemplate.postForEntity(Utilities.iP + "/saveRole", roleDTO, String.class);
        return saveResponseEntity.getBody();
    }

    public List<RoleDTO> findAll() {
        // create headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + LoginService.getToken());

        // create request
        HttpEntity request = new HttpEntity(headers);

        // make a request
        ResponseEntity<Roles> response = new RestTemplate().exchange(Utilities.iP + "/roles", HttpMethod.GET, request, Roles.class);

        return response.getBody().getRole();

    }

    //TODO-- add exception
    public Optional<RoleDTO> findById(Long id) {
        ResponseEntity<RoleDTO> forEntity = restTemplate.getForEntity(Utilities.iP + "/roleById/" + id, RoleDTO.class);
        return Optional.ofNullable(forEntity.getBody());
    }

    public Optional<RoleDTO> findByUsername(String name) {
        ResponseEntity<RoleDTO> forEntity = restTemplate.getForEntity(Utilities.iP + "/roleByUserName/" + name, RoleDTO.class);
        return Optional.ofNullable(forEntity.getBody());
    }

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
    public static class Roles {
        private List<RoleDTO> role;
        private Exception exception;
    }

    @Override
    public String toString() {
        return "Roles Service";
    }
}
