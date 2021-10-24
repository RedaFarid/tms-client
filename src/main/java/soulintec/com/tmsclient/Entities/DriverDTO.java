
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
public class DriverDTO {

    private Long id;
    private String licenseNumber;
    private String name;
    private LocalDate licenceExpirationDate;

    private String mobileNumber;
    private Permissions permissions;
    private String comment;
    private LocalDateTime creationDate;
    private LocalDateTime modifyDate;
    private String createdBy;
    private String lastModifiedBy;
    private String onTerminal;

}
