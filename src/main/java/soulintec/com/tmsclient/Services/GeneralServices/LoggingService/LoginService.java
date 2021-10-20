package soulintec.com.tmsclient.Services.GeneralServices.LoggingService;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import soulintec.com.tmsclient.Graphics.Controls.Utilities;

import java.util.Map;


@Log4j2
@Service
@RequiredArgsConstructor
public class LoginService {

    RestTemplate restTemplate = new RestTemplate();

    HttpHeaders headers = new HttpHeaders();

    static String token;
    static String error;

    public String login(String username, String password) {
        token = "";
        error = "";
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
        }
        return error;
    }

    public static String getToken() {
        return token;
    }
}
