package doctryn.emojicheck.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import doctryn.emojicheck.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import doctryn.emojicheck.tools.Emoji;
import doctryn.emojicheck.tools.Settings_util;

import static doctryn.emojicheck.tools.Emoji.bug;

public class Main extends Activity {
    final static String NONE = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAEgAAABICAMAAABiM0N1AAAABGdBTUEAALGPC/xhBQAAAAFzUkdCAK7OHOkAAAAJcEhZcwAACxMAAAsTAQCanBgAAACTUExURUdwTNoUId8RHt4OHJo5PlEREkcMDN8TIEoMDEULC00QENULGHEiJNZVXuAPHOASHkULC48zN8JMU0UKCtwSH90RHs8UIK5CSNQNGkwPD9gVIdQRHqEnLcQQG7wrM28iJJsnLGQaHKEzOOUTH8sFEc8HFOIOHNQIF94NHNkLGuETIOYhLuxZYulMWN9ETuo2Q9YuOVC1e+0AAAAjdFJOUwCsgLroviUQDoQ7ldX6yfCf4PVqH0b77GZRM+W92O6An5t6g5/MdwAABGNJREFUWMPtmOmOqloQhZlFBEFGceoBENiA+P5Pd6oKbAEZuvv8uTc5i3RiWvJlVVl7Sclx//Qf0crbCzwvijwv7L3VryGCaF5REVzXKDJF4RcwV9hee0JaFG0F90eYw8a8DhXhH/jaHL5flGBmoBFURCjhmwV624w4QMquRVHUpKKgRhFq632H09hBECvqqixLw7ANw4AXdYGoxtRyWfwDU9RlaQc7XW6k7wKjrAiF4hfKc5uysoxVpQ0Qywk1TQFpoWPJul3WbaOirbvAyYlTl7Yur0Ol967iWIAqWk9zpBVycrRj6LKjvN6gOrL0JE1Xx5OdvCgD2VHHb1HWuh+14ic/rxyEnJ2lTNt2vkDRxGfnISXPC0OfskPlixGDi8S88QaRH1bpztx4bKOORttEheXZbRfOc65o6FpMFueaBKqlBT9YFbsWZUUg83UGNsRh9nqOIwKGEWcX1DQFmxFDKRqSZ/vMGBpCzvrjNm5JSMlQEM5zrowRx+GUXT3WpdU2TQF1/1jwAyI/4Nu5Ub8GH5wHmDTNLtr3OHRcdsXILAkEKk6znAwwWesH9FZg5we1YWVp7ofTnAw5LHv4AWmXZih79/kEuijTnAz6nHX8QG0ncmR2m3RICfQ+w0FDPQ7HvTfT3f1W2Tegt/n4zaJS6nC4z2Y8992xThDkazMcqqvH4UIKAdYdbr4BKbN+2IDDKSY1ie+DAHVWRzlNiudFNeBwqokc1gclCBqPXwwXyKmhHwAdIzZwJKZo6Tzuhy724qcBwdUFnVK0dJ7wg6d5hMNxr45O+Sho1XCyCY56xFjpnat3dJT4P/PDcSblU3eM3xMCuWN+pjkugdhn51+fBEr2w/DFtEunOJzXBEsXpPkE6s6ouoFgwWuSwwnEuXfPg3Ih0LlzkDd0+vI0m+RwFHXRpffuKY/7tSEHom6Oc8AWsUEYvvlxnMTJuecHK5vmtJUV/cjQLkkcPy01nHSWszpSZfd+ZKgnsAQ6us9Umec8DJ0GN7zdCRTzxEmIlN9mONAhDPHbMAwVqbEU74kDpGSWw2H6QvYGLxn2cU8I5PPNcCbpLGeDIZ6x2+s3qrZri6OmJwt+hCY0a2kkndd22mLiRT/IYXlWGGPPLqp+6zha4mDawcPdaMqH0qO4JPZnOK6IiQBXJY1/M6vr4KtN8fRjyf5ID61ZXk8+lKmW/UU676dWpzakgGNNmVYV2X5WJ74ueKu9SBFFHEOeeRjXniRQf1d0vc0xfWCyypa1ua1Gk4Ob/0TBOnxwV+7B22/EYxssGFLFEgdIlmQ8y8NJ8H0fj97jHONVl5KlLW1+sLXYHVNJTPP5JMFzHexOa2V5FcVVynigGkrSxkGLkWTne9uxZumAuvvdo9fA8royAn25rOeQA8pGlp88aKl/v8N6KulW+JNfENQQNlhgGbeHKli2JVhyQ/WnP2rABou7tRSQJFy3LUfhfiNV0Zy1ZeGybllrR1NU7vdSVYWk/g3kf64/wHXgJYGJ6L4AAAAASUVORK5CYII=";
    final static String LOAD = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAEgAAABICAMAAABiM0N1AAAABGdBTUEAALGPC/xhBQAAAAFzUkdCAK7OHOkAAAAJcEhZcwAACxMAAAsTAQCanBgAAADeUExURUdwTJaWlr29vAAAAKSkpBMOB5ybmqKemQAAAJ6enolsRAgGAyMZDKiKX6WkoopzUsWeaAAAAMLCwrOVbHNcPTMiC7i4uMaYWaenp2RMLL6OSqSioM/Q0q6urpp5SbmKRltDI1xBHqF+TZGQkJGRkp53Qf///+bj4YqKil1CHPHx7+Dd2vn39P38+5KSkqenp2VIIOzq54JhM3FRJe3byf7ptP7ioObNs/7ywP704tTPy/rs1PzNgNrW05x7TsCoisGAKv3FZte+oNe0g9qaRPCyXImJiP/XhvvUlfa/cVqFvcYAAAAmdFJOUwC76hXqCk0KLkb+U5v+Fv77QvX99I3J/GPl7THuqfSzrvdivvnNKWZEegAAA0hJREFUWMPt2GlX4jAUBuAMtZZFdi0oMIBzTqtdlAKlxBTQVqT9/39obtKyOS5tynzzpS6n6nOSFJPcIPQTjvRbtSjNZrlc/s1ShFweZvC9I9343pQQMp06nrdaAbZcVqtUKkaf6TflazEBVPOdKTAOY5bF8XgyGY3uDzMap4CevCPokBpNEkNRzz5rUTII9W/YaLeabHiWRZDGFJtQbwi5vx8OB0keW+Xs7u1ckmRdVzUjjqZZqqovdFkUbx/f/oiJnr909nBXQFLOUjUljmkAZKkLNSeh88e7XygVpKqaeQip+pOeETJPBf206KdF/JAF09t8PifYUJRMEMbzmT3bQAKbZIFmNpnbs9nGfYGEwZQTQrITujZIAZPW69ABSEbpobYevIDEGgVW6DrOYtHmgDo5K3BnFApcNwxD3/fUXIcDQoUuDhjkhuvX1+fnlt49RzwQancNewuBo3bbiA8CySRbyLdYe/ggkBQMbyWAfK0ROXwQKjQMQlvkaLntLT4IFXIGDkJbkzsoG4Q6skZstVdBWSFU6RGnd/BjXkjq6XgaPfhMUKWnmoQoi56UDarIlqJgbJr7UeKCOjJMaibGimGocoUfajcMOtMa2KLbkfgdwPO/1ojmbANjmGqtWEoPdaL2ABRP/lHvUkMVOXZggBik6zqVOGbI3TbLVEwKLXSuGVJWLcOMIegbhZ70XkrorYAqjd0CiYnBurZIv4q8g7B5IuhkLSInahGxjVNBymkgO9BOAuFgHZwCwnQV8S0tK4TjJduxMkH7lfbZywJFa/8m2kSs+CESzGx7txt5rvFCU9ed7XcjVFqkghBMIzCpNqyNuwFovu0a7Gy8HCzijw/JoNs+FLP9fr9Za7Va0TECLW3HUNtCxT8YDKqXl4NSgo61fC+u+6HMjg4QltVl9ShJDhDEbd0fl+tVVq+zyh+K9iH7MimmgN6dRBxV/lD3C+hLShRF4eMDhNG7A4S8APnUEgUhn79p+T6Mj+f7KxhoOsy74wPWMHiNxteler2e/1QSwamXrva5gOsCrn9yVYKA9FWLWOpxSvXSR4G70KD8F32DMaLJ09dR6uzaRoiG6JtHBxr9oBEil/4Ru4T4Pv2F/3Sg9hcWGCJ+Wgvk2gAAAABJRU5ErkJggg==";
    final static String BUG = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAEgAAABICAMAAABiM0N1AAAAOVBMVEVHcEzcGSDlGiXbGR/gGiPYGB7fHCLoHibJFhbfGSHmGSLoHCfFFBTYGB3sMTbvRkXqJi7zYVjhGiLDx0QyAAAAC3RSTlMAXEN8K9ucHvC9C0zear4AAAFCSURBVFjD7ZdLssMgDAQNBmz8kSD3P2xEkorh1asspFl6DtDFpxlgmu7c+TfbMsYvSpDbxxxJCfJUW1hCRGU/ZiUolDqOyClBywh6HEG72vsfkHrb4gA69aBcSw9a9SAGgRJTv2lRDZpHUFaDHBFC7KY296BZDQqFEWI3tRkitqjNELFFbYaI3YyE+AgEJYaI3dSGiN3Uhojd1C4IsUXtHuQMoKUQRGxRmyBii9oEEVuMJIiPAiogULpA0QSaC0RsUbtAxBa1MWKL2hixRW2Q2KI2RmxRGyO2gM5PVito/SROd358AELYABif2+5nb+Ukru9uS7ZRpfp92JpqRH4jV7NZjn/uH/4Gt7fxT7MB2ugF8vY2eheJx0ztNExtWOzDUiS+49hKO10c27025fMh63yaOXJFxkMSHaJHQpju3PmZJ2JQHtxCjpRqAAAAAElFTkSuQmCC";
    int data = 0;
    boolean bug_oom;
    int column_setting = 0;
    String text = "";
    ProgressBar loading = null;
    ProgressBar loading_2 = null;
    TextView loading_information = null;
    ImageView parameters = null;
    TextView information = null;
    RadioButton all = null;
    RadioGroup radio = null;
    EditText edit = null;
    ArrayList<Map<String, String>> hmaps = Emoji.hmaps;
    Map<String, Integer> version = Emoji.version;
    boolean[] loaded = Emoji.loaded;
    ArrayList<String> list_to_translate = new ArrayList<>();
    ImageView[][] emplacements;
    int number_of_rows = 0;
    int number_of_column = 0;

