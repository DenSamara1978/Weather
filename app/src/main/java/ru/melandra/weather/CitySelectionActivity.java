package ru.melandra.weather;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CitySelectionActivity extends AppCompatActivity implements Constants
{
    ListView cityListVew;

    @Override
    protected void onCreate ( final Bundle savedInstanceState )
    {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.city_selection_layout );

        cityListVew = findViewById ( R.id.cityListView );
        fillCityList ();
    }

    private void fillCityList ()
    {
        final String[] cityNames = new String[] {
                "Москва", "Санкт-Петербург", "Нижний Новгород", "Казань",
                "Ростов-на-Дону", "Волгоград", "Краснодар", "Севастополь",
                "Самара", "Сочи", "Пермь", "Челябинск", "Ектеринбург",
                "Новосибирск", "Красноярск", "Иркутск", "Владивосток"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cityNames);
        cityListVew.setAdapter(adapter);

        cityListVew.setOnItemClickListener ( new AdapterView.OnItemClickListener ()
        {
            @Override
            public void onItemClick ( AdapterView< ? > adapterView, View view, int i, long l )
            {
                Intent intentResult = new Intent();
                intentResult.putExtra( cityNameIntentParam, (( TextView ) view ).getText().toString());
                setResult(RESULT_OK, intentResult);
                finish();
            }
        } );
    }
}
