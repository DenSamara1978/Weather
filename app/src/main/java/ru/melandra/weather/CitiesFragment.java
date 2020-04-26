package ru.melandra.weather;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class CitiesFragment extends Fragment implements Constants
{
    private boolean isLandscapeOrientation;
    private ListView cityListVew;

    public CitiesFragment ()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView ( LayoutInflater inflater, ViewGroup container,
                               Bundle savedInstanceState )
    {
        // Inflate the layout for this fragment
        return inflater.inflate ( R.layout.fragment_cities, container, false );
    }

    @Override
    public void onViewCreated ( @NonNull View view, @Nullable Bundle savedInstanceState )
    {
        super.onViewCreated ( view, savedInstanceState );
        cityListVew = view.findViewById ( R.id.cityListView );

        fillCityList ();
    }

    @Override
    public void onActivityCreated ( @Nullable Bundle savedInstanceState )
    {
        super.onActivityCreated ( savedInstanceState );

        isLandscapeOrientation = getResources ().getConfiguration ().orientation == Configuration.ORIENTATION_LANDSCAPE;

        if ( isLandscapeOrientation )
            showCityWeather ( GlobalSettings.getInstance ().getCityName ());
    }

    private void fillCityList ()
    {
        String[] cityNames = getResources ().getStringArray ( R.array.cityNames );
        ArrayAdapter<String> adapter = new ArrayAdapter<String> ( getContext (), android.R.layout.simple_list_item_1, cityNames);

        cityListVew.setAdapter(adapter);

        cityListVew.setOnItemClickListener ( new AdapterView.OnItemClickListener ()
        {
            @Override
            public void onItemClick ( AdapterView< ? > adapterView, View view, int i, long l )
            {
            showCityWeather ((( TextView ) view ).getText().toString());
            }
        } );
    }

    private void showCityWeather ( String cityName )
    {
        GlobalSettings.getInstance ().setCityName ( cityName );
        if ( isLandscapeOrientation ) {
            WeatherFragment detail = (WeatherFragment) getFragmentManager ().findFragmentById ( R.id.weather );
            if (( detail == null ) || ( !detail.getCurrentCityName ().equals ( cityName ))) {
                detail = WeatherFragment.create ( cityName );
                FragmentTransaction ft = getFragmentManager ().beginTransaction ();
                ft.replace ( R.id.weather, detail );
                ft.setTransition ( FragmentTransaction.TRANSIT_FRAGMENT_FADE );
                ft.commit ();
            }
        } else {
            Intent intent = new Intent();
            intent.setClass ( getActivity (), WeatherActivity.class );
            intent.putExtra( cityNameParam, cityName );
            startActivity ( intent );
        }
    }
}
