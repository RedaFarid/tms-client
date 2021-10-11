package soulintec.com.tmsclient.Services;

import javafx.collections.FXCollections;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import soulintec.com.tmsclient.Entities.ComputerDTO;
import soulintec.com.tmsclient.Graphics.Controls.Utilities;
import soulintec.com.tmsclient.Graphics.Windows.MainWindow.MainWindow;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class ComputersService {

    @Autowired
    private LogsService logsService;

    private final RestTemplate restTemplate = new RestTemplate();

    public String save(ComputerDTO computerDTO) {

        ResponseEntity<String> deleteResponseEntity = restTemplate.postForEntity(Utilities.iP + "/saveComputer", computerDTO, String.class);
        return deleteResponseEntity.getBody();
    }

    public List<ComputerDTO> findAll() {
        Computers body = new Computers();
        List<ComputerDTO> computerDTOS = FXCollections.observableArrayList();
        try {
            ResponseEntity<Computers> forEntity = restTemplate.getForEntity(Utilities.iP + "/computers", Computers.class);
            body = forEntity.getBody();

        } catch (Exception e) {
            MainWindow.showErrorWindow("Error loading data", e.getMessage());
        }
        if (body.getException() == null) {
            return body.getComputers();
        } else {
            MainWindow.showErrorWindow("Error loading data", body.getException().getMessage());
            return computerDTOS;
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Computers {
        private List<ComputerDTO> computers;
        private Exception exception;
    }

    @Override
    public String toString() {
        return "Computer Service";
    }
}
