package com.example.weatherdisplay;

import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.sql.Date;
import java.text.DateFormat;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class WeatherFragment extends Fragment{
	//private static final Context WeatherActivity = null;
	Typeface weatherFont;
	Typeface tahomaFont;
	Typeface vniFont;
    
    TextView cityField;
    TextView currentLocation;
    TextView updatedField;
    TextView detailsField;
    TextView descriptionField;
    TextView currentTemperatureField;
    TextView unitFied;
    DynamicImageView weatherIcon;
    
    TextView tempMin;
    TextView tempMax;
    TextView winSpeed;
    TextView winDeg;
    TextView humidity;
    TextView pressure;
    TextView pressureStat;
    TextView visibility;
    TextView sunrise;
    TextView sunset;
    
    byte[] Image;
    Bitmap img = null;
    
     
    Handler handler;
    
    private static String[] VietnameseSigns = new String[]
	        {
	 
	            "aAeEoOuUiIdDyY",
	 
	            "áàạảãâấầậẩẫăắằặẳẵ",
	 
	            "ÁÀẠẢÃÂẤẦẬẨẪĂẮẰẶẲẴ",
	 
	            "éèẹẻẽêếềệểễ",
	 
	            "ÉÈẸẺẼÊẾỀỆỂỄ",
	 
	            "óòọỏõôốồộổỗơớờợởỡ",
	 
	            "ÓÒỌỎÕÔỐỒỘỔỖƠỚỜỢỞỠ",
	 
	            "úùụủũưứừựửữ",
	 
	            "ÚÙỤỦŨƯỨỪỰỬỮ",
	 
	            "íìịỉĩ",
	 
	            "ÍÌỊỈĨ",
	 
	            "đ",
	 
	            "Đ",
	 
	            "ýỳỵỷỹ",
	 
	            "ÝỲỴỶỸ"
	 
	        };
 
    public WeatherFragment(){   
        handler = new Handler();
        
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);  
        weatherFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/weather.ttf"); 
        tahomaFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Tahoma.ttf");
        vniFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/vni.ttf");
        updateWeatherData_LOCATION(new CityPreference(getActivity()).getCity());
        
        
    }
 
    private void updateWeatherData_CITY(final String city) {
		// TODO Auto-generated method stub
    	new Thread(){
            public void run(){
                final JSONObject json = RemoteFetch.getJSON_CITY(getActivity(), city);

                if(json == null){
                    handler.post(new Runnable(){
                        public void run(){
                            Toast.makeText(getActivity(), 
                                    getActivity().getString(R.string.place_not_found), 
                                    Toast.LENGTH_LONG).show(); 
                        }
                    });
                } else {
                    handler.post(new Runnable(){
                        public void run(){
                        	//renderLocation(json);
                        	
                            renderWeather_CITY(json);
                        }

						
                    });
                }               
            }
        }.start();
		
	}
    
    private void updateWeatherData_LOCATION(final String city) {
		// TODO Auto-generated method stub
    	new Thread(){
            public void run(){
                final JSONObject json = 
                				RemoteFetch.getJSON_CITY(getActivity(), city);
                final JSONObject jsonLocation = 
                				RemoteFetch.getJSON_SEARCH(getActivity(), city);

                if(json == null){
                    handler.post(new Runnable(){
                        public void run(){
                            Toast.makeText(getActivity(), 
                                    getActivity().getString(R.string.place_not_found), 
                                    Toast.LENGTH_LONG).show(); 
                        }
                    });
                } else {
                    handler.post(new Runnable(){
                        public void run(){
                        	renderLocation(jsonLocation);
                        	
                            renderWeather_LOCATION(json);
                        }

						
                    });
                }               
            }
        }.start();
		
	}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_weather, container, false);
        cityField = (TextView)rootView.findViewById(R.id.city_field);
        
        cityField.setTypeface(vniFont);
        descriptionField = (TextView)rootView.findViewById(R.id.description_field);
        updatedField = (TextView)rootView.findViewById(R.id.updated_field);
        //detailsField = (TextView)rootView.findViewById(R.id.details_field);
        currentTemperatureField = (TextView)rootView.findViewById(R.id.current_temperature_field);
        weatherIcon = (DynamicImageView)rootView.findViewById(R.id.weather_icon);
        unitFied = (TextView)rootView.findViewById(R.id.unit_field);
        
        tempMin = (TextView)rootView.findViewById(R.id.tempMin);
        tempMax = (TextView)rootView.findViewById(R.id.tempMax);
        winSpeed = (TextView)rootView.findViewById(R.id.windSpeed);
        winDeg = (TextView)rootView.findViewById(R.id.windDeg);
        humidity = (TextView)rootView.findViewById(R.id.humidity);
        pressure = (TextView)rootView.findViewById(R.id.pressure);
        pressureStat = (TextView)rootView.findViewById(R.id.pressureStat);
        visibility = (TextView)rootView.findViewById(R.id.visibility);
        sunrise = (TextView)rootView.findViewById(R.id.sunrise);
        sunset = (TextView)rootView.findViewById(R.id.sunset);
         
        //weatherIcon.setTypeface(weatherFont);
        
        
        
