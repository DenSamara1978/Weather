package ru.melandra.weather.ui.activities;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ru.melandra.weather.R;
import ru.melandra.weather.ui.adapters.HistoryAdapter;

public class RecentlyCitiesActivity extends BaseActivity
{
    @Override
    protected void onCreate ( Bundle savedInstanceState )
    {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_recently_citiesactivity );

        fillRecentlyCitiesList ();
    }

    private void fillRecentlyCitiesList ()
    {
        RecyclerView recyclerView = findViewById ( R.id.historyView );
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter ( new HistoryAdapter ( this ) );
    }
}
