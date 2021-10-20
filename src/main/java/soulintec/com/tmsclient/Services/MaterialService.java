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
import soulintec.com.tmsclient.Entities.MaterialDTO;
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
public class MaterialService {

    @Autowired
    private LogsService logsService;

    private final RestTemplate restTemplate = new RestTemplate();

    public String save(MaterialDTO materialDTO) {
        try {
            materialDTO.setOnTerminal(InetAddress.getLocalHost().getHostName());
        } catch (UnknownHostException e) {
            materialDTO.setOnTerminal("Unknown computer");
            logsService.save(new LogDTO(LogIdentifier.Error, "Computer name", e.getMessage()));
            log.error("Can't get computer name");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + LoginService.getToken());
        HttpEntity request = new HttpEntity(materialDTO, headers);

        try {
            ResponseEntity<String> saveResponseEntity = restTemplate.exchange(Utilities.iP + "/saveMaterial", HttpMethod.POST, request, String.class);
            return saveResponseEntity.getBody();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public List<MaterialDTO> findAll() {
        Materials body = new Materials();
        List<MaterialDTO> materials = FXCollections.observableArrayList();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + LoginService.getToken());
        HttpEntity request = new HttpEntity(headers);

        try {
            ResponseEntity<Materials> forEntity = restTemplate.exchange(Utilities.iP + "/material", HttpMethod.GET, request, Materials.class);
            body = forEntity.getBody();

        } catch (Exception e) {
            MainWindow.showErrorWindow("Error loading data", e.getMessage());
        }
        if (body.getException() == null) {
            return body.getMaterials();
        } else {
            MainWindow.showErrorWindow("Error loading data", body.getException().getMessage());
            return materials;
        }
    }

    public Optional<MaterialDTO> findByName(String name) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + LoginService.getToken());
        HttpEntity request = new HttpEntity(headers);
        try {
            ResponseEntity<MaterialDTO> forEntity = restTemplate.exchange(Utilities.iP + "/materialByName/" + name, HttpMethod.GET, request, MaterialDTO.class);
            return Optional.ofNullable(forEntity.getBody());
        } catch (Exception e) {
            MainWindow.showErrorWindow("Error loading data", e.getMessage());
        }
        return Optional.ofNullable(null);
    }

    public Optional<MaterialDTO> findById(Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + LoginService.getToken());
        HttpEntity request = new HttpEntity(headers);
        try {
            ResponseEntity<MaterialDTO> forEntity = restTemplate.exchange(Utilities.iP + "/materialByID/" + id, HttpMethod.GET, request, MaterialDTO.class);
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
            ResponseEntity<String> deleteResponseEntity = restTemplate.exchange(Utilities.iP + "/deleteMaterialByID/", HttpMethod.POST, request, String.class);
            return deleteResponseEntity.getBody();
        } catch (Exception e) {
            return (e.getMessage());
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Materials {
        private List<MaterialDTO> materials;
        private Exception exception;
    }
}