//        GPSTracker mGPS = new GPSTracker(WeatherActivity);
	    currentLocation = (TextView)rootView.findViewById(R.id.location_field);
	    currentLocation.setText(WeatherActivity.current_location);
//	    if(mGPS.canGetLocation())
//	    {
//	    	mGPS.getLocation();
//	    	location.setText("Lat"+mGPS.getLatitude()+"Lon"+mGPS.getLongitude());
//	    }
//	    else
//	    {
//	    	System.out.println("Unable");
//	    }
        return rootView; 
    }
	
	private void renderLocation(JSONObject jsonLocation)
	{
		try {
			System.out.println("<<<<<<<<<<<<<<vanue");
			JSONObject location = jsonLocation.getJSONObject("search_api");
			JSONObject result= location.getJSONArray("result").getJSONObject(0);
			JSONObject area = result.getJSONArray("areaName").getJSONObject(0);
			JSONObject country = result.getJSONArray("country").getJSONObject(0);
					
			//cityField.setText(area.getString("value")+", "+country.getString("value"));
			cityField.setText(WeatherActivity.cityName);
			currentLocation.setText(WeatherActivity.current_location);
					
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void renderWeather_CITY(JSONObject json){
	    try {
	    	
	    	JSONObject data = json.getJSONObject("data");
			JSONArray location = data.getJSONArray("request");
	    	//if(json.getJSONObject("sys").getString("country").toUpperCase(Locale.US).equals("VIETNAM"))
	    	{
	    		
	    		cityField.setText(location.getJSONObject(0).getString("query"));
	    		
	    	}
//	    	
	    	
//	        detailsField.setText(
//	                details.getString("description").toUpperCase(Locale.US) +
//	                "\n" + "Humidity: " + main.getString("humidity") + "%" +
//	                "\n" + "Pressure: " + main.getString("pressure") + " hPa" +
//	                "\n" + "Min Temp: " + main.getString("temp_min") + "\u2103" +
//	                "\n" + "Max Temp: " + main.getString("temp_max") + "\u2103");
	        
	        
	        
	        
	        
	        JSONObject current_conditions = data.getJSONArray("current_condition").getJSONObject(0);
	        currentTemperatureField.setText(current_conditions.getString("temp_C"));//+ "\u2103");
	        
	        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<temp C: "+current_conditions.getString("temp_C"));
	        
	        JSONObject description = current_conditions.getJSONArray("weatherDesc").getJSONObject(0);
	    	        descriptionField.setText(description.getString("value").toUpperCase(Locale.US));
	 
//	        
	    	        
	    	JSONObject weatherUrl = current_conditions.getJSONArray("weatherIconUrl")
	    			.getJSONObject(0);
	    	URI weatherIconUrl = new URI(weatherUrl.getString("value"));
	    	
	    	String[] weatherIconName = weatherIconUrl.getPath().split("/");
	    	
	    	String iconRefName = weatherIconName[weatherIconName.length-1];
	    			
	    	System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<icon name: "+iconRefName);
	    	img= getBitmapFromAsset(iconRefName);
	    	weatherIcon.setImageBitmap(img);
	    	
	    	JSONObject weather = data.getJSONArray("weather").getJSONObject(0);
	    	tempMin.setText(weather.getString("mintempC")+ "\u2103");
	    	tempMax.setText("   "+weather.getString("maxtempC")+ "\u2103");
	    	winSpeed.setText("   "+current_conditions.getString("windspeedKmph")+" Km/h");
	    	winDeg.setText("   "+current_conditions.getString("winddirDegree")+ "°");
	    	humidity.setText("   "+current_conditions.getString("humidity")+ "%");
	    	pressure.setText("   "+current_conditions.getString("pressure")+" mb");
	    	visibility.setText("   "+current_conditions.getString("visibility")+" Km");
	    	
	    	JSONObject astronomy = weather.getJSONArray("astronomy").getJSONObject(0);
	    	sunrise.setText(astronomy.getString("sunrise"));
	    	sunset.setText(astronomy.getString("sunset"));
	    	
	    	
	        
	        
	 
//	        
//	         
	    }catch(Exception e){
	        Log.e("SimpleWeather", "One or more fields not found in the JSON data");
	    }
	}
	
	private void renderWeather_LOCATION(JSONObject json){
	    try {
	    	
	    	JSONObject data = json.getJSONObject("data");
			//JSONArray location = weather.getJSONArray("request");
	    	//if(json.getJSONObject("sys").getString("country").toUpperCase(Locale.US).equals("VIETNAM"))
//	    	{
//	    		
//	    		cityField.setText(location.getJSONObject(0).getString("query"));
//	    		
//	    	}
//	    	
	    	
//	        detailsField.setText(
//	                details.getString("description").toUpperCase(Locale.US) +
//	                "\n" + "Humidity: " + main.getString("humidity") + "%" +
//	                "\n" + "Pressure: " + main.getString("pressure") + " hPa" +
//	                "\n" + "Min Temp: " + main.getString("temp_min") + "\u2103" +
//	                "\n" + "Max Temp: " + main.getString("temp_max") + "\u2103");
	        
	        
	        
	        
	        
	        JSONObject current_conditions = data.getJSONArray("current_condition").getJSONObject(0);
	        currentTemperatureField.setText(current_conditions.getString("temp_C"));//+ "\u2103");
	        
	        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<temp C: "+current_conditions.getString("temp_C"));
	        
	        JSONObject description = current_conditions.getJSONArray("weatherDesc").getJSONObject(0);
	    	        descriptionField.setText(description.getString("value").toUpperCase(Locale.US));
	 
//	        
	    	        
	    	JSONObject weatherUrl = current_conditions.getJSONArray("weatherIconUrl")
	    			.getJSONObject(0);
	    	URI weatherIconUrl = new URI(weatherUrl.getString("value"));
	    	
	    	String[] weatherIconName = weatherIconUrl.getPath().split("/");
	    	
	    	String iconRefName = weatherIconName[weatherIconName.length-1];
	    			
	    	System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<icon name: "+iconRefName);
	    	img= getBitmapFromAsset(iconRefName);
	    	//img= getBitmapFromAsset("wsymbol_0003_white_cloud.png");
	    	weatherIcon.setImageBitmap(img);
	    	unitFied.setText("\u2103");
	    	
	    	JSONObject weather = data.getJSONArray("weather").getJSONObject(0);
	    	tempMin.setText(weather.getString("mintempC")+ "\u2103");
	    	tempMax.setText("   "+weather.getString("maxtempC")+ "\u2103");
	    	winSpeed.setText("   "+current_conditions.getString("windspeedKmph")+" Km/h");
	    	winDeg.setText("   "+current_conditions.getString("winddirDegree")+ "°");
	    	humidity.setText("   "+current_conditions.getString("humidity")+ "%");
	    	pressure.setText("   "+current_conditions.getString("pressure")+" mb");
	    	visibility.setText("   "+current_conditions.getString("visibility")+" Km");
	    	
	    	JSONObject astronomy = weather.getJSONArray("astronomy").getJSONObject(0);
	    	sunrise.setText(astronomy.getString("sunrise"));
	    	sunset.setText(astronomy.getString("sunset"));
	    	
	    	
	        
	        
	 
//	        
//	         
	    }catch(Exception e){
	        Log.e("SimpleWeather", "One or more fields not found in the JSON data");
	    }
	}
	
	private void setWeatherIcon(int actualId, long sunrise, long sunset){
	    int id = actualId / 100;
	    String icon = "";
	    if(actualId == 800){
	        long currentTime = new java.util.Date().getTime();
	        if(currentTime>=sunrise && currentTime<sunset) {
	            icon = getActivity().getString(R.string.weather_sunny);
	        } else {
	            icon = getActivity().getString(R.string.weather_clear_night);
	        }
	    } else {
	        switch(id) {
	        case 2 : icon = getActivity().getString(R.string.weather_thunder);
	                 break;         
	        case 3 : icon = getActivity().getString(R.string.weather_drizzle);
	                 break;     
	        case 7 : icon = getActivity().getString(R.string.weather_foggy);
	                 break;
	        case 8 : icon = getActivity().getString(R.string.weather_cloudy);
	                 break;
	        case 6 : icon = getActivity().getString(R.string.weather_snowy);
	                 break;
	        case 5 : icon = getActivity().getString(R.string.weather_rainy);
	                 break;
	        }
	    }
	    //weatherIcon.setText(icon);
	}
	
	
	public void changeCity(String city) {
		// TODO Auto-generated method stub
		updateWeatherData_CITY(city);
		
	}
	
	
	 
	public static String Filter(String str)
    {

        for (int i = 1; i < VietnameseSigns.length; i++)
        {

            for (int j = 0; j < VietnameseSigns[i].length(); j++)

                //str = str.Replace(VietnameseSigns[i][j], VietnameseSigns[0][i - 1]);
            	str=str.replace(VietnameseSigns[i].charAt(j),VietnameseSigns[0].charAt(i-1));
            

        }
        return str;
    }
	
	public Bitmap getBitmapFromAsset(String strName)
    {
        AssetManager assetManager = getActivity().getAssets();
        InputStream istr = null;
        try {
            istr = assetManager.open("icon/"+strName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(istr);
        return bitmap;
    }
}