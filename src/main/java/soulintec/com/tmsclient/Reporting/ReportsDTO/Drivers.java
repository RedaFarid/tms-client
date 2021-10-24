package soulintec.com.tmsclient.Reporting.ReportsDTO;

public class Drivers extends DTO {
    private String id;
    private String name;
    private String licenseNumber;
    private String licenseExpiry;
    private String telNo;
    private String permissions;
    private String comment;

    public Drivers(String id, String name, String licenseNumber, String licenseExpiry, String telNo, String permissions, String comment) {
        this.id = id;
        this.name = name;
        this.licenseNumber = licenseNumber;
        this.licenseExpiry = licenseExpiry;
        this.telNo = telNo;
        this.permissions = permissions;
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

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getLicenseExpiry() {
        return licenseExpiry;
    }

    public void setLicenseExpiry(String licenseExpiry) {
        this.licenseExpiry = licenseExpiry;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}