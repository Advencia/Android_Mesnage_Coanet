package com.example.mesnage_coanet;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.view.View.OnClickListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddProduct extends Activity implements OnClickListener {
	EditText name = null;
    EditText date = null;
    Button btn = null;
    
   		@Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);       
	        setContentView(R.layout.add_product);
	        name = (EditText) findViewById(R.id.edit_name);
	        date = (EditText) findViewById(R.id.edit_date);
	        btn = (Button) findViewById(R.id.add_product_button);
	        
	        btn.setOnClickListener((android.view.View.OnClickListener) this);
		}
		
   		/*******gestion du clic sur le bouton d'ajout******/
	    public void onClick(View view) {
	        switch(view.getId()){
	            case R.id.add_product_button:
	            	//if(!validate()){         
	            		Product product = new Product();
	            		product.setName(name.getText().toString());	            
	            		product.setDate(date.getText().toString());
	                	new HttpAsyncTask().execute(product);
	                	Log.d("var2","post");
	                	
	            	//}
	            break;
	        }	 
	    }
	    
	    /******fct utilis� pour verifier que les champs sont nn vides dans onClick*******/
	    private boolean validate(){
	        if(name.getText().toString().trim().equals(""))
	            return false;
	        else if(date.getText().toString().trim().equals(""))
	            return false;
	        else
	            return true;    
	    }
	       
	    /*********fonction d'envoi des donn�es en json au serveur************/
	    public static String POST(String url, Product product){
	        InputStream inputStream = null;
	        String result = "";
	        try {
	            HttpClient httpclient = new DefaultHttpClient();
	            Log.d("var","post");
	            HttpPost httpPost = new HttpPost(url);
	            String json;
	            // objet Json
	            JSONObject jsonObject = new JSONObject();
	            jsonObject.accumulate("name", product.getName());
	            jsonObject.accumulate("date", product.getDate());
	            //conversion
	            json = jsonObject.toString();
	 
	            // passer json en StringEntity
	            StringEntity se = new StringEntity(json);
	 
	            httpPost.setEntity(se);
	 
	            // met des titre headers pour informer le server du type de contenu 
	            httpPost.setHeader("Accept", "application/json");
	            httpPost.setHeader("Content-type", "application/json");
	          /*List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	 	       	nameValuePairs.add(new BasicNameValuePair("name", "anti-ride"));
	 	       	nameValuePairs.add(new BasicNameValuePair("date", "2015-06-08"));
	 	       	httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs)); */
	 
	            // Execute requete post
	            HttpResponse httpResponse = httpclient.execute(httpPost);
	 
	            // recoit la reponse comme inputstream
	            inputStream = httpResponse.getEntity().getContent();
	            Log.d("var","post");
	            // convertit inputstream en string
	            if(inputStream != null)
	                result = convertInputStreamToString(inputStream);
	            else
	                result = "Did not work!";
	 
	        } catch (Exception e) {
	            Log.d("InputStream", e.getLocalizedMessage());
	        }
	 
	        return result;
	    }
	 
	    /********connexion au serveur et envoi des donn�es********/
	    private class HttpAsyncTask extends AsyncTask<Product, Void, String> {
	        @Override
	        protected String doInBackground(Product... products) {
	            return POST(Product.url,products[0]);
	        }
	        
	        //fct d'affichage du resultat qui ici ne fait que confirmer l'envoi
	        @Override
	        protected void onPostExecute(String result) {Log.d("var3","post");
	            Toast.makeText(getBaseContext(), "Product save", Toast.LENGTH_LONG).show();
	       }
	    }
	    
	    /*********fonction de conversion**********/
	    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
	        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
	        String line = "";
	        String result = "";
	        while((line = bufferedReader.readLine()) != null){
	            result += line;
	        }
	        inputStream.close();
	        return result;	 
	    }
}