package com.example.mesnage_coanet;

import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends ListActivity implements FetchDataListener {
	private List<Product> list_products;
	private ProgressDialog dialog;
     
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);       
	        setContentView(R.layout.main);       
	        initView();
	         
	    }
	    	    
	    /*********************/
	    private void initView() {
	        // show progress dialog
	        dialog = ProgressDialog.show(this, "", "Loading...");	         
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
