package ru.melandra.weather.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import ru.melandra.weather.global.GlobalSettings;
import ru.melandra.weather.R;

public class SettingsActivity extends BaseActivity
{
    CheckBox fCheckBox;
    CheckBox wCheckBox;
    CheckBox tCheckBox;
    View settingsLayout;

    @Override
    protected void onCreate ( final Bundle savedInstanceState )
    {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.settings_layout );

        fCheckBox = findViewById ( R.id.fahrenheitScaleCheckBox );
        wCheckBox = findViewById ( R.id.windShowCheckBox );
        tCheckBox = findViewById ( R.id.darkThemeCheckBox );
        settingsLayout = findViewById ( R.id.settings_layout );

        restoreGlobalSettings ();
        setControls ();

        findViewById ( R.id.saveSettingsButton ).setOnClickListener ( new View.OnClickListener ()
        {
            @Override
            public void onClick ( View view )
            {
                Snackbar.make(settingsLayout, getString( R.string.save_settings_answer), Snackbar.LENGTH_LONG)
                        .setAction( R.string.yes, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                GlobalSettings.getInstance ().setFahrenheitScale ( fCheckBox.isChecked () );
                                GlobalSettings.getInstance ().setWindShow ( wCheckBox.isChecked () );
                                GlobalSettings.getInstance ().setDarkTheme ( tCheckBox.isChecked ());
                                saveGlobalSettings ();

                                recreate ();

                                Toast.makeText(getApplicationContext (), R.string.settings_are_saved, Toast.LENGTH_LONG).show();
                            }
                        }).show();
           }
        } );
    }

    private void setControls () {
        fCheckBox.setChecked ( GlobalSettings.getInstance ().getFahrenheitScale () );
        wCheckBox.setChecked ( GlobalSettings.getInstance ().getWindShow () );
        tCheckBox.setChecked ( GlobalSettings.getInstance ().isDarkTheme () );
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
