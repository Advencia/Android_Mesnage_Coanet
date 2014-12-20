package com.example.mesnage_coanet;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ProductAdapter extends ArrayAdapter<Product> {
	private List<Product> items;
	
	/*associe le modele d'un item à l'adapter*/
	public ProductAdapter(Context context, List<Product> items) {
		super(context, R.layout.app_custom_list, items);
		this.items = items;		
	}

	/*recupere le nombre d'item*/
	@Override
	public int getCount() {
		return items.size();
	}

	/*recuperation des donnees*/
	
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		//v.setOnLongClickListener();
		if (v == null) {
			LayoutInflater li = LayoutInflater.from(getContext());
			v = li.inflate(R.layout.app_custom_list, null);
		}

		Product app = items.get(position);

		if (app != null) {
			TextView name = (TextView) v.findViewById(R.id.name);
			TextView date = (TextView) v.findViewById(R.id.date);
			

			if (name != null) {
				name.setText(app.getName());
			}

			if (date != null) {
				date.setText(app.getDate());
			}
		}
		return v;
	}
	
}
