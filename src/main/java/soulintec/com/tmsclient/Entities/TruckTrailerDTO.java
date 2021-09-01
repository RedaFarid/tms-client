
package soulintec.com.tmsclient.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Date;
import java.time.LocalDate;

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

    public TruckTrailerDTO(String trailerNumber, String licenceNumber, LocalDate licenceExpirationDate, String permissions, String comment) {
        TrailerNumber = trailerNumber;
        LicenceNumber = licenceNumber;
        LicenceExpirationDate = licenceExpirationDate;
        Permissions = permissions;
        Comment = comment;
    }

    public TruckTrailerDTO(String LicenceNumber, LocalDate LicenceExpirationDate, String Permissions, String Comment) {
        this.LicenceNumber = LicenceNumber;
        this.LicenceExpirationDate = LicenceExpirationDate;
        this.Permissions = Permissions;
        this.Comment = Comment;
    }
    
}
