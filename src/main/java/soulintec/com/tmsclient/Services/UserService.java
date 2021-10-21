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
import soulintec.com.tmsclient.Entities.Authorization.AppUserDTO;
import soulintec.com.tmsclient.Entities.StationDTO;
import soulintec.com.tmsclient.Graphics.Controls.Utilities;
import soulintec.com.tmsclient.Graphics.Windows.MainWindow.MainWindow;
import soulintec.com.tmsclient.Services.GeneralServices.LoggingService.LoginService;


import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private LogsService logsService;

    private final RestTemplate restTemplate = new RestTemplate();

    public String save(AppUserDTO userDTO) {

        ResponseEntity<String> deleteResponseEntity = restTemplate.postForEntity(Utilities.iP + "/saveUser", userDTO, String.class);
        return deleteResponseEntity.getBody();
    }

    public List<AppUserDTO> findAll() {
//        // create headers
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "Bearer " + LoginService.getToken());
//
//        // create request
//        HttpEntity request = new HttpEntity(/*headers*/);
//
//        // make a request
//        ResponseEntity<Users> response = new RestTemplate().exchange(Utilities.iP + "/users", HttpMethod.GET, request, Users.class);
//
//        return response.getBody().getUser();

        Users body = new Users();
        List<AppUserDTO> appUserDTOS = FXCollections.observableArrayList();
        try {
            ResponseEntity<Users> forEntity = restTemplate.getForEntity(Utilities.iP + "/users", Users.class);
            body = forEntity.getBody();

        } catch (Exception e) {
            MainWindow.showErrorWindow("Error loading data", e.getMessage());
        }
        if (body.getException() == null) {
            return body.getUser();
        } else {
            MainWindow.showErrorWindow("Error loading data", body.getException().getMessage());
            return appUserDTOS;
        }

    }

    //TODO-- add exception
    public Optional<AppUserDTO> findById(Long id) {
        ResponseEntity<AppUserDTO> forEntity = restTemplate.getForEntity(Utilities.iP + "/userById/" + id, AppUserDTO.class);

        return Optional.ofNullable(forEntity.getBody());
    }

    public Optional<AppUserDTO> findByName(String name) {
        ResponseEntity<AppUserDTO> forEntity = restTemplate.getForEntity(Utilities.iP + "/userByName/" + name, AppUserDTO.class);

        return Optional.ofNullable(forEntity.getBody());
    }

    public String deleteById(Long id) {
        ResponseEntity<String> deleteResponseEntity = restTemplate.postForEntity(Utilities.iP + "/deleteUserById", id, String.class);
        return deleteResponseEntity.getBody();
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Users {
        private List<AppUserDTO> user;
        private Exception exception;
    }

    @Override
    public String toString() {
        return "Users Service";
    }
}
