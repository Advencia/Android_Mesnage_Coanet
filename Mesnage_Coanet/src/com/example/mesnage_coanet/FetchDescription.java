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

public class FetchDescription extends AsyncTask<String, Void, String> {
	 @Override
	    protected String doInBackground(String... params) {
		 if(params == null) return null;
         
	        // get url from params
	        String url = params[0];  
	             
	            try {
	                // create http connection
	                HttpClient client = new DefaultHttpClient();
	                HttpGet httpget = new HttpGet(url);
	                 
	                // connect
	                HttpResponse response = client.execute(httpget);
	                 
	                // get response
	                HttpEntity entity = response.getEntity();
	                 
	                if(entity == null) {
	                    String msg = "No response from server";
	                    return null;       
	                }
	              
	                // get response content and convert it to json string
	                InputStream is = entity.getContent();
	                return streamToString(is);
	            }
	            catch(IOException e){
	               String msg = "No Network Connection";
	            }
	             
	            return null;
	        }
	         
	    @Override
	    protected void onPostExecute(String sJson) {
	        if(sJson == null) {
	           
	            return;
	        }       
	         
	        try {
	        	List<Product> app = new ArrayList<Product>();
	            // on récupère ici dans un tableau json l'object (la table) products qui contient les produits dans un tableau
	            JSONObject aJson = new JSONObject(sJson);
	            JSONArray prodTab = aJson.getJSONArray("products");
	             
	            for(int i=0; i<prodTab.length(); i++) {
	            	JSONObject json = prodTab.getJSONObject(i);
	            	Product prod = new Product();
	                prod.setName(json.getString("name_product"));
	                prod.setDate(json.getString("date"));
	                app.add(prod);                
	            }
	             
	           
	        }  
	        	catch (JSONException e) {
	        		String msg = "Invalid response";
	        		
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
