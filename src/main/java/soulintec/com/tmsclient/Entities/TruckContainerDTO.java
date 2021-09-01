
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

public class TruckContainerDTO {

    private Long id;
    private String ContainerNumber  ;
    private String LicenceNumber  ;
    private LocalDate LicenceExpirationDate  ;
    private double MaximumWeightConstrain ;
    private int CompartementsNumber;
    private LocalDate CalibrationExpirationDate;
    private String Permissions;
    private String Comment  ;

    public TruckContainerDTO(String containerNumber, String licenceNumber, LocalDate licenceExpirationDate, double maximumWeightConstrain, int compartementsNumber, LocalDate calibrationExpirationDate, String permissions, String comment) {
        ContainerNumber = containerNumber;
        LicenceNumber = licenceNumber;
        LicenceExpirationDate = licenceExpirationDate;
        MaximumWeightConstrain = maximumWeightConstrain;
        CompartementsNumber = compartementsNumber;
        CalibrationExpirationDate = calibrationExpirationDate;
        Permissions = permissions;
        Comment = comment;
    }

    public TruckContainerDTO(String LicenceNumber, LocalDate LicenceExpirationDate, double MaximumWeightConstrain, int CompartementsNumber, LocalDate CalibrationExpirationDate, String Permissions, String Comment) {
        this.LicenceNumber = LicenceNumber;
        this.LicenceExpirationDate = LicenceExpirationDate;
        this.MaximumWeightConstrain = MaximumWeightConstrain;
        this.CompartementsNumber = CompartementsNumber;
        this.CalibrationExpirationDate = CalibrationExpirationDate;
        this.Permissions = Permissions;
        this.Comment = Comment;
    }
    
}
