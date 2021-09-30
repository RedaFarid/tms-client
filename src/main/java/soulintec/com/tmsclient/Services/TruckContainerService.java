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
import soulintec.com.tmsclient.Entities.TruckContainerDTO;
import soulintec.com.tmsclient.Graphics.Controls.Utilities;
import soulintec.com.tmsclient.Graphics.Windows.TruckWindow.TruckView;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class TruckContainerService {

    private final RestTemplate restTemplate = new RestTemplate();

    public String save(TruckContainerDTO truckContainerDTO) {
        ResponseEntity<String> saveResponseEntity = restTemplate.postForEntity(Utilities.iP +"/saveTruckContainers",truckContainerDTO, String.class);
        return saveResponseEntity.getBody();
    }

    public List<TruckContainerDTO> findAll() {
        TruckContainers body= new TruckContainers();
        List<TruckContainerDTO> truckContainerDTOS = FXCollections.observableArrayList();
        try{
        ResponseEntity<TruckContainers> forEntity = restTemplate.getForEntity(Utilities.iP +"/truckContainers", TruckContainers.class);
        body = forEntity.getBody();

        }catch (Exception e){
            TruckView.showErrorWindow("Error loading data" , e.getMessage());
        }
        if (body.getException() == null){
            return body.getTruckContainer();
        }
        else {
            TruckView.showErrorWindow("Error loading data" , body.getException().getMessage());
            return truckContainerDTOS;
        }

    }

    public Optional<TruckContainerDTO> findById(Long id) {
        ResponseEntity<TruckContainerDTO> forEntity = restTemplate.getForEntity(Utilities.iP +"/truckContainersById/"+id, TruckContainerDTO.class);
        return Optional.ofNullable(forEntity.getBody());
    }

    public Optional<TruckContainerDTO> findByLicenceNo(String  licenceNo) {
        ResponseEntity<TruckContainerDTO> forEntity = restTemplate.getForEntity(Utilities.iP +"/truckContainersByLicenceNo/"+licenceNo, TruckContainerDTO.class);
        return Optional.ofNullable(forEntity.getBody());
    }

    public Optional<TruckContainerDTO> findByContainerNo(String containerNo) {
        ResponseEntity<TruckContainerDTO> forEntity = restTemplate.getForEntity(Utilities.iP +"/truckContainersByContainerNo/"+containerNo, TruckContainerDTO.class);
        return Optional.ofNullable(forEntity.getBody());
    }

    public String deleteById(Long id) {
        ResponseEntity<String> deleteResponseEntity = restTemplate.postForEntity(Utilities.iP +"/deleteTruckContainerById",id,  String.class);
        return deleteResponseEntity.getBody();
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TruckContainers{
        private List<TruckContainerDTO> truckContainer;
        private Exception exception;
    }
}
