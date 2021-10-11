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
import soulintec.com.tmsclient.Entities.DriverDTO;
import soulintec.com.tmsclient.Entities.LogDTO;
import soulintec.com.tmsclient.Graphics.Controls.Utilities;
import soulintec.com.tmsclient.Graphics.Windows.LogsWindow.LogIdentifier;
import soulintec.com.tmsclient.Graphics.Windows.MainWindow.MainWindow;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class DriverService {

    @Autowired
    private LogsService logsService;

    private final RestTemplate restTemplate = new RestTemplate();

    public String save(DriverDTO driverDTO) {
        try {
            driverDTO.setOnTerminal(InetAddress.getLocalHost().getHostName());
        } catch (UnknownHostException e) {
            driverDTO.setOnTerminal("Unknown computer");
            logsService.save(new LogDTO(LogIdentifier.Error, "Computer name",e.getMessage() ));
            log.error("Can't get computer name");
        }
        ResponseEntity<String> saveResponseEntity = restTemplate.postForEntity(Utilities.iP +"/saveDriver",driverDTO, String.class);
        return saveResponseEntity.getBody();
    }

    public List<DriverDTO> findAll() {

        Drivers body= new Drivers();
        List<DriverDTO> driverDTOS = FXCollections.observableArrayList();
        try{
            ResponseEntity<Drivers> forEntity = restTemplate.getForEntity(Utilities.iP +"/drivers", Drivers.class);
            body = forEntity.getBody();

        }catch (Exception e){
            MainWindow.showErrorWindow("Error loading data" , e.getMessage());
        }
        if (body.getException() == null){
            return body.getDrivers();
        }
        else {
            MainWindow.showErrorWindow("Error loading data" , body.getException().getMessage());
            return driverDTOS;
        }
    }

    public Optional<DriverDTO> findById(Long id) {
        ResponseEntity<DriverDTO> forEntity = restTemplate.getForEntity(Utilities.iP +"/driverById/"+id, DriverDTO.class);

        return Optional.ofNullable(forEntity.getBody());
    }

    public Optional<DriverDTO> findByLicenceId(String licenceId) {
        ResponseEntity<DriverDTO> forEntity = restTemplate.getForEntity(Utilities.iP +"/driverByLicenceId/"+licenceId, DriverDTO.class);

        return Optional.ofNullable(forEntity.getBody());
    }

    public String deleteById(Long id) {
        ResponseEntity<String> deleteResponseEntity = restTemplate.postForEntity(Utilities.iP +"/deleteDriverById",id,  String.class);
        return deleteResponseEntity.getBody();
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Drivers{
        private List<DriverDTO> drivers;
        private Exception exception;
    }

    @Override
    public String toString() {
        return "Driver Service";
    }
}
