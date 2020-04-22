package ru.melandra.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements Constants
{
    TextView cityNameLabel;
    ImageButton settingsButton;

    private final static int REQUEST_CODE = 1;

    @Override
    protected void onCreate ( final Bundle savedInstanceState )
    {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );

        cityNameLabel = findViewById ( R.id.cityView );
        Button aboutButton = findViewById ( R.id.contextAboutCityButton );
        ImageButton settingsButton = findViewById ( R.id.settingsButton );

        cityNameLabel.setText ( GlobalSettings.getInstance ().getCityName ());

        aboutButton.setOnClickListener ( new View.OnClickListener ()
        {
            @Override
            public void onClick ( View view )
            {
                Intent intent = new Intent ( Intent.ACTION_VIEW, Uri.parse ("https://ru.wikipedia.org/wiki/" + cityNameLabel.getText ()));
                startActivity ( intent );
            }
        } );

        settingsButton.setOnClickListener ( new View.OnClickListener ()
        {
            @Override
            public void onClick ( View view )
            {
                Intent intent = new Intent ( MainActivity.this, SettingsActivity.class );
                startActivity ( intent );
            }
        } );

        cityNameLabel.setOnClickListener ( new View.OnClickListener ()
        {
            @Override
            public void onClick ( View view )
            {
                Intent intent = new Intent ( MainActivity.this, CitySelectionActivity.class );
                startActivityForResult ( intent, REQUEST_CODE );
            }
        } );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != REQUEST_CODE) {
            super.onActivityResult(requestCode, resultCode, data);
            return;
        }

        if (resultCode == RESULT_OK)
        {
            String cityName = data.getStringExtra ( cityNameIntentParam );
            cityNameLabel.setText ( cityName );
            GlobalSettings.getInstance ().setCityName ( cityName );
        }
    }
}
