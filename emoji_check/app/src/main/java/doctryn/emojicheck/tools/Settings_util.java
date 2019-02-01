package doctryn.emojicheck.tools;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class Settings_util {
    private static boolean hideKeyboard = false;
    private static int device = 0;
    private static int numberOfEmoji = 0;

    public static void setHideKeyboard(boolean hideKeyboard) {
        Settings_util.hideKeyboard = hideKeyboard;
    }

    public static boolean isHideKeyboard() {
        return hideKeyboard;
    }

    public static int getDevice() {
        return device;
    }

    public static void setDevice(int device) {
        Settings_util.device = device;
    }

    public static int getNumberOfEmoji() {
        return numberOfEmoji;
    }

    public static void setNumberOfEmoji(int numberOfEmoji) {
        Settings_util.numberOfEmoji = numberOfEmoji;
    }

    public static void save(String key, Object value, Application application){
        SharedPreferences saver = application.getSharedPreferences("saved_settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor writer = saver.edit();

        if(value instanceof Boolean)  writer.putBoolean(key, (Boolean)value);
        else if(value instanceof Integer) writer.putInt(key, (Integer)value);

        writer.commit();
    }

    public static Object load(String key, Object value, Application application){
        SharedPreferences saver = application.getSharedPreferences("saved_settings", Context.MODE_PRIVATE);

        if(value instanceof Boolean)  return saver.getBoolean(key, (Boolean)value);
        else if(value instanceof Integer) return saver.getInt(key, (Integer)value);

        return new Object();
    }
}
