
package soulintec.com.tmsclient.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class TruckContainerDTO {
    private Long id;
    private String containerNumber;
    private String licenceNumber;
    private LocalDate licenceExpirationDate;
    private double maximumWeightConstrain;
    private Permissions permissions;
    private String comment;
    private LocalDateTime creationDate;
    private LocalDateTime modifyDate;
    private String createdBy;
    private String onTerminal;


}
