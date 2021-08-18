package soulintec.com.tmsclient.Services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import soulintec.com.tmsclient.Entities.LogDTO;
import soulintec.com.tmsclient.Entities.LogIdentifier;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TanksService {

    private final RestTemplate restTemplate = new RestTemplate();

    public void getTanks() {

        ResponseEntity<Tanks> forEntity = restTemplate.getForEntity("http://192.168.0.7:8086/tank", Tanks.class);

        Tanks body = forEntity.getBody();

//        System.err.println("\n\n\n\n\n");
//
//        body.getLogs().forEach(System.err::println);


        ResponseEntity<LogDTO> logDTOResponseEntity = restTemplate.postForEntity("http://192.168.0.7:8086/addTank", new LogDTO(1L, LogIdentifier.Error, "fdef", "dfmaelfm", "vfsfvs", LocalDateTime.now(), true), LogDTO.class);
        System.out.println(logDTOResponseEntity.getBody());

    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Tanks{
        private List<LogDTO> logs;
    }
}
