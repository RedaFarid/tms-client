
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
    private String LicenceNumber;
    private String Name;
    private LocalDate LicenceExpirationDate;
    private String MobileNumber;
    private String Permissions;
    private String Comment;

    public DriverDTO(String licenceNumber, String name, LocalDate licenceExpirationDate, String mobileNumber, String permissions, String comment) {
        LicenceNumber = licenceNumber;
        Name = name;
        LicenceExpirationDate = licenceExpirationDate;
        MobileNumber = mobileNumber;
        Permissions = permissions;
        Comment = comment;
    }
}
