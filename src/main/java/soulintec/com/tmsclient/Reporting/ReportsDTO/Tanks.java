package soulintec.com.tmsclient.Reporting.ReportsDTO;

public class Tanks extends DTO {
    private String id ;
    private String name;
    private String station;
    private String location;
    private String material;
    private String capacity;
    private String qty;
    private String calcQty;
    private String dateOfQtySet;
    private String userOfQtySet;

    public Tanks(String id, String name, String station, String location, String material, String capacity, String qty, String calcQty, String dateOfQtySet, String userOfQtySet) {
        this.id = id;
        this.name = name;
        this.station = station;
        this.location = location;
        this.material = material;
        this.capacity = capacity;
        this.qty = qty;
        this.calcQty = calcQty;
        this.dateOfQtySet = dateOfQtySet;
        this.userOfQtySet = userOfQtySet;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getCalcQty() {
        return calcQty;
    }

    public void setCalcQty(String calcQty) {
        this.calcQty = calcQty;
    }

    public String getDateOfQtySet() {
        return dateOfQtySet;
    }

    public void setDateOfQtySet(String dateOfQtySet) {
        this.dateOfQtySet = dateOfQtySet;
    }

    public String getUserOfQtySet() {
        return userOfQtySet;
    }

    public void setUserOfQtySet(String userOfQtySet) {
        this.userOfQtySet = userOfQtySet;
    }
}
