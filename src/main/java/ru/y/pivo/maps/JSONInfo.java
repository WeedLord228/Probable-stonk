package ru.y.pivo.maps;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;


public class JSONInfo {

    static final String USER_AGENT = "Mozilla/5.0";

    public Double[] getCoordinates(String address) throws Exception {
        String url = "https://geocode-maps.yandex.ru/1.x/?format=json&apikey=2ef09851-8fd9-4490-a0fd-e3b08b2ec731&geocode=" + coder(address);
        return parse(url);
    }

    // HTTP GET request
    private static JSONObject sendGet(String stringUrl) throws IOException, JSONException {
        URL obj = new URL(stringUrl);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = connection.getResponseCode();

        if (responseCode != 200)
            throw new IOException("Bad request");

        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));

        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }

        in.close();
        return new JSONObject(response.toString());
    }

    //Get coordinates from JSON
    private static Double[] parse(String url) throws IOException, JSONException {
        JSONObject obj = sendGet(url);
        String result;
        String[] str;
        try {
            obj = obj.getJSONObject("response").getJSONObject("GeoObjectCollection");
            JSONArray array = new JSONObject("" + obj).getJSONArray("featureMember");
            result = array.getJSONObject(0).getJSONObject("GeoObject").getJSONObject("Point").toString();
        } catch (Exception e) {
            throw  new JSONException(e.toString());
        }
        result = result.replace("\"", "").substring(5).replace("}", "");
        str = result.split(" ");
        Double[] doubles = new Double[2];
        for (int i = 0; i < str.length; i++) {
            doubles[i] = Double.parseDouble(str[i]);
        }
        return doubles;
    }

    //Query coder in HEX format
    private static String coder(String query) throws UnsupportedEncodingException {
        char[] array;
        StringBuilder ourQuery = new StringBuilder("%");
        String result = String.format("%040x", new BigInteger(1, query.getBytes("UTF-8")));
        result = result.replaceFirst("^0*", "").toUpperCase();
        array = result.toCharArray();
        for (int j = 0; j < array.length; j++) {
            if (j % 2 == 0) {
                ourQuery.append(array[j]);
            } else {
                if (j == array.length - 1) {
                    ourQuery.append(array[j]);
                } else {
                    ourQuery.append(array[j]).append("%");
                }
            }
        }
        return ourQuery.toString().replace("%2C", ",");
    }
}
