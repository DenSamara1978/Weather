package ru.melandra.weather.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ru.melandra.weather.model.History;

@Dao
public interface HistoryDao
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertHistoryItem(History history);

    @Update
    void updateHistoryItem(History history);

    @Query("UPDATE history SET temperature=:temperature, date=:date WHERE city_name=:city_name")
    void update (String city_name, String temperature, String date);

    @Delete
    void deleteHistoryItem( History history);

    @Query("DELETE FROM history WHERE id = :id")
    void deteleHistoryItemById(long id);

    @Query("SELECT * FROM history")
    List<History> getWholeHistory();

    @Query("SELECT * FROM history WHERE id = :id")
    History getHistoryItemById(long id);

    @Query("SELECT COUNT(*) FROM history")
    long getHistoryLength();

    @Query("DELETE FROM history")
    void clear ();
}
