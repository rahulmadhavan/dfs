package edu.rahulk.cs8982.singlefs.models;

import org.apache.commons.lang3.StringEscapeUtils;

import java.io.Serializable;

/**
 * Created by rahulk on 2/9/16.
 */
public class Provider implements Serializable {
    private String row;
    private String providerId;
    private String zipCode;
    private String procedureCode;
    private String nosOfProcedures;
    private String name;
    private String street1;
    private String street2;
    private String city;
    private String gender;
    private String state;


    public Provider() {

    }

    public Provider(String providerId, String zipCode, String procedureCode) {
        this.providerId = providerId;
        this.zipCode = zipCode;
        this.procedureCode = procedureCode;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getProcedureCode() {
        return procedureCode;
    }

    public void setProcedureCode(String procedureCode) {
        this.procedureCode = procedureCode;
    }

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public String getNosOfProcedures() {
        return nosOfProcedures;
    }

    public void setNosOfProcedures(String nosOfProcedures) {
        this.nosOfProcedures = nosOfProcedures;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet1() {
        return street1;
    }

    public void setStreet1(String street1) {
        this.street1 = street1;
    }

    public String getStreet2() {
        return street2;
    }

    public void setStreet2(String street2) {
        this.street2 = street2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(this.getProviderId())
                .append(",")
                .append(this.getName())
                .append(",")
                .append(this.getZipCode())
                .append(",")
                .append(this.getProcedureCode())
                .append(",")
                .append(this.getStreet1())
                .append(",")
                .append(this.getStreet2())
                .append(",")
                .append(this.getCity())
                .append(",")
                .append(this.getNosOfProcedures())
                .append(",")
                .append(this.getGender())
                .append(",")
                .append(this.getState())
                .append(",")
                .append(this.getRow())
                .toString();
    }
}
