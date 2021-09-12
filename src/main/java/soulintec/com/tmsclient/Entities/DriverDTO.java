
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
    private String onTerminal;

    public DriverDTO(String licenseNumber, String name, LocalDate licenceExpirationDate, String mobileNumber, Permissions permissions, String comment, LocalDateTime creationDate, LocalDateTime modifyDate, String createdBy, String onTerminal) {
        this.licenseNumber = licenseNumber;
        this.name = name;
        this.licenceExpirationDate = licenceExpirationDate;
        this.mobileNumber = mobileNumber;
        this.permissions = permissions;
        this.comment = comment;
        this.creationDate = creationDate;
        this.modifyDate = modifyDate;
        this.createdBy = createdBy;
        this.onTerminal = onTerminal;
    }
}
