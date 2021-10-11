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
import soulintec.com.tmsclient.Entities.MaterialDTO;
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
        ResponseEntity<String> deleteResponseEntity = restTemplate.postForEntity(Utilities.iP + "/saveMaterial", materialDTO, String.class);
        return deleteResponseEntity.getBody();
    }

    public List<MaterialDTO> findAll() {
        Materials body = new Materials();
        List<MaterialDTO> materials = FXCollections.observableArrayList();
        try {
            ResponseEntity<Materials> forEntity = restTemplate.getForEntity(Utilities.iP + "/material", Materials.class);
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
        ResponseEntity<MaterialDTO> forEntity = restTemplate.getForEntity(Utilities.iP + "/materialByName/" + name, MaterialDTO.class);

        return Optional.ofNullable(forEntity.getBody());
    }

    public Optional<MaterialDTO> findById(Long id) {
        ResponseEntity<MaterialDTO> forEntity = restTemplate.getForEntity(Utilities.iP + "/materialByID/" + id, MaterialDTO.class);

        return Optional.ofNullable(forEntity.getBody());
    }

    public String deleteById(Long id) {
        ResponseEntity<String> deleteResponseEntity = restTemplate.postForEntity(Utilities.iP + "/deleteMaterialByID", id, String.class);
        return deleteResponseEntity.getBody();
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Materials {
        private List<MaterialDTO> materials;
        private Exception exception;
    }
}
