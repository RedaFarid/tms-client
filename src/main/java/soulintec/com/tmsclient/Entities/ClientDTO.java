
package soulintec.com.tmsclient.Entities;

import com.google.common.base.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ClientDTO {


    private long ID;
    private String Name;
    private String MainOfficeAdress;
    private String ContactName;
    private String ContactTelNumber;
    private String ContactEmail;

    public ClientDTO(String Name, String MainOfficeAdress, String ContactName, String ContactTelNumber, String ContactEmail) {
        this.Name = Name;
        this.MainOfficeAdress = MainOfficeAdress;
        this.ContactName = ContactName;
        this.ContactTelNumber = ContactTelNumber;
        this.ContactEmail = ContactEmail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientDTO clientDTO = (ClientDTO) o;
        return ID == clientDTO.ID && Objects.equal(Name, clientDTO.Name) && Objects.equal(MainOfficeAdress, clientDTO.MainOfficeAdress) && Objects.equal(ContactName, clientDTO.ContactName) && Objects.equal(ContactTelNumber, clientDTO.ContactTelNumber) && Objects.equal(ContactEmail, clientDTO.ContactEmail);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(ID, Name, MainOfficeAdress, ContactName, ContactTelNumber, ContactEmail);
    }
}
