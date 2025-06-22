
import java.io.Serializable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Cao Phuc
 */

public class Ram implements Serializable {

    private String code;
    private String type;
    private int bus;
    private String brand;
    private long quantity;
    private String productionMonthYear;
    private boolean active;

    public Ram() {
    }

    public Ram(String code, String type, int bus, String brand, long quantity, String productionMonthYear) {
        this.code = code;
        this.type = type;
        this.bus = bus;
        this.brand = brand;
        this.quantity = quantity;
        this.productionMonthYear = productionMonthYear;
        this.active = true;
    }

    public String getCode() {
        return code;
    }

    public String getType() {
        return type;
    }

    public int getBus() {
        return bus;
    }

    public String getBrand() {
        return brand;
    }

    public long getQuantity() {
        return quantity;
    }

    public String getProductionMonthYear() {
        return productionMonthYear;
    }

    public boolean isActive() {
        return active;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setBus(int bus) {
        this.bus = bus;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public void setProductionMonthYear(String productionMonthYear) {
        this.productionMonthYear = productionMonthYear;
    }

    public void setActive(String active) {
        this.active = MyTools.MyTool.parseBoolean(active); 
    }

    @Override
    public String toString() {
        return "Ram{" + "code=" + code + ", brand=" + brand + ", type=" + type + ", bus=" + bus + "MHz" + ", quantity=" + quantity + ", productionMonthYear=" + productionMonthYear + ", active=" + active + '}';
    }
    
    public static String parseDate(String inputDate) {
        String result = inputDate;
        return result;
    }
}
    