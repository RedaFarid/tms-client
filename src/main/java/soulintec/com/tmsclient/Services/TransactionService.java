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
import soulintec.com.tmsclient.Entities.TransactionDTO;
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
public class TransactionService {

    @Autowired
    private LogsService logsService;

    private final RestTemplate restTemplate = new RestTemplate();

    public String save(TransactionDTO transactionDTO) {
        try {
            transactionDTO.setOnTerminal(InetAddress.getLocalHost().getHostName());
        } catch (UnknownHostException e) {
            transactionDTO.setOnTerminal("Unknown computer");
            logsService.save(new LogDTO(LogIdentifier.Error, "Computer name",e.getMessage() ));
            log.error("Can't get computer name");
        }
        ResponseEntity<String> saveResponseEntity = restTemplate.postForEntity(Utilities.iP +"/saveTransaction",transactionDTO, String.class);
        return saveResponseEntity.getBody();
    }

    public List<TransactionDTO> findAll() {

        Transactions body= new Transactions();
        List<TransactionDTO> TransactionDTOS = FXCollections.observableArrayList();
        try{
            ResponseEntity<Transactions> forEntity = restTemplate.getForEntity(Utilities.iP +"/transactions", Transactions.class);
            body = forEntity.getBody();

        }catch (Exception e){
            MainWindow.showErrorWindow("Error loading data" , e.getMessage());
        }
        if (body.getException() == null){
            return body.getTransaction();
        }
        else {
            MainWindow.showErrorWindow("Error loading data" , body.getException().getMessage());
            return TransactionDTOS;
        }
    }

    public Optional<TransactionDTO> findById(Long id) {
        ResponseEntity<TransactionDTO> forEntity = restTemplate.getForEntity(Utilities.iP +"/transactionById/"+id, TransactionDTO.class);

        return Optional.ofNullable(forEntity.getBody());
    }


    public String deleteById(Long id) {
        ResponseEntity<String> deleteResponseEntity = restTemplate.postForEntity(Utilities.iP +"/deleteTransactionById",id,  String.class);
        return deleteResponseEntity.getBody();
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Transactions {
        private List<TransactionDTO> transaction;
        private Exception exception;
    }

    @Override
    public String toString() {
        return "Driver Service";
    }
}
