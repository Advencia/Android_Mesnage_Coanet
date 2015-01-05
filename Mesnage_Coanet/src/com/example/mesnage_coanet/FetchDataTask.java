package com.example.mesnage_coanet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class FetchDataTask extends AsyncTask<String, Void, String> {
	
	private static final String TAG_PRODUCT = "products";
	private final FetchDataListener listener;
    private String msg;
     
    public FetchDataTask(FetchDataListener listener) {
        this.listener = listener;
    }
    
    @Override
    protected String doInBackground(String... params) {
        if(params == null) return null;
         
        // recupere l'url
        String url = params[0];  
             
            try {
                // crée http connection
                HttpClient client = new DefaultHttpClient();
                HttpGet httpget = new HttpGet(url);
                HttpResponse response = client.execute(httpget);
                 
                // obtient response
                HttpEntity entity = response.getEntity();
                 
                if(entity == null) {
                    msg = "No response from server";
                    return null;       
                }
              
                // obtient contenu de la response et convertit
                InputStream is = entity.getContent();
                return streamToString(is);
            }
            catch(IOException e){
                msg = "No Network Connection";
            }
             
            return null;
        }
         
    @Override
    protected void onPostExecute(String sJson) {
        if(sJson == null) {
            if(listener != null) listener.onFetchFailure(msg);
            return;
        }       
         
        try {
        	List<Product> app = new ArrayList<Product>();
            // on récupère ici dans un tableau json l'object (la table) products qui contient les produits dans un tableau
            JSONObject aJson = new JSONObject(sJson);
            JSONArray prodTab = aJson.getJSONArray(TAG_PRODUCT);
            
            for(int i=0; i<prodTab.length(); i++) {
            	JSONObject json = prodTab.getJSONObject(i);
            	Product prod = new Product();
            	prod.setUser(aJson.getString("name"));
                prod.setName(json.getString("name_product"));
                prod.setDate(json.getString("date"));
                app.add(prod); 
                Log.d("TRY", json.toString());
            }
             
            if(listener != null){
            	listener.onFetchComplete(app);
            }
           
        }  
        	catch (JSONException e) {
        		msg = "Invalid response";
        		if(listener != null) listener.onFetchFailure(msg);
        		return;
        }       
    }
    
    
   /********Conversion ***********/ 
    public String streamToString(final InputStream is) throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
         
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        }
        catch (IOException e) {
            throw e;
        }
        finally {          
            try {
                is.close();
            }
            catch (IOException e) {
                throw e;
            }
        }
         
        return sb.toString();
    }

  
}