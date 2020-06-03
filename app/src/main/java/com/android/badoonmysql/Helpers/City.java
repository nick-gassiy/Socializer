package com.android.badoonmysql.Helpers;

public class City {
    private String name;
    private String region_id;
    private String country_id;
    private String city_id;

    public City() {}

    public void setName(String name) {
        this.name = name;
    }
    public void setRegion_id(String region_id) {
        this.region_id = region_id;
    }
    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }
    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getName() {
        return name;
    }
    public String getRegion_id() {
        return region_id;
    }
    public String getCountry_id() {
        return country_id;
    }
    public String getCity_id() {
        return city_id;
    }
}
