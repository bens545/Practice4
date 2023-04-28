package com.benshaner.practice4.models;

import java.io.Serializable;

public class Country implements Serializable {

    /**
     * 3 Letter country code
     */
    private String code;

    /**
     * The country name
     */
    private String name;

    /**
     * The continent the country is on
     */
    private String continent;

    public Country() {
    }

    public Country(String code, String name, String continent) {
        this.code = code;
        this.name = name;
        this.continent = continent;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }
}
