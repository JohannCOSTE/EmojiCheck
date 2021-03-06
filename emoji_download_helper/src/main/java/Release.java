import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;

public class Release {
    private void process(Document doc, String output) throws FileNotFoundException, UnsupportedEncodingException {
        Elements newsHeadlines = doc.select("article");
        Element article = newsHeadlines.get(0);
        Elements emojis = article.select(".emoji");
        PrintWriter writer = new PrintWriter(output, "UTF-8");
        writer.print(emojis);
        writer.close();
    }

    private Document getDocumentFromUrl(String url) throws IOException {
        return Jsoup.connect(url).maxBodySize(0).get();
    }

    public Document getDocumentFromFile(String path) throws IOException {
        File input = new File(path);
        return Jsoup.parse(input, "UTF-8", "");
    }

    public static void main(String[] args) {
        Release release = new Release();
        Document doc;
        try {
            doc = release.getDocumentFromUrl("https://emojipedia.org/emoji-12.0/");
            release.process(doc, "emoji.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
