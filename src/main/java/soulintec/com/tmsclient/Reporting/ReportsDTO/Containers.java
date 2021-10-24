package soulintec.com.tmsclient.Reporting.ReportsDTO;

public class Containers extends DTO {
    private String id;
    private String number;
    private String licence;
    private String licenceExpiration;
    private String maxWeight;
    private String permission;
    private String comment;

    public Containers(String id, String number, String licence, String licenceExpiration, String maxWeight, String permission, String comment) {
        this.id = id;
        this.number = number;
        this.licence = licence;
        this.licenceExpiration = licenceExpiration;
        this.maxWeight = maxWeight;
        this.permission = permission;
        this.comment = comment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getLicence() {
        return licence;
    }

    public void setLicence(String licence) {
        this.licence = licence;
    }

    public String getLicenceExpiration() {
        return licenceExpiration;
    }

    public void setLicenceExpiration(String licenceExpiration) {
        this.licenceExpiration = licenceExpiration;
    }

    public String getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(String maxWeight) {
        this.maxWeight = maxWeight;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
