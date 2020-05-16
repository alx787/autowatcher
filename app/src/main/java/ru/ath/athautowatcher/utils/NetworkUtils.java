package ru.ath.athautowatcher.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import ru.ath.athautowatcher.MainActivity;

public class NetworkUtils {
    private static final String BASE_URL = "http://192.168.1.2:8080/restprox/";

    private static final String URL_CONTEXT = "/restprox/";
    private static final String URL_GET_ALL_OBJS = "wl/getobject/db/all";
    private static final String URL_GET_LASTPOS = "track/getlastpos/__invnom__";
    private static final String URL_GET_TRACKS = "track/gettrack/__invnom__/__datebeg__/__dateend__";
    private static final String URL_GET_NEWAUTH = "info/newauth";


    //////////////////////////////////////////
    // запросы к серверу
    //////////////////////////////////////////
    public static JsonObject getJsonAllObjects(Context ctxt) {
//        String url = BASE_URL + URL_GET_ALL_OBJS;
        String url = getServerSite(ctxt) + URL_GET_ALL_OBJS;

        //Log.i("myres", url);
        return getJsonFromNetwork(ctxt, url);
    }

    public static JsonObject getJsonLastPosition(Context ctxt, String invnom) {
//        String url = BASE_URL + URL_GET_LASTPOS.replace("__invnom__", invnom);
        String url = getServerSite(ctxt) + URL_GET_LASTPOS.replace("__invnom__", invnom);
        return getJsonFromNetwork(ctxt, url);
    }

    public static JsonObject getJsonTracks(Context ctxt, String invnom, String datebeg, String dateend) {
//        String url = BASE_URL + URL_GET_TRACKS.replace("__invnom__", invnom);
        String url = getServerSite(ctxt) + URL_GET_TRACKS.replace("__invnom__", invnom);
        url = url.replace("__datebeg__", datebeg);
        url = url.replace("__dateend__", dateend);
        return getJsonFromNetwork(ctxt, url);
    }

    // запрос токена
    public static JsonObject getAuthInfo(Context ctxt) {
        String url = getServerSite(ctxt) + URL_GET_NEWAUTH;
        return getJsonFromNetwork(ctxt, url);
    }


    //////////////////////////////////////////
    // вспомогательные процедуры
    //////////////////////////////////////////
    private static String getServerSite(Context ctxt) {
        SharedPreferences mSettings = ctxt.getSharedPreferences(Globals.APP_PREFERENCES, Context.MODE_PRIVATE);
        String sAddress = mSettings.getString(Globals.SRV_ADDRESS, "");
        String sPort = mSettings.getString(Globals.SRV_PORT, "");

        if (sAddress.equals("") || sPort.equals("")) {
            return null;
        }

        return "http://" + sAddress + ":" + sPort + URL_CONTEXT;
    }

    private static String getAuthPostData(Context ctxt, String url) {
        String retValue = null;

        SharedPreferences mSettings = ctxt.getSharedPreferences(Globals.APP_PREFERENCES, Context.MODE_PRIVATE);

        if (url.endsWith(URL_GET_NEWAUTH)) {
            retValue = "{\"login\":\"" + mSettings.getString(Globals.SRV_USERNAME, "") + "\", \"password\":\"" + mSettings.getString(Globals.SRV_PASSWORD, "") + "\"}";
        } else {
            retValue = "{\"userid\":\"" + mSettings.getString(Globals.SRV_USERID, "") + "\", \"token\":\"" + mSettings.getString(Globals.SRV_TOKEN, "") + "\"}";
        }
        return retValue;
    }

    private static JsonObject getJsonFromNetwork(Context ctxt, String url) {
        JsonObject result = null;
//        String url = BASE_URL + URL_GET_ALL_OBJS;

        try {
            result = new JsonLoadTask().execute(ctxt, url).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return result;
    }

    //////////////////////////////////////////
    // асинхронный запрос
    //////////////////////////////////////////
    private static class JsonLoadTask extends AsyncTask<Object, Void, JsonObject> {
        @Override
        protected JsonObject doInBackground(Object... objects) {
            if ((objects == null) || (objects.length == 0)) {
                return null;
            }

            Context ctxt = (Context) objects[0];
            String url = (String) objects[1];

            JsonObject resJson = null;
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) new URL(url).openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                connection.setRequestProperty("Accept", "application/json");
                connection.setDoOutput(true);
                connection.setDoInput(true);

                Log.i("myres", connection.getURL().getPath() + " - " + connection.getURL().getQuery());

                // установим данные для авторизации
                String postData = getAuthPostData(ctxt, url);
                try {
                    OutputStream os = connection.getOutputStream();
                    byte[] input = postData.getBytes("utf-8");
                    os.write(input, 0, input.length);
                } catch (Exception e) {

                }

                connection.setConnectTimeout(5000);

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