    /* ************************* ON CREATE ************************* */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);
        Emoji.init(getApplicationContext());

        radio = (RadioGroup) findViewById(R.id.radio);
        information = (TextView) findViewById(R.id.textViewInformation);
        loading = (ProgressBar) findViewById(R.id.progressBar);
        loading_2 = (ProgressBar) findViewById(R.id.progressBar2);
        loading_information = (TextView) findViewById(R.id.textViewChargement);
        edit = (EditText) findViewById(R.id.editText);
        all = (RadioButton) findViewById(R.id.radio_all);
        parameters = (ImageView) findViewById(R.id.settings);

        parameters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Main.this, Settings.class);
                Main.this.startActivity(myIntent);
            }
        });

        new Load().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                text = edit.getText().toString();
                if (!bug_oom) hideView(loading_information);

                if (text.length() > 0) { /* Text field isn't empty */

                    if (before < count){ // Writing
                        list_to_translate.add(text.substring(start));
                        closeKeyboard();
                    }
                    else { // Deletion
                        try {
                            list_to_translate.remove(list_to_translate.size() - 1);
                            if (list_to_translate.size() < number_of_column) for (int i = 0; i < number_of_rows; i++) emplacements[i][list_to_translate.size()].setImageDrawable(null);
                        }catch (ArrayIndexOutOfBoundsException e){
                            edit.setText("");
                            list_to_translate.clear();
                        }
                    }

                    new Display().execute(emplacements);
                }
                else { /* Text field is empty */
                    list_to_translate.clear();
                    for (int i = 0; i < number_of_rows; i++) emplacements[i][0].setImageDrawable(null);
                    information(getString(R.string.version));
                }
            }
        });
    }


    /* ************************* ASYNC TASC ************************* */

    private class Load extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            /* Why not load all at startup? Because there is a lot of emoji to load so
             * it takes time and some devices fail to load all data due to out of memory.
             * So we load them on demand. */

            resetLoading();
            loading(true);
            setRadio(false);
            hideView(edit);

            int i = 0;
            try {
                if(data == 7) {
                    for(i = 0; i < 7 ; i++){
                        Emoji.load(i, getAssets()); // if "All" is selected
                        progress(10);
                    }
                }
                else{
                    i = data; // To be able in both case to detect which index fail to load
                    Emoji.load(i, getAssets()); // if one is selected
                }
            }catch (IOException | OutOfMemoryError e){
                bug_oom = true;
                displayView(loading_information);
                oom();
                bug[i] = true;
            }

            if (text.length() > 0) new Display().execute(emplacements);
            loading(false);
            displayView(edit);
            setRadio(true);
            return null;
        }
    }

    private class Display extends AsyncTask<ImageView[], Void, Void> {
        @Override
        protected Void doInBackground(final ImageView[][] emplacements) {

            /* Display Emoji */
            if(data == 7) for (int row = 0; row < emplacements.length; row++) fill_emplacement(row);
            else fill_emplacement(data);

            /* Display release */
            if(list_to_translate.size() == 1 && Emoji.release){
                Integer v = version.get(list_to_translate.get(0));
                if(v == null) bug(getString(R.string.twoemoji));
                else  version(v);
            }
            else bug(getString(R.string.twoemoji));

            if (!bug_oom) hideView(loading_information);

            return null;
        }

        void fill_emplacement(int row){
            String src;
            int number_of_column_to_display = Math.min(list_to_translate.size(), number_of_column);

            for (int column = 0; column < number_of_column_to_display; column++) {
                String emoji = list_to_translate.get(column);
                src = hmaps.get(row).get(emoji);

                /* Mystery patch */
                if(src == null && (emoji.charAt(emoji.length() - 1) == '\uFE0F')){
                    emoji = emoji.substring(0, emoji.length() - 1);
                    src = hmaps.get(row).get(emoji);
                    list_to_translate.set(column, emoji);
                }

                if (src == null) { // No correspondence
                    if (bug[row]) src = BUG;
                    else if (loaded[row]) src = NONE;
                    else src = LOAD;
                }
                result(emplacements[row][column], URI2Byte(src));
            }
        }

        void result(ImageView v, byte[] decoded) {
            final ImageView mView = v;
            final byte[] mDecoded = decoded;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setImageViewWithByteArray(mView, mDecoded);
                }
            });
        }

        byte[] URI2Byte(String URI) {
            int dataStartIndex = URI.indexOf(",") + 1;
            final String data = URI.substring(dataStartIndex);
            return android.util.Base64.decode(data, Base64.DEFAULT);
        }

        void setImageViewWithByteArray(ImageView view, byte[] data) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            view.setImageBitmap(bitmap);
        }
    }


    /* ************************* OTHER ************************* */

    public void onRadioChecked(View view) {
        /* Hide all emoji in the array */
        for (int i = 0; i < number_of_column; i++) for (int i2 = 0; i2 < number_of_rows; i2++) hideView(emplacements[i2][i]);

        /* Get which radio box is tick */
        data = radio.indexOfChild(findViewById(radio.getCheckedRadioButtonId()));

        /* Display the write row */
        if(data == 7) for (int column = 0; column < number_of_column; column++) for (int row = 0; row < number_of_rows; row++) displayView(emplacements[row][column]);
        else for (int column = 0; column < number_of_column; column++) displayView(emplacements[data][column]);

        /* Not essential but allow to show waiting emoji while loading */
        new Display().execute(emplacements);

        /* Load emoji if not */
        if (!Emoji.completed()) new Load().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void loadSettings() {
        /* Default emoji */
        int temp = (int) Settings_util.load("saved_device", 0, getApplication());
        Settings_util.setDevice(temp);
        radio.check(radio.getChildAt(temp).getId());
        data = temp;

        /* Number of emoji */
        int number_of_column_wanted = (int) Settings_util.load("saved_nb_emoji", 3, getApplication());
        Settings_util.setNumberOfEmoji(number_of_column_wanted);
        column_setting = 7 - (number_of_column_wanted + 1);

        /* Hide keyboard */
        Settings_util.setHideKeyboard((boolean) Settings_util.load("saved_keyboard", false, getApplication()));

        /* Column setting */
        LinearLayout column = (LinearLayout) findViewById(R.id.columns);
        number_of_column = column.getChildCount();
        number_of_rows = ((LinearLayout) column.getChildAt(0)).getChildCount() - 1;
        if (emplacements == null) emplacements = new ImageView[number_of_rows][number_of_column];

        for (int i = 0; i < number_of_column; i++) {
            LinearLayout column2 = (LinearLayout) column.getChildAt(i);
            if (i <= number_of_column_wanted) {
                displayView(column2);
                for (int i2 = 0; i2 < number_of_rows; i2++)
                    emplacements[i2][i] = (ImageView) column2.getChildAt(i2);
            } else removeView(column2);
        }

        number_of_column = number_of_column_wanted + 1;
        onRadioChecked(null);
    }

    /* ************************* UPDATE GUI ************************* */

    public void displayView(final View v) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                v.setVisibility(View.VISIBLE);
            }
        });

    }

    public void removeView(final View v) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                v.setVisibility(View.GONE);
            }
        });

    }

    public void hideView(final View v) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                v.setVisibility(View.INVISIBLE);
            }
        });

    }

    public void resetLoading() {
        if (!Emoji.completed()) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    loading.setMax(Emoji.toLoad() * 10);
                    loading.setProgress(0);
                }
            });

        }
    }

    public void setRadio(final boolean checked) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < radio.getChildCount(); i++) {
                    radio.getChildAt(i).setEnabled(checked);
                }
            }
        });

    }

    public void version(int ver) {
        final String mVer;
        switch (ver) {
            case 1:
                mVer = "Emoji 1.0 (08/2015)";
                break;
            case 2:
                mVer = "Emoji 2.0 (11/2015)";
                break;
            case 3:
                mVer = "Emoji 3.0 (06/2016)";
                break;
            case 4:
                mVer = "Emoji 4.0 (11/2016)";
                break;
            case 5:
                mVer = "Emoji 5.0 (05/2017)";
                break;
            case 11:
                mVer = "Emoji 11.0 (05/2018)";
                break;
            default:
                mVer = "";
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                information.setText(mVer);
            }
        });

        displayView(information);
    }

    public void bug(final String erreur) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                information.setText(erreur);
            }
        });
    }

    public void progress(int progress) {
        final int prog = loading.getProgress() + progress;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loading.setProgress(prog);
            }
        });

    }

    public void loading(boolean bool) {
        if (bool) {
            if (!Emoji.bug()) information(getString(R.string.loading));
            if (data == 7) {
                displayView(loading);
                removeView(loading_2);
            } else {
                displayView(loading_2);
                removeView(loading);
            }
        } else {
            if (data == 7) {
                removeView(loading);
                hideView(loading_2);
            } else {
                removeView(loading_2);
                hideView(loading);
            }
            if (edit.getText().toString().equals("")) information(getString(R.string.version));
        }
    }

    public void information(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                information.setText(text);
            }
        });

    }

    public void loadingInformation(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loading_information.setText(text);
            }
        });
    }

    public void oom() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loadingInformation(getString(R.string.oom));
            }
        });
    }

    public void closeKeyboard() {
        if (Settings_util.isHideKeyboard()) {
            if(list_to_translate.size() > Settings_util.getNumberOfEmoji()) {
                View view = this.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        }
    }

    /* ************************* ON RESUME ************************* */

    @Override
    protected void onResume() {
        super.onResume();
        loadSettings();
    }
}
