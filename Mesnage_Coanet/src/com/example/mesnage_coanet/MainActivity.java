package com.example.mesnage_coanet;

import java.util.List;
import java.util.Locale;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
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
	      
	      /*********Fonction de Refresh **********/
	       final SwipeRefreshLayout swipeContain = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
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
	       
	     /******Fonction de filtre******/       
	      final EditText editsearch = (EditText) findViewById(R.id.filtre);
	       editsearch.addTextChangedListener(new TextWatcher() {
	    	   @Override
	    	   public void afterTextChanged(Editable arg0) {
	    		   String text = editsearch.getText().toString().toLowerCase(Locale.getDefault());
	    		   ProductAdapter adapter = new ProductAdapter(context, list_products);
	    		   adapter.filter(text);
	    		   adapter.notifyDataSetChanged();
	    	   }
	     
	    	   @Override
	    	   public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
	    
	    	   }
	     
	    	   @Override
	    	   public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

	    	   }
	       });
	    }
	    	    
	    /*********************/
	    private void initView() {
	        // show progress dialog
	        dialog = ProgressDialog.show(this, "", "Loading...");	         
	        String url = "https://mysterious-journey-1753.herokuapp.com/products.json";
	        //String url2 = "https://remindmymakeup.herokuapp.com/users/1";
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
