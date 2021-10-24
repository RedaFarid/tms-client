package soulintec.com.tmsclient.Reporting.ReportsDTO;

public class Stations extends DTO {
    private String id;
    private String name;
    private String location;
    private String computerName;
    private String comment;

    public Stations(String id, String name, String location, String computerName, String comment) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.computerName = computerName;
        this.comment = comment;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getComputerName() {
        return computerName;
    }

    public void setComputerName(String computerName) {
        this.computerName = computerName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
