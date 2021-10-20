package soulintec.com.tmsclient.Entities.Authorization;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class RoleDTO {

    private long id;
    private String name;

    public RoleDTO(String name) {
        this.name = name;
    }
}
