package com.example.mesnage_coanet;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.TextView;

public class ProductAdapter extends ArrayAdapter<Product> implements Filterable{
	
	private List<Product> items;
	
	/****associe le modele d'un item � l'adapter****/
	public ProductAdapter(Context context, List<Product> items) {
		super(context, R.layout.app_custom_list, items);
		this.items = items;		
	}

	/****recupere le nombre d'item****/
	@Override
	public int getCount() {
		return items.size();
	}

	/****recuperation des donnees*****/
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		
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
	
	/**********fonction de filtre appel�e dans le main************/
	public void filter(String charText) {
		charText = charText.toLowerCase(Locale.getDefault());
		//items.clear();
		
		//on cr�� une nouvelle liste
		List<Product> list_filtre = new ArrayList<Product>();
		
		//si aucun caractere nest tap� par l'utilisateur pour une recherche, on renvoi la liste initiale
		if (charText.length() == 0) {
				list_filtre.addAll(items);
				Log.d("fil2", "test");
		}
			
			//sinon on renvoi une nouvelle liste impl�ment�e avec les items dont le nom contient les caract�res tap�s dans le edittext
		else {
			for (Product product : items) {
				if (product.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
					list_filtre.add(product);
					Log.d("fil3", "test");
				}
			}
		}		
		notifyDataSetChanged();
		}
	
}
