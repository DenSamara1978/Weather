package ru.melandra.weather.global;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ru.melandra.weather.dao.HistoryDao;
import ru.melandra.weather.datasources.HistorySource;
import ru.melandra.weather.model.History;

public class HistoryList
{
    private volatile static HistoryList instance = null;
    private static final Object monitor = new Object ();

    private HistorySource historySource;
    private SimpleDateFormat dateFormat;

    private HistoryList ()
    {
        HistoryDao dao = App
                .getInstance()
                .getHistoryDao ();
        historySource = new HistorySource (dao);
        dateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
    }

    public static HistoryList getInstance ()
    {
        if ( instance == null )
        {
            synchronized ( monitor )
            {
                if ( instance == null )
                    instance = new HistoryList ();
            }
        }
        return instance;
    }

    public HistorySource getSource () {
        return historySource;
    }

    public void addCity ( String cityName ) {
        History history = new History ();
        history.cityName = cityName;
        history.date = dateFormat.format ( new Date());
        history.temperature = "-";
        historySource.update ( history );
    }

    public void updateCity ( History history ) {
        historySource.update ( history );
    }
}
