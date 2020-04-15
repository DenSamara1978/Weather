package ru.melandra.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{

    private final String fahrenheitScaleParamName = "GLOBAL_SETTINGS.FAHRENHEIT_SCALE";
    private final String windShowParamName = "GLOBAL_SETTINGS.WIND_SHOW";

    CheckBox fCheckBox;
    CheckBox wCheckBox;

    @Override
    protected void onCreate ( final Bundle savedInstanceState )
    {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.settings_layout );

        fCheckBox = findViewById ( R.id.fahrenheitScaleCheckBox );
        wCheckBox = findViewById ( R.id.windShowCheckBox );

        if ( savedInstanceState == null )
            toastAndLog ( "onCreate() is firstly called." );
        else
        {
            toastAndLog ( "onCreate() is called one more time." );
            restoreGlobalSettings ( savedInstanceState );
            setControls ();
        }

        findViewById ( R.id.saveSettingsButton ).setOnClickListener ( new View.OnClickListener ()
        {
            @Override
            public void onClick ( View view )
            {
                GlobalSettings.getInstance ().setFahrenheitScale ( fCheckBox.isChecked () );
                GlobalSettings.getInstance ().setWindShow ( wCheckBox.isChecked () );
            }
        } );
    }

    private void restoreGlobalSettings ( Bundle state ) {
        if ( state != null )
        {
            GlobalSettings.getInstance ().setFahrenheitScale ( state.getBoolean ( fahrenheitScaleParamName, false ) );
            GlobalSettings.getInstance ().setWindShow ( state.getBoolean ( windShowParamName, false ) );
        }
    }

    private void saveGlobalSettings ( Bundle state ) {
        if ( state != null ) {
            state.putBoolean ( fahrenheitScaleParamName, GlobalSettings.getInstance ().getFahrenheitScale () );
            state.putBoolean ( windShowParamName, GlobalSettings.getInstance ().getWindShow ());
        }
    }

    private void setControls () {
        fCheckBox.setChecked ( GlobalSettings.getInstance ().getFahrenheitScale () );
        wCheckBox.setChecked ( GlobalSettings.getInstance ().getWindShow () );
    }

    @Override
    protected void onSaveInstanceState ( Bundle outState )
    {
        super.onSaveInstanceState ( outState );
        toastAndLog ( "onSaveInstanceState() called." );

        saveGlobalSettings ( outState );
        setControls ();
    }

    @Override
    protected void onRestoreInstanceState ( Bundle savedInstanceState )
    {
        super.onRestoreInstanceState ( savedInstanceState );
        toastAndLog ( "onRestoreInstanceState() called." );

        restoreGlobalSettings ( savedInstanceState );
    }

    @Override
    protected void onStart ()
    {
        super.onStart ();
        toastAndLog ( "onStart() called." );
    }

    @Override
    protected void onResume ()
    {
        super.onResume ();
        toastAndLog ( "onResume() called." );
    }

    @Override
    protected void onRestart ()
    {
        super.onRestart ();
        toastAndLog ( "onRestart() called." );
    }

    @Override
    protected void onPause ()
    {
        super.onPause ();
        toastAndLog ( "onPause() called." );
    }

    @Override
    protected void onStop ()
    {
        super.onStop ();
        toastAndLog ( "onStop() called." );
    }

    @Override
    protected void onDestroy ()
    {
        super.onDestroy ();
        toastAndLog ( "onDestroy() called." );
    }

    private void toastAndLog ( String message ) {
        Toast.makeText ( getApplicationContext (), message, Toast.LENGTH_SHORT ).show ();
        Log.d ( "LIFE_CYCLE", message );
    }
}
