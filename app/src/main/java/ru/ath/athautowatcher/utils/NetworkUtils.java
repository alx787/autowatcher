package ru.ath.athautowatcher.utils;

import android.os.AsyncTask;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class NetworkUtils {
    private static final String BASE_URL = "http://192.168.1.2:8080/restprox/";
    private static final String URL_GET_ALL_OBJS = "wl/getobject/db/all";
    private static final String URL_GET_LASTPOS = "track/getlastpos/__invnom__";
    private static final String URL_GET_TRACKS = "track/gettrack/__invnom__/__datebeg__/__dateend__";


    public static JsonObject getJsonAllObjects() {
        String url = BASE_URL + URL_GET_ALL_OBJS;
        return getJsonFromNetwork(url);
    }

    public static JsonObject getJsonLastPosition(String invnom) {
        String url = BASE_URL + URL_GET_LASTPOS.replace("__invnom__", invnom);
        return getJsonFromNetwork(url);
    }

    public static JsonObject getJsonTracks(String invnom, String datebeg, String dateend) {
        String url = BASE_URL + URL_GET_TRACKS.replace("__invnom__", invnom);
        url = url.replace("__datebeg__", datebeg);
        url = url.replace("__dateend__", dateend);

        return getJsonFromNetwork(url);
    }



    private static JsonObject getJsonFromNetwork(String url) {
        JsonObject result = null;
//        String url = BASE_URL + URL_GET_ALL_OBJS;

        try {
            result = new JsonLoadTask().execute(url).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return result;
    }

    private static class JsonLoadTask extends AsyncTask<String, Void, JsonObject> {
        @Override
        protected JsonObject doInBackground(String... strings) {
            if ((strings == null) || (strings.length == 0)) {
                return null;
            }

            JsonObject resJson = null;
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) new URL(strings[0]).openConnection();

                int responseCode = connection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {

                    StringBuilder result = new StringBuilder();

                    InputStream inputStream = connection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                    BufferedReader reader = new BufferedReader(inputStreamReader);
                    String line = reader.readLine();
                    while (line != null) {
                        result.append(line);
                        line = reader.readLine();
                    }

                    if (result.toString().isEmpty()) {
                        return null;
                    }

                    resJson = JsonParser.parseString(result.toString()).getAsJsonObject();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }

            return resJson;
        }
    }
}
