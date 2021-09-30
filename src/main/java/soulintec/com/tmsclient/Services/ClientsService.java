package soulintec.com.tmsclient.Services;

import javafx.collections.FXCollections;
import jfxtras.scene.layout.HBox;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import soulintec.com.tmsclient.Entities.ClientDTO;
import soulintec.com.tmsclient.Graphics.Controls.Utilities;
import soulintec.com.tmsclient.Graphics.Windows.ClientsWindow.ClientView;
import soulintec.com.tmsclient.Graphics.Windows.TruckWindow.TruckView;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class ClientsService {

    private final RestTemplate restTemplate = new RestTemplate();

    public String save(ClientDTO clientDTO) {
        ResponseEntity<String> deleteResponseEntity = restTemplate.postForEntity(Utilities.iP +"/saveClient",clientDTO, String.class);
        return deleteResponseEntity.getBody();
    }

    public List<ClientDTO> findAll() {
        Clients body= new Clients();
        List<ClientDTO> clientDTOS = FXCollections.observableArrayList();
        try{
            ResponseEntity<Clients> forEntity = restTemplate.getForEntity(Utilities.iP +"/clients", Clients.class);
            body = forEntity.getBody();

        }catch (Exception e){
            ClientView.showErrorWindow("Error loading data" , e.getMessage());
        }
        if (body.getException() == null){
            return body.getClient();
        }
        else {
            TruckView.showErrorWindow("Error loading data" , body.getException().getMessage());
            return clientDTOS;
        }
    }

    public Optional<ClientDTO> findById(Long id) {
        ResponseEntity<ClientDTO> forEntity = restTemplate.getForEntity(Utilities.iP +"/clientById/"+id, ClientDTO.class);

        return Optional.ofNullable(forEntity.getBody());
    }

    public Optional<ClientDTO> findByName(String  name) {
        ResponseEntity<ClientDTO> forEntity = restTemplate.getForEntity(Utilities.iP +"/clientByName/"+name, ClientDTO.class);

        return Optional.ofNullable(forEntity.getBody());
    }

    public String deleteById(Long id) {
        ResponseEntity<String> deleteResponseEntity = restTemplate.postForEntity(Utilities.iP +"/deleteClientById",id,  String.class);
        return deleteResponseEntity.getBody();
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Clients{
        private List<ClientDTO> client;
        private Exception exception;
    }
}
