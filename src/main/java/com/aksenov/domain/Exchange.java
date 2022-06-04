package com.aksenov.domain;

import java.util.Map;

public class Exchange {
    private String disclaimer;
    private String license;
    private String base;
    private Map<String, Double> rates;

    public Exchange() {
    }

    public Exchange(String disclaimer, String license, String base, Map<String, Double> rates) {
        this.disclaimer = disclaimer;
        this.license = license;
        this.base = base;
        this.rates = rates;
    }

    public String getDisclaimer() {
        return disclaimer;
    }

    public void setDisclaimer(String disclaimer) {
        this.disclaimer = disclaimer;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Map<String, Double> getRates() {
        return rates;
    }

    public void setRates(Map<String, Double> rates) {
        this.rates = rates;
    }
}
