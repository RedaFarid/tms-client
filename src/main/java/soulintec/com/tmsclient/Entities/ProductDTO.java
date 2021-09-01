
package soulintec.com.tmsclient.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO{

    private Long id;
    private String Name;
    private String stockID;
    private double SpecificGravity  ;
    private String Description;

    private double stockQty;

    public ProductDTO(String name, String stockID, double specificGravity, String description) {
        Name = name;
        this.stockID = stockID;
        SpecificGravity = specificGravity;
        Description = description;
    }
}
