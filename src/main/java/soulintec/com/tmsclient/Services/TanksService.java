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
import soulintec.com.tmsclient.Entities.TankDTO;
import soulintec.com.tmsclient.Graphics.Controls.Utilities;
import soulintec.com.tmsclient.Graphics.Windows.LogsWindow.LogIdentifier;
import soulintec.com.tmsclient.Graphics.Windows.TruckWindow.TruckView;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class TanksService {

    @Autowired
    private LogsService logsService;

    private final RestTemplate restTemplate = new RestTemplate();

    public String save(TankDTO tankDTO) {
        try {
            tankDTO.setOnTerminal(InetAddress.getLocalHost().getHostName());
        } catch (UnknownHostException e) {
            tankDTO.setOnTerminal("Unknown computer");
            logsService.save(new LogDTO(LogIdentifier.Error, "Computer name", e.getMessage()));
            log.error("Can't get computer name");
        }
        ResponseEntity<String> deleteResponseEntity = restTemplate.postForEntity(Utilities.iP + "/saveTank", tankDTO, String.class);
        return deleteResponseEntity.getBody();
    }

    public List<TankDTO> findAll() {
        Tanks body = new Tanks();
        List<TankDTO> tankDTOS = FXCollections.observableArrayList();
        try {
            ResponseEntity<Tanks> forEntity = restTemplate.getForEntity(Utilities.iP + "/tanks", Tanks.class);
            body = forEntity.getBody();

        } catch (Exception e) {
            TruckView.showErrorWindow("Error loading data", e.getMessage());
        }
        if (body.getException() == null) {
            return body.getTank();
        } else {
            TruckView.showErrorWindow("Error loading data", body.getException().getMessage());
            return tankDTOS;
        }
    }

    public Optional<TankDTO> findById(Long id) {
        ResponseEntity<TankDTO> forEntity = restTemplate.getForEntity(Utilities.iP + "/tankById/" + id, TankDTO.class);

        return Optional.ofNullable(forEntity.getBody());
    }

    public Optional<TankDTO> findByNameAndStation(String name, Long station) {
        ResponseEntity<TankDTO> forEntity = restTemplate.getForEntity(Utilities.iP + "/tankByNameAndStation/" + name + "/" + station, TankDTO.class);

        return Optional.ofNullable(forEntity.getBody());
    }

    public String deleteById(Long id) {
        ResponseEntity<String> deleteResponseEntity = restTemplate.postForEntity(Utilities.iP + "/deleteTankById", id, String.class);
        return deleteResponseEntity.getBody();
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Tanks {
        private List<TankDTO> tank;
        private Exception exception;
    }

//    private final RestTemplate restTemplate = new RestTemplate();
//
////    public void getTanks() {
////        ResponseEntity<Tanks> forEntity = restTemplate.getForEntity("http://192.168.0.7:8086/tank", Tanks.class);
////        Tanks body = forEntity.getBody();
//
////        System.err.println("\n\n\n\n\n");
////
////        body.getLogs().forEach(System.err::println);
//
//
////        ResponseEntity<LogDTO> logDTOResponseEntity = restTemplate.postForEntity("http://192.168.0.7:8086/addTank", new LogDTO(1L, LogIdentifier.Error, "fdef", "dfmaelfm", "vfsfvs", LocalDateTime.now(), true), LogDTO.class);
////        System.out.println(logDTOResponseEntity.getBody());
//
////    }
//
//    public long existsByProductId(String text) {
//        return 0;
//    }
//
//    public Collection<TankDTO> findByProductId(String text) {
//        return null;
//    }
//
//    public  List<TankDTO> findAll() {
//        ResponseEntity<Tanks> forEntity = restTemplate.getForEntity(Utilities.iP +"/tank", Tanks.class);
//        Tanks body = forEntity.getBody();
//
//        return body.getTanks();
//    }
//
//    public Optional<TankDTO> findByTankName(String tankId) {
//        return null;
//    }
//
//    public void save(TankDTO tank) {
//
//    }
//
//    public void deleteByTankName(String text) {
//    }
//
//    @Data
//    @AllArgsConstructor
//    @NoArgsConstructor
//    public static class Tanks{
//        private List<TankDTO> tanks;
//    }
}
