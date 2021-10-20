package soulintec.com.tmsclient.Services;

import javafx.collections.FXCollections;
import jfxtras.scene.layout.HBox;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import soulintec.com.tmsclient.Entities.ClientDTO;
import soulintec.com.tmsclient.Entities.LogDTO;
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
public class ClientsService {

    @Autowired
    private LogsService logsService;

    private final RestTemplate restTemplate = new RestTemplate();

    public String save(ClientDTO clientDTO) {
        try {
            clientDTO.setOnTerminal(InetAddress.getLocalHost().getHostName());
        } catch (UnknownHostException e) {
            clientDTO.setOnTerminal("Unknown computer");
            logsService.save(new LogDTO(LogIdentifier.Error, "Computer name", e.getMessage()));
            log.error("Can't get computer name");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + LoginService.getToken());
        HttpEntity request = new HttpEntity(clientDTO, headers);

        try {
            ResponseEntity<String> saveResponseEntity = new RestTemplate().exchange(Utilities.iP + "/saveClient", HttpMethod.POST, request, String.class);
            return saveResponseEntity.getBody();
        } catch (Exception e) {
            return e.getMessage();
        }

    }

    public List<ClientDTO> findAll() {
        Clients body = new Clients();
        List<ClientDTO> clientDTOS = FXCollections.observableArrayList();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + LoginService.getToken());
        HttpEntity request = new HttpEntity(headers);
        try {
            ResponseEntity<Clients> forEntity = new RestTemplate().exchange(Utilities.iP + "/clients", HttpMethod.GET, request, Clients.class);
            body = forEntity.getBody();
        } catch (Exception e) {
            MainWindow.showErrorWindow("Error loading data", e.getMessage());
        }
        if (body.getException() == null) {
            return body.getClient();
        } else {
            MainWindow.showErrorWindow("Error loading data", body.getException().getMessage());
            return clientDTOS;
        }
    }

    public Optional<ClientDTO> findById(Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + LoginService.getToken());
        HttpEntity request = new HttpEntity(headers);
        try {
            ResponseEntity<ClientDTO> forEntity = new RestTemplate().exchange(Utilities.iP + "/clientById/" + id, HttpMethod.GET, request, ClientDTO.class);
            return Optional.ofNullable(forEntity.getBody());
        } catch (Exception e) {
            MainWindow.showErrorWindow("Error loading data", e.getMessage());
        }
        return Optional.ofNullable(null);
    }

    public Optional<ClientDTO> findByName(String name) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + LoginService.getToken());
        HttpEntity request = new HttpEntity(headers);
        try {
            ResponseEntity<ClientDTO> forEntity = new RestTemplate().exchange(Utilities.iP + "/clientByName/" + name, HttpMethod.GET, request, ClientDTO.class);
            return Optional.ofNullable(forEntity.getBody());
        } catch (Exception e) {
            MainWindow.showErrorWindow("Error loading data", e.getMessage());
        }
        return Optional.ofNullable(null);
    }

    public String deleteById(Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + LoginService.getToken());
        HttpEntity request = new HttpEntity(id, headers);
        try {
            ResponseEntity<String> deleteResponseEntity = new RestTemplate().exchange(Utilities.iP + "/deleteClientById/", HttpMethod.POST, request, String.class);
            return deleteResponseEntity.getBody();
        } catch (Exception e) {
            return (e.getMessage());
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Clients {
        private List<ClientDTO> client;
        private Exception exception;
    }

    @Override
    public String toString() {
        return "Clients Service";
    }
}
