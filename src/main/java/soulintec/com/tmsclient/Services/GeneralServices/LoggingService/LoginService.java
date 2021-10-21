package soulintec.com.tmsclient.Services.GeneralServices.LoggingService;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import soulintec.com.tmsclient.Graphics.Controls.Utilities;
import soulintec.com.tmsclient.UserObeserver.Observer;
import soulintec.com.tmsclient.UserObeserver.Subject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Log4j2
@Service
@RequiredArgsConstructor
public class LoginService {

    RestTemplate restTemplate = new RestTemplate();

    HttpHeaders headers = new HttpHeaders();

    static String token;
    static String error;
    private  UserObject userObject = new UserObject();

    public void addObserver(Observer observer){
        userObject.add(observer);
    }
    public String login(String username, String password) {
        token = "";
        error = "";
        userObject.setUsername("");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED.toString());
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("username", username);
        requestBody.add("password", password);

        HttpEntity formEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> response =
                restTemplate.exchange(Utilities.iP + "/login", HttpMethod.POST, formEntity, Map.class);

        if ((response.getBody()).keySet().contains("Error")) {
            error = response.getBody().get("Error").toString();
        } else {
            token = response.getBody().get("access_Token").toString();
            userObject.setUsername(username);
        }
        return error;
    }

    public static String getToken() {
        return token;
    }

    public  UserObject getUserObject() {
        return userObject;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    public  class UserObject implements Subject {
        private String username = "";

        private List<Observer> observerList = new ArrayList<>();

        public void setUsername(String username) {
            this.username = username;
            notifyAllObservers();
        }

        public String getUsername() {
            return username;
        }

        @Override
        public void add(Observer observer) {
            observerList.add(observer);
        }

        @Override
        public void remove(Observer observer) {
            observerList.remove(observer);
        }

        @Override
        public void notifyAllObservers() {
            for (Observer observer:observerList){
                observer.update(username);
            }

        }
    }
}
