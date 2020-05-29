package ru.melandra.weather.datasources;

import java.util.ArrayList;
import java.util.List;

public class WeatherDaySource implements WeatherDayDataSource
{
    private List<WeatherDayData> dataSource;

    public WeatherDaySource () {
        this.dataSource = new ArrayList ( 3 );
    }

    @Override
    public WeatherDayData get ( int position )
    {
        return dataSource.get ( position );
    }

    @Override
    public int size ()
    {
        return dataSource.size ();
    }

    public WeatherDaySource init () {
//        dataSource.add ( new WeatherDayData ("27 апреля", 20, 33, 755, 10, "NE", 3  ));
//        dataSource.add ( new WeatherDayData ("28 апреля", 19, 35, 753, 12, "N", 4  ));
//        dataSource.add ( new WeatherDayData ("29 апреля", 23, 20, 765, 7, "NW", 2  ));
        return this;
    }

}
