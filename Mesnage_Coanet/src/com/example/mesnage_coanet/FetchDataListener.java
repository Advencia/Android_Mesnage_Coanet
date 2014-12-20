package com.example.mesnage_coanet;
import java.util.List;
public interface FetchDataListener {
	
		public void onFetchComplete(List<Product> data);
		public void onFetchFailure(String msg);
}
