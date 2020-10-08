package ruisun;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;


public class Model {

    public String getPicture(String state, String picSize) {
        Document doc = Jsoup.parse(fetch("https://en.wikipedia.org/wiki/Flags_of_the_U.S._states_and_territories"));
        Elements pics = doc.getElementsByAttributeValue("alt", "Flag of " + state + ".svg");
        return "https:" + pics.get(0).attr("src");
    }


    private String fetch(String urlString) {
        String response = "";
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String str;
            while ((str = in.readLine()) != null) {
                response += str;
            }
            in.close();
        } catch (IOException e) {
            System.out.println("Eeek, an exception");
        }
        return response;
    }

    public String getStats(String state, String category, String date) {
        String data = null;
        try {
            URL url = new URL("https://api.covidtracking.com/v1/states/" + state + "/" + date + ".csv");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                String key = in.readLine();
                String val = in.readLine();
                in.close();
                ArrayList<String> keys = new ArrayList<>(Arrays.asList(key.split(",")));
                ArrayList<String> vals = new ArrayList<>(Arrays.asList(val.split(",")));

                data = vals.get(keys.indexOf(category));
            }
        } catch (IOException e) {
            System.out.println("Eeek, an exception: " + e);
        }
        return data;
    }
}
