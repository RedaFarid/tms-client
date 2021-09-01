
package soulintec.com.tmsclient.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ClientLocationsDTO {

    private Long ID;
    private Long ClientID;
    private String LocationName;
    private String LocationAdress;
    private String ContactName;
    private String ContactTelNumber;
    private String ContactEmail;

    public ClientLocationsDTO(long ClientID, String LocationName, String LocationAdress, String ContactName, String ContactTelNumber, String ContactEmail) {
        this.ClientID = ClientID;
        this.LocationName = LocationName;
        this.LocationAdress = LocationAdress;
        this.ContactName = ContactName;
        this.ContactTelNumber = ContactTelNumber;
        this.ContactEmail = ContactEmail;
    }

}
