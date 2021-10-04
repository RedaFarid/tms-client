package soulintec.com.tmsclient.Services;

import javafx.collections.FXCollections;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import soulintec.com.tmsclient.Entities.DriverDTO;
import soulintec.com.tmsclient.Entities.StationDTO;
import soulintec.com.tmsclient.Graphics.Controls.Utilities;
import soulintec.com.tmsclient.Graphics.Windows.DriversWindow.DriversView;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StationService {

    private final RestTemplate restTemplate = new RestTemplate();

    public String save(StationDTO stationDTO) {
        ResponseEntity<String> saveResponseEntity = restTemplate.postForEntity(Utilities.iP + "/saveStations", stationDTO, String.class);
        return saveResponseEntity.getBody();
    }

    public List<StationDTO> findAll() {

        Stations body = new Stations();
        List<StationDTO> stationDTOS = FXCollections.observableArrayList();
        try {
            ResponseEntity<Stations> forEntity = restTemplate.getForEntity(Utilities.iP + "/stations", Stations.class);
            body = forEntity.getBody();

        } catch (Exception e) {
            DriversView.showErrorWindow("Error loading data", e.getMessage());
        }
        if (body.getException() == null) {
            return body.getStations();
        } else {
            DriversView.showErrorWindow("Error loading data", body.getException().getMessage());
            return stationDTOS;
        }
    }

    public Optional<StationDTO> findById(Long id) {
        ResponseEntity<StationDTO> forEntity = restTemplate.getForEntity(Utilities.iP + "/stationsById/" + id, StationDTO.class);

        return Optional.ofNullable(forEntity.getBody());
    }

    public Optional<StationDTO> findByStationName(String stationName) {
        ResponseEntity<StationDTO> forEntity = restTemplate.getForEntity(Utilities.iP + "/stationByName/" + stationName, StationDTO.class);

        return Optional.ofNullable(forEntity.getBody());
    }

    public String deleteById(Long id) {
        ResponseEntity<String> deleteResponseEntity = restTemplate.postForEntity(Utilities.iP + "/deleteStationsById", id, String.class);
        return deleteResponseEntity.getBody();
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Stations {
        private List<StationDTO> stations;
        private Exception exception;
    }
}
