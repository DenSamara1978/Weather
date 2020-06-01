package ru.melandra.weather.ui.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.melandra.weather.R;
import ru.melandra.weather.global.GlobalSettings;
import ru.melandra.weather.global.HistoryList;
import ru.melandra.weather.model.History;

import static android.app.Activity.RESULT_OK;
import static ru.melandra.weather.global.Constants.cityNameParam;

public class HistoryAdapter  extends RecyclerView.Adapter<HistoryAdapter.ViewHolder>
{
    Activity activity;

    public HistoryAdapter ( Activity activity ) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder ( @NonNull ViewGroup parent, int viewType )
    {
        View v = LayoutInflater.from(parent.getContext())
                .inflate( R.layout.item_history, parent, false);
        v.setOnClickListener ( new View.OnClickListener ()
        {
            @Override
            public void onClick ( View view )
            {
                RecyclerView recyclerView =((RecyclerView)(view.getParent ()));
                int itemPosition = recyclerView.getChildLayoutPosition(view);
                List<History> history = HistoryList.getInstance ().getSource ().getHistory ();
                History historyItem = history.get(itemPosition);
                showCityWeather ( historyItem.cityName );
            }
        } );
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder ( @NonNull HistoryAdapter.ViewHolder holder, int position )
    {
        List<History> history = HistoryList.getInstance ().getSource ().getHistory ();
        History historyItem = history.get(position);

        holder.cityName.setText(historyItem.cityName);
        holder.date.setText(historyItem.date);
        holder.temperature.setText(historyItem.temperature);
    }

    @Override
    public int getItemCount ()
    {
        return (int) HistoryList.getInstance ().getSource ().getHistoryLength ();
    }


    private void showCityWeather ( String cityName )
    {
        GlobalSettings.getInstance ().setCityName ( cityName );
        Intent intent = new Intent();
        intent.putExtra( cityNameParam, cityName );
        activity.setResult ( RESULT_OK, intent );
        activity.finish ();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView cityName;
        TextView date;
        TextView temperature;
        View cardView;

        public ViewHolder ( @NonNull View itemView )
        {
            super ( itemView );
            cardView = itemView;
            cityName = cardView.findViewById(R.id.textHistoryCityName);
            date = cardView.findViewById(R.id.textHistoryDate);
            temperature = cardView.findViewById(R.id.textHistoryTemperature);
        }
    }
}
