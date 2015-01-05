package com.example.mesnage_coanet;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ClickItem extends Activity {
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);       
	    setContentView(R.layout.description);
	    TextView name = (TextView) findViewById(R.id.name);
	    TextView date = (TextView) findViewById(R.id.date);
	    
	 }
	
	
}
