package ru.melandra.weather.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ru.melandra.weather.R;
import ru.melandra.weather.datasources.WeatherDayData;
import ru.melandra.weather.datasources.WeatherDayDataSource;

public class WeatherDayAdapter  extends RecyclerView.Adapter<WeatherDayAdapter.ViewHolder>
{
    private WeatherDayDataSource dataSource;

    public WeatherDayAdapter ( WeatherDayDataSource dataSource ) {
        this.dataSource = dataSource;
    }

    @NonNull
    @Override
    public WeatherDayAdapter.ViewHolder onCreateViewHolder ( @NonNull ViewGroup parent, int viewType )
    {
        View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.item_weather_day, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder ( @NonNull WeatherDayAdapter.ViewHolder holder, int position )
    {
        holder.setData ( dataSource.get ( position ));
    }

    @Override
    public int getItemCount ()
    {
        return dataSource.size ();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView dateView;
        private TextView temperatureView;
        private TextView humidityView;
        private TextView pressureView;
        private TextView cloudyView;
        private TextView windStrengthView;
        private TextView windDirectionView;

        public ViewHolder ( @NonNull View itemView )
        {
            super ( itemView );

            dateView = itemView.findViewById ( R.id.dateView );
            temperatureView = itemView.findViewById ( R.id.temperatureView );
            humidityView = itemView.findViewById ( R.id.humidityView );
            pressureView = itemView.findViewById ( R.id.pressureView );
            cloudyView = itemView.findViewById ( R.id.cloudyView );
            windStrengthView = itemView.findViewById ( R.id.windStrengthView );
            windDirectionView = itemView.findViewById ( R.id.windDirectionView );
        }

        public void setData ( WeatherDayData data ) {
            dateView.setText ( data.getDate () );
            int temperature = (int)(data.getTemperature ());
            temperatureView.setText ((( temperature < 0 ) ? "-" : "+" ) + temperature + "°C");
            humidityView.setText ( data.getHumidity () + "%" );
            pressureView.setText ( data.getPressure () + " мм рт.ст.");
            cloudyView.setText ( data.getCloudy () + " баллов" );
            windStrengthView.setText ( data.getWindSpeed () + "м/с" );
            windDirectionView.setText ( data.getWindDirection ());
        }
    }
}
