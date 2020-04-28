package ru.melandra.weather.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ru.melandra.weather.global.Constants;
import ru.melandra.weather.global.GlobalSettings;
import ru.melandra.weather.R;

public class SettingsActivity extends AppCompatActivity implements Constants
{
    CheckBox fCheckBox;
    CheckBox wCheckBox;

    @Override
    protected void onCreate ( final Bundle savedInstanceState )
    {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.settings_layout );

        fCheckBox = findViewById ( R.id.fahrenheitScaleCheckBox );
        wCheckBox = findViewById ( R.id.windShowCheckBox );

        restoreGlobalSettings ();
        setControls ();

        findViewById ( R.id.saveSettingsButton ).setOnClickListener ( new View.OnClickListener ()
        {
            @Override
            public void onClick ( View view )
            {
                GlobalSettings.getInstance ().setFahrenheitScale ( fCheckBox.isChecked () );
                GlobalSettings.getInstance ().setWindShow ( wCheckBox.isChecked () );
                saveGlobalSettings ();
                Toast.makeText ( getApplicationContext (), getString( R.string.settings_saved), Toast.LENGTH_SHORT ).show ();
            }
        } );
    }

    private void setControls () {
        fCheckBox.setChecked ( GlobalSettings.getInstance ().getFahrenheitScale () );
        wCheckBox.setChecked ( GlobalSettings.getInstance ().getWindShow () );
    }

    private void restoreGlobalSettings () {
        // Первоначальная установка глобальных параметров
        // В будующем здесь будет установка глобальных параметров из, например, БД
        // Пока на данный момент, глобальные параметры при запуске приложения будут всегда дефолтными
    }

    private void saveGlobalSettings () {
        // Сохранение глобальных настроек в БД
    }
}
