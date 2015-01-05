package com.example.mesnage_coanet;

public class Product {
	 private String user;
	 private String name;
	 private String date;
	 public static String url= "https://mysterious-journey-1753.herokuapp.com/products.json";
	 public static String url2= "https://mysterious-journey-1753.herokuapp.com/products";
	 
	 public void setName(String name) {
	  this.name = name;
	 }
	 
	 public String getName() {
	  return name;
	 }

	 public void setDate(String date) {
	  this.date = date;
	 }

	 public String getDate() {
	  return date;
	 }
	 
	 public void setUser(String user){
		 this.user = user;
	 }
	 public String getUser() {
		 return user;
	 }
}

