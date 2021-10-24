
package soulintec.com.tmsclient.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {
    private long id;
    private long material;
    private long station;
    private long tank;
    private long driver;
    private long truckTrailer;
    private long truckContainer;
    private long client;
    private OperationType operationType;
    private Double qty;
    private LocalDateTime dateTime;
    private LocalDateTime creationDate;
    private LocalDateTime modifyDate;
    private String createdBy;
    private String lastModifiedBy;
    private String onTerminal;


}
