package ru.melandra.weather.datasources;

import java.util.List;

import ru.melandra.weather.dao.HistoryDao;
import ru.melandra.weather.model.History;

public class HistorySource
{
    private final HistoryDao historyDao;

    private List<History> historyItems;

    public HistorySource(HistoryDao historyDao){
        this.historyDao = historyDao;
    }

    public List<History> getHistory(){
        if (historyItems == null){
            LoadHistory();
        }
        return historyItems;
    }

    public void LoadHistory(){
        historyItems = historyDao.getWholeHistory();
    }

    public long getHistoryLength(){
        return historyDao.getHistoryLength();
    }

    public void addHistory(History history){
        historyDao.insertHistoryItem(history);
        LoadHistory();
    }

    public void update(History history){
        boolean exist = false;
        if ( historyItems != null )
        {
            for ( History hist : historyItems )
            {
                if ( hist.cityName.equals ( history.cityName ) )
                {
                    exist = true;
                    break;
                }
            }
        }

        if ( exist )
            historyDao.update(history.cityName, history.temperature, history.date);
        else
            historyDao.insertHistoryItem ( history );

        LoadHistory();
    }

    public void removeHistory(long id){
        historyDao.deteleHistoryItemById(id);
        LoadHistory();
    }

    public void clearHistory () {
        historyDao.clear ();
    }
}
