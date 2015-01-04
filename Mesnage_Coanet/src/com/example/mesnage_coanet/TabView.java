package com.example.mesnage_coanet;
import android.app.ActionBar;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TabHost;

public class TabView extends TabActivity {

	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); 
        final TabHost tabHost = getTabHost();
        
        tabHost.addTab(tabHost.newTabSpec("tab1")
                .setIndicator("Products")
                .setContent(new Intent(this, MainActivity.class)));

        tabHost.addTab(tabHost.newTabSpec("tab2")
                .setIndicator("Add Product")
                .setContent(new Intent(this, AddProduct.class)));       
    }
    
    /**********Menu Action Bar***********/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
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
}