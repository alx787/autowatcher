package ru.ath.athautowatcher.data;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "transport")
public class Transport {
    @PrimaryKey
    private int id;
    private String wlnid;
    private String wlnnm;
    private String vehicletype;
    private String grossvehicleweight;
    private String registrationplate;
    private String vin;
    private String brand;
    private String model;
    private String prodyear;
    private String color;
    private String enginemodel;
    private String primaryfueltype;
    private String enginepower;
    private String atinvnom;
    private String atinstalldate;
    private String atwheelformula;
    private String atdepartment;
    private String atautocol;
    private String atlocation;
    private String atbase;
    private String atres;

    @Ignore
    public Transport() {
    }

    public Transport(int id, String wlnid, String wlnnm, String vehicletype, String grossvehicleweight, String registrationplate, String vin, String brand, String model, String prodyear, String color, String enginemodel, String primaryfueltype, String enginepower, String atinvnom, String atinstalldate, String atwheelformula, String atdepartment, String atautocol, String atlocation, String atbase, String atres) {
        this.id = id;
        this.wlnid = wlnid;
        this.wlnnm = wlnnm;
        this.vehicletype = vehicletype;
        this.grossvehicleweight = grossvehicleweight;
        this.registrationplate = registrationplate;
        this.vin = vin;
        this.brand = brand;
        this.model = model;
        this.prodyear = prodyear;
        this.color = color;
        this.enginemodel = enginemodel;
        this.primaryfueltype = primaryfueltype;
        this.enginepower = enginepower;
        this.atinvnom = atinvnom;
        this.atinstalldate = atinstalldate;
        this.atwheelformula = atwheelformula;
        this.atdepartment = atdepartment;
        this.atautocol = atautocol;
        this.atlocation = atlocation;
        this.atbase = atbase;
        this.atres = atres;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWlnid() {
        return wlnid;
    }

    public void setWlnid(String wlnid) {
        this.wlnid = wlnid;
    }

    public String getWlnnm() {
        return wlnnm;
    }

    public void setWlnnm(String wlnnm) {
        this.wlnnm = wlnnm;
    }

    public String getVehicletype() {
        return vehicletype;
    }

    public void setVehicletype(String vehicletype) {
        this.vehicletype = vehicletype;
    }

    public String getGrossvehicleweight() {
        return grossvehicleweight;
    }

    public void setGrossvehicleweight(String grossvehicleweight) {
        this.grossvehicleweight = grossvehicleweight;
    }

    public String getRegistrationplate() {
        return registrationplate;
    }

    public void setRegistrationplate(String registrationplate) {
        this.registrationplate = registrationplate;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getProdyear() {
        return prodyear;
    }

    public void setProdyear(String prodyear) {
        this.prodyear = prodyear;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getEnginemodel() {
        return enginemodel;
    }

    public void setEnginemodel(String enginemodel) {
        this.enginemodel = enginemodel;
    }

    public String getPrimaryfueltype() {
        return primaryfueltype;
    }

    public void setPrimaryfueltype(String primaryfueltype) {
        this.primaryfueltype = primaryfueltype;
    }

    public String getEnginepower() {
        return enginepower;
    }

    public void setEnginepower(String enginepower) {
        this.enginepower = enginepower;
    }

    public String getAtinvnom() {
        return atinvnom;
    }

    public void setAtinvnom(String atinvnom) {
        this.atinvnom = atinvnom;
    }

    public String getAtinstalldate() {
        return atinstalldate;
    }

    public void setAtinstalldate(String atinstalldate) {
        this.atinstalldate = atinstalldate;
    }

    public String getAtwheelformula() {
        return atwheelformula;
    }

    public void setAtwheelformula(String atwheelformula) {
        this.atwheelformula = atwheelformula;
    }

    public String getAtdepartment() {
        return atdepartment;
    }

    public void setAtdepartment(String atdepartment) {
        this.atdepartment = atdepartment;
    }

    public String getAtautocol() {
        return atautocol;
    }

    public void setAtautocol(String atautocol) {
        this.atautocol = atautocol;
    }

    public String getAtlocation() {
        return atlocation;
    }

    public void setAtlocation(String atlocation) {
        this.atlocation = atlocation;
    }

    public String getAtbase() {
        return atbase;
    }

    public void setAtbase(String atbase) {
        this.atbase = atbase;
    }

    public String getAtres() {
        return atres;
    }

    public void setAtres(String atres) {
        this.atres = atres;
    }
}
