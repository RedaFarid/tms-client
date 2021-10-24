package soulintec.com.tmsclient.Reporting.ReportsDTO;

public class Clients extends DTO {
    private String id;
    private String name;
    private String officeAddress;
    private String contactName;
    private String telNo;
    private String email;

    public Clients(String id, String name, String officeAddress, String contactName, String telNo, String email) {
        this.id = id;
        this.name = name;
        this.officeAddress = officeAddress;
        this.contactName = contactName;
        this.telNo = telNo;
        this.email = email;
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

    public String getOfficeAddress() {
        return officeAddress;
    }

    public void setOfficeAddress(String officeAddress) {
        this.officeAddress = officeAddress;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
