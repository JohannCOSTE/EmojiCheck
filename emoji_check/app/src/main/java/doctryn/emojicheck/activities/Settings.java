package doctryn.emojicheck.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;

import doctryn.emojicheck.R;
import doctryn.emojicheck.tools.Settings_util;

public class Settings extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parametres);

        ImageView back = (ImageView) findViewById(R.id.back);
        Spinner devices_list = (Spinner) findViewById(R.id.spinner);
        devices_list.setSelection(Settings_util.getDevice()-1);

        Spinner numberOfEmoji = (Spinner) findViewById(R.id.spinner2);
        numberOfEmoji.setSelection(Settings_util.getNumberOfEmoji());

        Switch hideKeyboard = (Switch) findViewById(R.id.hideKeyboard);
        hideKeyboard.setChecked(Settings_util.isHideKeyboard());

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        /* Display loaded settings */
        hideKeyboard.setChecked(Settings_util.isHideKeyboard());
        devices_list.setSelection(Settings_util.getDevice());
        numberOfEmoji.setSelection(Settings_util.getNumberOfEmoji());

        hideKeyboard.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Settings_util.setHideKeyboard(isChecked);
                Settings_util.save("saved_keyboard", isChecked, getApplication());
            }
        });

        devices_list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Settings_util.setDevice(position);
                Settings_util.save("saved_device", position, getApplication());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        numberOfEmoji.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Settings_util.setDevice(position);
                Settings_util.save("saved_nb_emoji", position, getApplication());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });


    }
}