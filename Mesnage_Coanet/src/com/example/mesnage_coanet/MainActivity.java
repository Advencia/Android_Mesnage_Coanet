package com.example.mesnage_coanet;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.ActionBar;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ListActivity implements FetchDataListener {
	private List<Product> list_products;
	ProductAdapter adapter;
	private ProgressDialog dialog;
	SwipeRefreshLayout swipeLayout;
	FetchDataListener listener;
	Product product;
	Context context = this;
	
	
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);       
	        setContentView(R.layout.main);       
	        initView();
	      
	      /*********Fonction de Reload des données **********/
	       final SwipeRefreshLayout swipeContain = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
	        swipeContain.setOnRefreshListener(new OnRefreshListener() {
	            @Override
	            public void onRefresh() {
	            	refreshView();
	                swipeContain.setRefreshing(false);
	            } 
	        });
	        // Configure les couleurs
	       swipeContain.setColorSchemeResources(android.R.color.holo_blue_bright, 
	                android.R.color.holo_purple, 
	                android.R.color.holo_blue_light, 
	                android.R.color.holo_blue_dark);	
	       
	     /*********Fonction de filtre***********/   
	       final ListView lv = (ListView)findViewById(android.R.id.list);
	      adapter = new ProductAdapter(this, list_products);
	      final EditText editsearch = (EditText) findViewById(R.id.filtre);
	       editsearch.addTextChangedListener(new TextWatcher() {
	    	   @Override
	    	   public void afterTextChanged(Editable arg0) {
	    		   /*String text = editsearch.getText().toString().toLowerCase(Locale.getDefault());
	    		   adapter.filter(text);
	    		   Log.d("fil1", "test");
	    		   adapter.notifyDataSetChanged();*/
	    		   
	    	   }
	     
	    	   @Override
	    	   public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
	    
	    	   }
	     
	    	   @Override
	    	   public void onTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
	    		   /*String text = editsearch.getText().toString();
	    		   MainActivity.this.adapter.getFilter().filter(text);
	    		   Log.d("fil1", "test");
	    		   adapter.notifyDataSetChanged();*/
	    		   int textlength = s.length();
	               ArrayList<Product> tempArrayList = new ArrayList<Product>();
	               for(Product c: list_products){
	                  if (textlength <= c.getName().length()) {
	                     if (c.getUser().toLowerCase().contains(s.toString().toLowerCase()) || c.getName().toLowerCase().contains(s.toString().toLowerCase())) {
	                        tempArrayList.add(c);
	                     }
	                  }
	               }
	               adapter = new ProductAdapter(context, tempArrayList);
	               lv.setAdapter(adapter);
	    	   }
	       });
	    }
	    	
	    /**********Menu Action Bar***********/
	    @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        getMenuInflater().inflate(R.menu.main, menu);
	        ActionBar actionBar = getActionBar();
	        actionBar.setDisplayHomeAsUpEnabled(true);
	        actionBar.show();
	        return true;
	    }
	    
	    @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	    	switch (item.getItemId()) {
	    		case R.id.search:
	    		
	    			return true;
	    		default:
	    			return super.onOptionsItemSelected(item);
	    	}
	    }
	    
	    /**********initialise la connexion***********/
	    private void initView() {
	    	int i = 1;
	        // show progress dialog
	        dialog = ProgressDialog.show(this, "", "Loading...");	         	        
	        String url = "https://remindmymakeup.herokuapp.com/users/1.json"; 
	        
	      /*  if(isValidUrl(url,i)){
	    	  FetchDataTask task = new FetchDataTask(this);
	    	  task.execute("https://remindmymakeup.herokuapp.com/users/"+i+".json");
	    	  //i = i+1;
	    	  isValidUrl("https://remindmymakeup.herokuapp.com/users/"+(i+1)+".json", i);*/	    	  
	      }       
	    
	    
	   /* private boolean isValidUrl(String url, int i) {
	    	 Pattern p = Patterns.WEB_URL;
		        Matcher m = p.matcher("https://remindmymakeup.herokuapp.com/users/"+i+".json");
	    	 if(m.matches()){
		        	return true;
		        }
	    	 else {
	    		 return false;
	    	 }
	    }*/
	    /**********************/
	    private void refreshView() {
	    	 String url = "https://remindmymakeup.herokuapp.com/users/1.json";
		     FetchDataTask task = new FetchDataTask(this);
		     task.execute(url);
	    }
	    
	    /********************/
	    @Override
	    public void onFetchComplete(List<Product> data) {
	        if(dialog != null)  dialog.dismiss();
	        ProductAdapter adapter = new ProductAdapter(this, data);
	        // remplit la liste selon l'adapter 
	        setListAdapter(adapter); 
	        list_products = data;	        
	    }
	 
	    @Override
	    public void onFetchFailure(String msg) {
	        if(dialog != null)  dialog.dismiss();
	        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();       
	    }	     
}
