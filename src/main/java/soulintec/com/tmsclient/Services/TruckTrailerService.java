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
import soulintec.com.tmsclient.Entities.ClientDTO;
import soulintec.com.tmsclient.Entities.LogDTO;
import soulintec.com.tmsclient.Entities.TruckTrailerDTO;
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
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + LoginService.getToken());
        HttpEntity request = new HttpEntity(truckTrailerDTO, headers);

        try {
            ResponseEntity<String> saveResponseEntity = restTemplate.exchange(Utilities.iP + "/saveTruckTrailers", HttpMethod.POST, request, String.class);
            return saveResponseEntity.getBody();
        } catch (Exception e) {
            return e.getMessage();
        }

    }

    public List<TruckTrailerDTO> findAll() {
        TruckTrailers body = new TruckTrailers();
        List<TruckTrailerDTO> truckTrailerDTOS = FXCollections.observableArrayList();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + LoginService.getToken());
        HttpEntity request = new HttpEntity(headers);

        try {
            ResponseEntity<TruckTrailers> forEntity = restTemplate.exchange(Utilities.iP + "/truckTrailers", HttpMethod.GET, request, TruckTrailers.class);
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
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + LoginService.getToken());
        HttpEntity request = new HttpEntity(headers);
        try {
            ResponseEntity<TruckTrailerDTO> forEntity = restTemplate.exchange(Utilities.iP + "/truckTrailerById/" + id, HttpMethod.GET, request, TruckTrailerDTO.class);
            return Optional.ofNullable(forEntity.getBody());
        } catch (Exception e) {
            MainWindow.showErrorWindow("Error loading data", e.getMessage());
        }
        return Optional.ofNullable(null);
    }

    public Optional<TruckTrailerDTO> findByLicenceNo(String licenceNo) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + LoginService.getToken());
        HttpEntity request = new HttpEntity(headers);
        try {
            ResponseEntity<TruckTrailerDTO> forEntity = restTemplate.exchange(Utilities.iP + "/truckTrailersByLicenceNo/" + licenceNo, HttpMethod.GET, request, TruckTrailerDTO.class);
            return Optional.ofNullable(forEntity.getBody());
        } catch (Exception e) {
            MainWindow.showErrorWindow("Error loading data", e.getMessage());
        }
        return Optional.ofNullable(null);
    }

    public Optional<TruckTrailerDTO> findByTrailerNo(String trailerNo) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + LoginService.getToken());
        HttpEntity request = new HttpEntity(headers);
        try {
            ResponseEntity<TruckTrailerDTO> forEntity = restTemplate.exchange(Utilities.iP + "/truckTrailersByTrailerNo/" + trailerNo, HttpMethod.GET, request, TruckTrailerDTO.class);
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
            ResponseEntity<String> deleteResponseEntity = restTemplate.exchange(Utilities.iP + "/deleteTruckTrailerById/", HttpMethod.POST, request, String.class);
            return deleteResponseEntity.getBody();
        } catch (Exception e) {
            return (e.getMessage());
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TruckTrailers {
        private List<TruckTrailerDTO> truckTrailers;
        private Exception exception;
    }
}
