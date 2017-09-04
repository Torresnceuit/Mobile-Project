package com.example.weatherdisplay;
 
import android.app.Activity;
import android.content.SharedPreferences;
 
public class CityPreference {
     
    SharedPreferences prefs;
     
    public CityPreference(Activity activity){
        prefs = activity.getPreferences(Activity.MODE_PRIVATE);
    }
     
    // If the user has not chosen a city yet, return
    // Sydney as the default city
    String getCity(){
        //return prefs.getString("city", "Sydney, AU");  
    	System.out.println("lat="+WeatherActivity.myLat+"&lon="+WeatherActivity.myLong);
    	return (""+WeatherActivity.myLat+","+WeatherActivity.myLong);
    }
    
     
    void setCity(String city){
        prefs.edit().putString("city", city).commit();
    }
     
}