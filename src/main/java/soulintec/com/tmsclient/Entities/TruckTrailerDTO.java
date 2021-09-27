
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
public class TruckTrailerDTO {
    private Long id;
    private String TrailerNumber  ;
    private String LicenceNumber  ;
    private LocalDate LicenceExpirationDate  ;
    private String Permissions;
    private String Comment  ;
    private String comment;
    private LocalDateTime creationDate;
    private LocalDateTime modifyDate;
    private String createdBy;
    private String onTerminal;
}
