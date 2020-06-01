package ru.melandra.weather.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import ru.melandra.weather.dao.HistoryDao;
import ru.melandra.weather.model.History;

@Database(entities = {History.class}, version = 1)
public abstract class WeatherDatabase extends RoomDatabase
{
    public abstract HistoryDao getHistoryDao();
}
