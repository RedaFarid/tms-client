package soulintec.com.tmsclient.Reporting.ReportsDTO;

public class Tanks extends DTO {
    private String batchCode;
    private String actionName;
    private String product;
    private String source;
    private String setPoint1;
    private String measure1;
    private String tolerance;
    private String unit;
    private String begin;
    private String end;
    private String productName;

    public Tanks(String batchCode, String actionName, String product, String source, String setPoint1, String measure1, String tolerance, String unit, String begin, String end, String productName) {

        this.batchCode = batchCode;
        this.actionName = actionName;
        this.product = product;
        this.source = source;
        this.setPoint1 = setPoint1;
        this.measure1 = measure1;
        this.tolerance = tolerance;
        this.unit = unit;
        this.begin = begin;
        this.end = end;
        this.productName = productName;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSetPoint1() {
        return setPoint1;
    }

    public void setSetPoint1(String setPoint1) {
        this.setPoint1 = setPoint1;
    }

    public String getMeasure1() {
        return measure1;
    }

    public void setMeasure1(String measure1) {
        this.measure1 = measure1;
    }

    public String getTolerance() {
        return tolerance;
    }

    public void setTolerance(String tolerance) {
        this.tolerance = tolerance;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getBegin() {
        return begin;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
