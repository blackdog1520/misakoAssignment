package com.blackdev.misakoassignment.database;

import com.blackdev.misakoassignment.database.RegionData;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface RegionDao {

    @Insert(onConflict = REPLACE)
    void insert(RegionData regionData);

    @Delete
    void reset(List<RegionData> regionData);

    @Query("SELECT * FROM data_table")
    List<RegionData> getAll();

    @Query("UPDATE data_table SET imageFile = :byteArr WHERE CountryName = :country" )
    void updateImage(byte[] byteArr, String country);

    @Query("UPDATE data_table SET FlagLoaded = :flag WHERE CountryName = :country" )
    void updateFlag(boolean flag, String country);

}
