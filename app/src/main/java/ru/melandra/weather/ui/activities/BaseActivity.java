package ru.melandra.weather.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import ru.melandra.weather.R;
import ru.melandra.weather.global.Constants;
import ru.melandra.weather.global.GlobalSettings;
import ru.melandra.weather.model.History;

public abstract class BaseActivity extends AppCompatActivity implements Constants
{
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isDarkTheme()) {
            setTheme( R.style.AppDarkTheme );
        } else {
            setTheme ( R.style.AppTheme );
        }
    }

    protected boolean isDarkTheme() {
        return GlobalSettings.getInstance ().isDarkTheme ();
    }

    protected void setDarkTheme(boolean isDarkTheme) {
        GlobalSettings.getInstance ().setDarkTheme ( isDarkTheme );
    }


    public boolean onCreateOptionsMenu( Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
        Intent intent;
        switch ( item.getItemId())
        {
            case R.id.action_settings:
                intent = new Intent ( this, SettingsActivity.class );
                startActivityForResult ( intent, REQUEST_SETTINGS_CODE );
                return true;
            case R.id.action_recently:
                intent = new Intent ( this, RecentlyCitiesActivity.class );
                startActivityForResult ( intent, REQUEST_RECENTLY_CITY_CODE );
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult ( int requestCode, int resultCode, Intent data )
    {
        switch ( requestCode )
        {
            case REQUEST_SETTINGS_CODE:
                recreate ();
                break;
            case REQUEST_RECENTLY_CITY_CODE:
                if ( resultCode == RESULT_OK )
                {
                    GlobalSettings.getInstance ().setCityName ( data.getStringExtra ( cityNameParam ) );
                    recreate ();
                }
                break;
            default:
                super.onActivityResult ( requestCode, resultCode, data );
                break;
        }
    }
}
