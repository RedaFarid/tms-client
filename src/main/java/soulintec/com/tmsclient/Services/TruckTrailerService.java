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
import soulintec.com.tmsclient.Entities.LogDTO;
import soulintec.com.tmsclient.Entities.TruckTrailerDTO;
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
public class TruckTrailerService {

    @Autowired
    private LogsService logsService;

    private final RestTemplate restTemplate = new RestTemplate();

    public String save(TruckTrailerDTO truckTrailerDTO) {
        try {
            truckTrailerDTO.setOnTerminal(InetAddress.getLocalHost().getHostName());
        } catch (UnknownHostException e) {
            truckTrailerDTO.setOnTerminal("Unknown computer");
            logsService.save(new LogDTO(LogIdentifier.Error, "Computer name", e.getMessage()));
            log.error("Can't get computer name");
        }
        ResponseEntity<String> saveResponseEntity = restTemplate.postForEntity(Utilities.iP + "/saveTruckTrailers", truckTrailerDTO, String.class);
        return saveResponseEntity.getBody();
    }

    public List<TruckTrailerDTO> findAll() {
        TruckTrailers body = new TruckTrailers();
        List<TruckTrailerDTO> truckTrailerDTOS = FXCollections.observableArrayList();
        try {
            ResponseEntity<TruckTrailers> forEntity = restTemplate.getForEntity(Utilities.iP + "/truckTrailers", TruckTrailers.class);
            body = forEntity.getBody();

        } catch (Exception e) {
            MainWindow.showErrorWindow("Error loading data", e.getMessage());
        }

        if (body.getException() == null) {
            return body.getTruckTrailers();

        } else {
            MainWindow.showErrorWindow("Error loading data", body.getException().getMessage());
            return truckTrailerDTOS;
        }


    }

    public Optional<TruckTrailerDTO> findById(Long id) {
        ResponseEntity<TruckTrailerDTO> forEntity = restTemplate.getForEntity(Utilities.iP + "/truckTrailerById/" + id, TruckTrailerDTO.class);
        return Optional.ofNullable(forEntity.getBody());
    }

    public Optional<TruckTrailerDTO> findByLicenceNo(String licenceNo) {
        ResponseEntity<TruckTrailerDTO> forEntity = restTemplate.getForEntity(Utilities.iP + "/truckTrailersByLicenceNo/" + licenceNo, TruckTrailerDTO.class);
        return Optional.ofNullable(forEntity.getBody());
    }

    public Optional<TruckTrailerDTO> findByTrailerNo(String trailerNo) {
        ResponseEntity<TruckTrailerDTO> forEntity = restTemplate.getForEntity(Utilities.iP + "/truckTrailersByTrailerNo/" + trailerNo, TruckTrailerDTO.class);
        return Optional.ofNullable(forEntity.getBody());
    }

    public String deleteById(Long id) {
        ResponseEntity<String> deleteResponseEntity = restTemplate.postForEntity(Utilities.iP + "/deleteTruckTrailerById", id, String.class);
        return deleteResponseEntity.getBody();
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TruckTrailers {
        private List<TruckTrailerDTO> truckTrailers;
        private Exception exception;
    }
}
