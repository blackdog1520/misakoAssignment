package com.blackdev.misakoassignment;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

@Entity(tableName = "data_table")
@TypeConverters({Converters.class})
public class RegionData implements Serializable {


    @ColumnInfo(typeAffinity = ColumnInfo.BLOB,name = "imageFile")
    private byte[] image;

    @NonNull
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "CountryName")
    private String countryName;

    @ColumnInfo(name = "CapitalName")
    private String capitalName;

    @ColumnInfo(name = "RegionName")
    private String regionName;

    @ColumnInfo(name = "SubRegionName")
    private String subRegionName;

    @ColumnInfo(name = "Population")
    private String population;

    @ColumnInfo(name = "Borders")
    private ArrayList<String> borders;

    @ColumnInfo(name = "Languages")
    private ArrayList<String> languages;

    @ColumnInfo(name = "FlagLoaded")
    private boolean flagLoaded;

    public boolean isFlagLoaded() {
        return flagLoaded;
    }

    public void setFlagLoaded(boolean flagLoaded) {
        this.flagLoaded = flagLoaded;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @ColumnInfo(name = "ImageURL")
    private String url;


    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCapitalName() {
        return capitalName;
    }

    public void setCapitalName(String capitalName) {
        this.capitalName = capitalName;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getSubRegionName() {
        return subRegionName;
    }

    public void setSubRegionName(String subRegionName) {
        this.subRegionName = subRegionName;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public ArrayList<String> getBorders() {
        return borders;
    }

    public void setBorders(ArrayList<String> borders) {
        this.borders = borders;
    }

    public ArrayList<String> getLanguages() {
        return languages;
    }

    public void setLanguages(ArrayList<String> languages) {
        this.languages = languages;
    }

}
