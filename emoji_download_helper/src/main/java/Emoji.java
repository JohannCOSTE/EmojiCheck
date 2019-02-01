import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.*;

public class Emoji {

    public Document getDocumentFromUrl(String url) throws IOException {
        return Jsoup.connect(url).maxBodySize(0).get();
    }

    public Document getDocumentFromFile(String path) throws IOException {
        File input = new File(path);
        return Jsoup.parse(input, "UTF-8", "");
    }

    public void process(Document doc, String output){
        for(int i =0; i< 7; i++) processDevice(doc, output, i); // 0: Apple, 1: Google, 2: Twitter, 3: One, 4: Facebook, 5: Samsung, 6: Windows
    }

    public void processDevice(Document doc, String outputDirectory, int device){
        try {
            Elements newsHeadlines = doc.select("tr");
            String outputFile = outputDirectory;
            switch (device){
                case 0: outputFile += "apple.html"; break;
                case 1: outputFile += "google.html"; break;
                case 2: outputFile += "twitter.html"; break;
                case 3: outputFile += "one.html"; break;
                case 4: outputFile += "fb.html"; break;
                case 5: outputFile += "samsung.html"; break;
                case 6: outputFile += "windows.html"; break;
            }

            PrintWriter writer = new PrintWriter(outputFile, "UTF-8");
            int DEVICE = device+3; // 3: Apple, 4: Google, 5: Twitter, 6: One, 7: Facebook, 8: Samsung, 9: Windows

            for (int i = 3; i < newsHeadlines.size(); i++) {
                Element line = newsHeadlines.get(i);
                try {
                    if (line.childNodeSize() >= 3) writer.println(line.child(DEVICE).select("img"));
                }catch (IndexOutOfBoundsException e){

                }
            }
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Emoji emoji = new Emoji();
        Document doc;
        try {
            doc = emoji.getDocumentFromUrl("http://unicode.org/emoji/charts/full-emoji-list.html");
            emoji.process(doc,"");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
