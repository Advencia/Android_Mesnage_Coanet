package com.example.mesnage_coanet;

import java.util.List;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends ListActivity implements FetchDataListener {
	private List<Product> list_products;
	private ProgressDialog dialog;
	SwipeRefreshLayout swipeLayout;
	FetchDataListener listener;
	Product product;
	
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);       
	        setContentView(R.layout.main);       
	        initView();
	        
	       final SwipeRefreshLayout swipeContain = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
	        // Setup refresh listener which triggers new data loading
	        swipeContain.setOnRefreshListener(new OnRefreshListener() {
	            @Override
	            public void onRefresh() {
	            	refreshView();
	                swipeContain.setRefreshing(false);
	            } 
	        });
	        // Configure the refreshing colors
	       swipeContain.setColorSchemeResources(android.R.color.holo_blue_bright, 
	                android.R.color.holo_purple, 
	                android.R.color.holo_blue_light, 
	                android.R.color.holo_blue_dark);	
	    }
	    	    
	    /*********************/
	    private void initView() {
	        // show progress dialog
	        dialog = ProgressDialog.show(this, "", "Loading...");	         
	        String url = "https://mysterious-journey-1753.herokuapp.com/products.json";
	        FetchDataTask task = new FetchDataTask(this);
	        task.execute(url);
	        
	    }
	    
	    private void refreshView() {
	    	 String url = "https://mysterious-journey-1753.herokuapp.com/products.json";
		     FetchDataTask task = new FetchDataTask(this);
		     task.execute(url);
	    }
	    
	    @Override
	    public void onFetchComplete(List<Product> data) {
	        // dismiss the progress dialog
	        if(dialog != null)  dialog.dismiss();
	        // create new adapter
	        ProductAdapter adapter = new ProductAdapter(this, data);
	        // set the adapter to list
	        setListAdapter(adapter); 
	        list_products = data;	        
	    }
	 
	    @Override
	    public void onFetchFailure(String msg) {
	        // dismiss the progress dialog
	        if(dialog != null)  dialog.dismiss();
	        // show failure message
	        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();       
	    }
	     
}
