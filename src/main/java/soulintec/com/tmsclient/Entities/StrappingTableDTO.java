
package soulintec.com.tmsclient.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StrappingTableDTO{

    private Long id;
    private String tankId;
    private double Level;
    private double Volume;


    public StrappingTableDTO(String tankId, double level, double volume) {
        this.tankId = tankId;
        Level = level;
        Volume = volume;
    }

    public StrappingTableDTO(String tankId) {
        this.tankId = tankId;
    }
}
