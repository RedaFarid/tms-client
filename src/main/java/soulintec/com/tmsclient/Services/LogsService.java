package soulintec.com.tmsclient.Services;

import javafx.collections.FXCollections;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import soulintec.com.tmsclient.Entities.ClientDTO;
import soulintec.com.tmsclient.Entities.LogDTO;
import soulintec.com.tmsclient.Graphics.Controls.Utilities;
import soulintec.com.tmsclient.Graphics.Windows.ClientsWindow.ClientView;
import soulintec.com.tmsclient.Graphics.Windows.MainWindow.MainWindow;
import soulintec.com.tmsclient.Graphics.Windows.TruckWindow.TruckView;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class LogsService {

    private final RestTemplate restTemplate = new RestTemplate();

    public String save(LogDTO logDTO) {
        //set User here
        ResponseEntity<String> deleteResponseEntity = restTemplate.postForEntity(Utilities.iP +"/saveLog",logDTO, String.class);
        return deleteResponseEntity.getBody();
    }

    public List<LogDTO> findAll() {
        Logs body= new Logs();
        List<LogDTO> logDTOS = FXCollections.observableArrayList();
        try{
            ResponseEntity<Logs> forEntity = restTemplate.getForEntity(Utilities.iP +"/logs", Logs.class);
            body = forEntity.getBody();

        }catch (Exception e){
            MainWindow.showErrorWindow("Error loading data" , e.getMessage());
        }
        if (body.getException() == null){
            return body.getLogs();
        }
        else {
            MainWindow.showErrorWindow("Error loading data" , body.getException().getMessage());
            return logDTOS;
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Logs {
        private List<LogDTO> logs;
        private Exception exception;
    }
}
