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


    public static String USER_AGENT = "Mozilla/5.0";


    public Double[] getCoordinates(String addres) throws Exception {
        String url = "https://geocode-maps.yandex.ru/1.x/?format=json&apikey=2ef09851-8fd9-4490-a0fd-e3b08b2ec731&geocode="+coder(addres);
        Double[] arr = parse(url);
//        System.out.println(arr[1]);первая координата
//        System.out.println(arr[0]);вторая координата
        return arr;
    }

    // HTTP GET request
    public static JSONObject sendGet(String stringUrl) throws IOException, JSONException {
        URL obj = new URL(stringUrl);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        int responseCode = con.getResponseCode();
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return new JSONObject(response.toString());
    }
    //Get coordinates from JSON
    public static Double[] parse(String url) throws IOException, JSONException {
        JSONObject obj = sendGet(url);
        String result = "";
        String [] str ;
        try{
            obj = obj.getJSONObject("response").getJSONObject("GeoObjectCollection");
            JSONArray array = new JSONObject(""+obj).getJSONArray("featureMember");
            result = array.getJSONObject(0).getJSONObject("GeoObject").getJSONObject("Point").toString();
        }catch (Exception e){
            System.out.print(e);
        }
        result = result.replace("\"" , "" ).substring(5).replace("}" , "");
        str = result.split(" ");
        Double[] doubles = new Double[2];
        for(int i=0 ; i<str.length ; i++){
            doubles[i] = Double.parseDouble(str[i]);
        }
        return doubles;
    }
    //Query coder in HEX format
    public static String coder(String query) throws UnsupportedEncodingException {
        char[] array;
        String ourQuery = "%";
        String result = String.format("%040x", new BigInteger   (1, query.getBytes("UTF-8")));
        result = result.replaceFirst("^0*" , "").toUpperCase();
        array = result.toCharArray();
        for(int j=0 ; j<array.length; j++){
            if(j%2==0){
                ourQuery+=array[j];
            } else {
                if(j==array.length-1){
                    ourQuery+=array[j];
                } else {
                    ourQuery+=array[j]+"%";
                }
            }
        }
        return ourQuery.replace("%2C", ",");
    }
}

