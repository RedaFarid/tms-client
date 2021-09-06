
package soulintec.com.tmsclient.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MaterialDTO implements Comparable<MaterialDTO>, Cloneable, Serializable {

    private Long id;
    private String name;
    private String description;
    private LocalDateTime creationDate;
    private LocalDateTime modifyDate;
    private String createdBy;
    private String onTerminal;

    public MaterialDTO(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public int compareTo(MaterialDTO o) {
        return this.getId() > o.getId() ? 1 : -1;
    }
}
