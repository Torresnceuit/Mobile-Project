package com.example.weatherdisplay;
 
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
 
import org.json.JSONObject;
 
import android.R.string;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
 
public class RemoteFetch {
	
	private static String key = "ddfd176915d4c42ff4ac5c42b7c13";
	
	private static final String SEARCH_LOCAL = 
			"http://api.worldweatheronline.com/free/v2/search.ashx?q=%s&format=json&key="+key;
 
    private static final String OPEN_WEATHER_MAP_API_CITY = 
            "http://api.worldweatheronline.com/free/v2/weather.ashx?q=%s&format=json&key="+key;
    		//"http://api.openweathermap.org/data/2.5/weather?q=%s&units";
    
    private static final String OPEN_WEATHER_MAP_API_LOCATION =
    		"http://api.worldweatheronline.com/free/v2/weather.ashx?q=%s&format=json&key="+key;
            //"http://api.openweathermap.org/data/2.5/weather?%s&units=metric";
    
    private static String IMG_URL = "http://openweathermap.org/img/w/";
    
    public static JSONObject getJSON_SEARCH(Context context, String city){
    	try {
        	city = city.replaceAll(" ", "%20");
            URL url = new URL(String.format(SEARCH_LOCAL, city));           
            HttpURLConnection connection = 
                    (HttpURLConnection)url.openConnection();
            System.out.println(url);
             

            
            int status = connection.getResponseCode();
             

            BufferedReader reader;
            
            
            if(status >= 400)
            {
            	reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            }
            else
            {
            	reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            }
            

             
            StringBuffer json = new StringBuffer(1024);
            String tmp="";
            while((tmp=reader.readLine())!=null)
                json.append(tmp).append("\n");
            reader.close();
            
            System.out.println(json);
            JSONObject data = new JSONObject(json.toString());
             

             
            return data;
        }catch(Exception e){
        	System.out.println("<<<<<<<<<<<<<<<<<<<NULL<<<<<<<<<<<<<<<");
        	e.printStackTrace();
            return null;
        }
    } 
    
    
     
    public static JSONObject getJSON_CITY(Context context, String city){
        try {
        	city = city.replaceAll(" ", "%20");
            URL url = new URL(String.format(OPEN_WEATHER_MAP_API_CITY, city));           
            HttpURLConnection connection = 
                    (HttpURLConnection)url.openConnection();
            System.out.println(url);
             

            
            int status = connection.getResponseCode();
             

            BufferedReader reader;
            
            
            if(status >= 400)
            {
            	reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            }
            else
            {
            	reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            }
            

             
            StringBuffer json = new StringBuffer(1024);
            String tmp="";
            while((tmp=reader.readLine())!=null)
                json.append(tmp).append("\n");
            reader.close();
            
            System.out.println(json);
            JSONObject data = new JSONObject(json.toString());
             

             
            return data;
        }catch(Exception e){
        	System.out.println("<<<<<<<<<<<<<<<<<<<NULL<<<<<<<<<<<<<<<");
        	e.printStackTrace();
            return null;
        }
    }   


public static JSONObject getJSON_LOCATION(Context context, String city){
    try {
        URL url = new URL(String.format(OPEN_WEATHER_MAP_API_LOCATION, city));           
        HttpURLConnection connection = 
                (HttpURLConnection)url.openConnection();
         
//        connection.addRequestProperty("x-api-key", 
//                context.getString(R.string.open_weather_maps_app_id));
         
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
         
        StringBuffer json = new StringBuffer(1024);
        String tmp="";
        while((tmp=reader.readLine())!=null)
            json.append(tmp).append("\n");
        reader.close();
         
        JSONObject data = new JSONObject(json.toString());
         
        // This value will be 404 if the request was not
        // successful
//        if(data.getInt("cod") != 200){
//            return null;
//        }
         
        return data;
    }catch(Exception e){
        return null;
    }
} 

public static byte[] getImage(String weatherUrl) {
	HttpURLConnection con = null ;
	InputStream is = null;
	try {
		con = (HttpURLConnection) ( new URL(weatherUrl)).openConnection();
		
		con.setRequestMethod("GET");
		
		con.setDoInput(true);
		
		con.setDoOutput(true);
		
		con.connect();
		
		// Let's read the response
		is = con.getInputStream();
		byte[] buffer = new byte[1024];
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		int readedByte = 0;
		  while ((readedByte = is.read(buffer) )!= -1){
		  if(readedByte > 0)
		   baos.write(buffer,0,readedByte);
		  System.out.println(weatherUrl);
		}
		
		
		return baos.toByteArray();
    }
	catch(Throwable t) {
		t.printStackTrace();
	}
	finally {
		try { is.close(); } catch(Throwable t) {}
		try { con.disconnect(); } catch(Throwable t) {}
	}
	
	
	return null;
	
}

public static Bitmap GetBitmapfromUrl(String weatherUrl) {
    try {
        URL url=new URL(weatherUrl);
        HttpURLConnection connection=(HttpURLConnection)url.openConnection();
        connection.setDoInput(true);
        connection.connect();
        InputStream input=connection.getInputStream();
        Bitmap bmp = BitmapFactory.decodeStream(input);
        return bmp;



    } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
        return null;

    }
}

}
