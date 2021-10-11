
package soulintec.com.tmsclient.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ComputerDTO {
    private long id;
    private String name;

    public ComputerDTO(String name) {
        this.name = name;
    }
}
