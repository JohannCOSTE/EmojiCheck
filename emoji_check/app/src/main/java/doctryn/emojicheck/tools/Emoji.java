package doctryn.emojicheck.tools;

import android.content.Context;
import android.content.res.AssetManager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Emoji {
    private static Context context;

    private static ArrayList<String> data_files = new ArrayList<>();
    private static Map<String, String> apple = new HashMap<>();
    private static Map<String, String> google = new HashMap<>();
    private static Map<String, String> googleb = new HashMap<>();
    private static Map<String, String> twitter = new HashMap<>();
    private static Map<String, String> fb = new HashMap<>();
    private static Map<String, String> samsung = new HashMap<>();
    private static Map<String, String> windows = new HashMap<>();

    public static ArrayList<Map<String, String>> hmaps = new ArrayList<>(8);
    public static Map<String, Integer> version = new HashMap<>();
    public static boolean[] loaded = new boolean[9];
    public static boolean[] bug = new boolean[9];
    public static Boolean release = false;

     /* ************************* METHODS ************************* */

    public static void init(Context cont){
        context = cont;

        hmaps.add(apple);
        hmaps.add(google);
        hmaps.add(googleb);
        hmaps.add(twitter);
        hmaps.add(fb);
        hmaps.add(samsung);
        hmaps.add(windows);

        data_files.add("apple.xml");
        data_files.add("google.xml");
        data_files.add("googleb.xml");
        data_files.add("twitter.xml");
        data_files.add("fb.xml");
        data_files.add("samsung.xml");
        data_files.add("windows.xml");

        if(!release) {
            fillVersion("v1.xml", 1);
            fillVersion("v2.xml", 2);
            fillVersion("v3.xml", 3);
            fillVersion("v4.xml", 4);
            fillVersion("v5.xml", 5);
            fillVersion("v11.xml", 11);
            fillVersion("v12.xml", 12);
            Emoji.release = true;
        }
    }

    public static boolean completed(){
        boolean res = true;
        for(boolean val : loaded) res &=val;
        return res;
    }

    public static boolean bug(){
        boolean res = false;
        for(boolean val : bug) res |=val;
        return res;
    }

    public static int toLoad() {
        int res = 0;
        for(boolean val : loaded) if(!val) res++;
        return res;
    }

    private static void fillVersion(String file, int index){
        System.gc();
        try {
            Document doc = Jsoup.parse(context.getAssets().open(file), "UTF-8", "");
            Elements newsHeadlines = doc.select(".emoji");
            for (int i = 0; i < newsHeadlines.size(); i++) {
                Element ligne = newsHeadlines.get(i);
                String emoji = ligne.ownText();
                version.put(emoji, index);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /* *** LOAD *** */

    public static void load(int index, AssetManager am) throws OutOfMemoryError, IOException {
        if (!loaded[index]) {
            fillHash(index, am);
            loaded[index] = true;
        }
    }

    private static void fillHash(int index, AssetManager am) throws OutOfMemoryError, IOException {
        Document doc = Jsoup.parse(am.open(data_files.get(index)), "UTF-8", "");
        Elements newsHeadlines = doc.select("img");
        int size = newsHeadlines.size();
        for (int i = 0; i < size; i++) {
            Element line = newsHeadlines.get(i);
            hmaps.get(index).put(line.attr("alt"), line.attr("src"));
        }
    }

}
