package soulintec.com.tmsclient.Entities.Authorization;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class AppUserDTO {

    private long userId;
    private String name;
    private String password;

    public AppUserDTO(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
