package ru.melandra.weather.global;

import android.app.Application;

import androidx.room.Room;

import ru.melandra.weather.dao.HistoryDao;
import ru.melandra.weather.database.WeatherDatabase;

public class App extends Application
{
    private static App instance;
    private WeatherDatabase db;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        db = Room.databaseBuilder(
                getApplicationContext(),
                WeatherDatabase.class,
                "weather_database")
                .allowMainThreadQueries ()
                .build();
        GlobalSettings.getInstance ().load ();
    }

    public HistoryDao getHistoryDao() {
        return db.getHistoryDao();
    }
}
