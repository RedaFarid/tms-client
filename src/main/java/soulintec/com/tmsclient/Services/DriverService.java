package soulintec.com.tmsclient.Services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import soulintec.com.tmsclient.Entities.ClientDTO;
import soulintec.com.tmsclient.Entities.DriverDTO;
import soulintec.com.tmsclient.Graphics.Controls.Utilities;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DriverService {

    private final RestTemplate restTemplate = new RestTemplate();

    public String save(DriverDTO driverDTO) {
        ResponseEntity<String> deleteResponseEntity = restTemplate.postForEntity(Utilities.iP +"/saveDriver",driverDTO, String.class);
        return deleteResponseEntity.getBody();
    }

    public List<DriverDTO> findAll() {
        ResponseEntity<Drivers> forEntity = restTemplate.getForEntity(Utilities.iP +"/drivers", Drivers.class);
        Drivers body = forEntity.getBody();
        return body.getDrivers();
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
    }
}
