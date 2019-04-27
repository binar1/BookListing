package com.example.developer.booklisting;

import android.support.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;



/**
 * Created by binar on 12/11/2017.
 */

public class Query {

    /**
     * Created by binar on 08/11/2017.
     */



        public static List<Book> fetchData(String urlstring) {
            URL url = genarateUrl(urlstring);
            System.out.println(url);
            String json = null;
            try {
                json = makeHttpRequest(url);
            } catch (IOException e) {
                System.out.println("FAILED MAKING HTTP REQUEST");
            }
            List<Book> book = parse(json);

            return book;

        }


        public static URL genarateUrl(String stringurl) {
            if (stringurl == null || stringurl.length() < 1)
                return null;
            URL url = null;
            try {
                url = new URL(stringurl);
            } catch (MalformedURLException e) {
                System.out.println("FAILED GENERATING URL");
            }
            return url;
        }


        public static String makeHttpRequest(URL url) throws IOException {
            String jsoneResponse = "";
            HttpURLConnection Connection = null;
            InputStream inputstream = null;
            try {
                Connection = (HttpURLConnection) url.openConnection();
                Connection.setConnectTimeout(1000);
                Connection.setReadTimeout(1000);
                Connection.setRequestMethod("GET");
                Connection.connect();
                if (Connection.getResponseCode() == 200) {
                    inputstream = Connection.getInputStream();
                    jsoneResponse = readFromStream(inputstream);
                } else {
                    System.out.println("BAD RESPONSE CODE");
                }
            } catch (IOException e) {
                System.out.println("FAILED OPENING CONNECTION");
            } finally {
                if (Connection != null) Connection.disconnect();
                if (inputstream != null) inputstream.close();
            }
            return jsoneResponse;

        }

        public static String readFromStream(InputStream inputstream) throws IOException {
            StringBuilder sb = new StringBuilder();
            if (inputstream != null) {
                InputStreamReader inputstreamreader = new InputStreamReader(inputstream, Charset.forName("UTF-8"));
                BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
                String line = bufferedreader.readLine();
                while (line != null) {
                    sb.append(line);
                    line = bufferedreader.readLine();
                }
            }
            return sb.toString();
        }


        public static List<Book> parse(String jsons) {
            List<Book> json =new ArrayList<>();

            JSONObject jsobj ;
            try {

                String img = "http://www.francoisfabie.fr/wp-content/uploads/2017/06/cuisine-grise-laquee-16-cuisine-quip-e-plan-3d-1752-x-1107.jpg";
                String title = "no title";
                String author = "no author";
                String url = "www.google.com";
                jsobj = new JSONObject(jsons);
                JSONArray jsnarray = jsobj.getJSONArray("items");
                for (int i = 0;i < jsnarray.length(); i++) {
                    JSONObject imageLinks, volumeInfo, items;
                    items = jsnarray.getJSONObject(i);
                    volumeInfo = items.getJSONObject("volumeInfo");
                    JSONArray authors;

                    if (volumeInfo.has("imageLinks")) {
                        imageLinks = volumeInfo.getJSONObject("imageLinks");
                        img = imageLinks.getString("smallThumbnail");
                    }
                    if (volumeInfo.has("title")) {
                        title = volumeInfo.getString("title");
                    }
                    if (volumeInfo.has("previewLink")) {
                        url = volumeInfo.getString("previewLink");
                    }
                    if (volumeInfo.has("authors")) {
                        authors = volumeInfo.getJSONArray("authors");
                        author = authors.getString(0);
                    }
                    Book book = new Book(img,title,author,url);
                    json.add(book);
                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
            return json;
        }


    }


