package ru.melandra.weather.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = {"city_name" })})
public class History
{
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "city_name")
    public String cityName;

    @ColumnInfo(name = "date")
    public String date;

    @ColumnInfo(name = "temperature")
    public String temperature;
}
