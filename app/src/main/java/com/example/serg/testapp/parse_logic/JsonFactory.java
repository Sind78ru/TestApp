package com.example.serg.testapp.parse_logic;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class JsonFactory {

    public JsonFactory() {

    }

    public static JSONArray decodeStream(InputStream is) {
        BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
        String jsonText = null;
        try {
            StringBuilder sb = new StringBuilder();
            int cp;
            while ((cp = rd.read()) != -1) {
                sb.append((char) cp);
            }
            jsonText = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONArray jsonArr = null;
        try {
            jsonArr = new JSONArray(jsonText);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonArr;

    }

    public static JSONArray parseStringToArray(String text) {
        JSONArray jsonArr = null;
        try {
            jsonArr = new JSONArray(text);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonArr;
    }
}
