
package soulintec.com.tmsclient.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TankDTO {
    private Long id;
    private String name;
    private Long station;
    private double capacity;
    private double qty;
    private LocalDateTime dateOfQtySet;
    private String userOfQtySet;
    private Long materialID;
    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;
    private String createdBy;
    private String onTerminal;
    private double calculatedQty;
    private String lastModifiedBy;

    public Long getMaterialID() {
        if (Objects.isNull(materialID))
            return 0L;
        else
            return materialID;
    }
}
