package com.example.a1706020040_dokimdangkhoa.AsyncTask;

import android.os.AsyncTask;

import com.example.a1706020040_dokimdangkhoa.Interface.IviewDta;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

public class DataAsyncTask extends AsyncTask<String, Boolean, JSONObject> {
    Map<String, String> resource;
    IviewDta iviewDta;
    public DataAsyncTask(Map<String, String> resource, IviewDta iviewDta) {
        this.resource = resource;
        this.iviewDta =iviewDta;
    }

    @Override
    protected JSONObject doInBackground(String... strings) {

        try {
            URL surl = new URL(strings[0]);
            HttpURLConnection connection = (HttpURLConnection) surl.openConnection();
            connection.setRequestProperty("Content-Type","application/json");
            connection.setRequestMethod("POST");

            JSONObject data = new JSONObject();
            Iterator iterator = resource.keySet().iterator();
            while (iterator.hasNext()){
                String key = (String)iterator.next();
                String value = resource.get(key);
                data.put(key, value);
            }
            OutputStream out = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
            writer.write(String.valueOf(data));
            writer.flush();
            writer.close();
            out.close();

            connection.connect();
            InputStream in = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder builder = new StringBuilder();
            String result;
            while ((result = reader.readLine()) != null){
                builder.append(result);
            }
            result = builder.toString();
            JSONObject parent = new JSONObject(result);
            publishProgress(true);
            return parent;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);
        if (jsonObject != null){
            try {
                int result = jsonObject.getInt("result");
                String message = jsonObject.getString("response_message");
                if (result > 0){
                    JSONArray child = jsonObject.getJSONArray("response_data");
                    iviewDta.getData(message,child);
                }
                else
                {
                    iviewDta.Error(message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
