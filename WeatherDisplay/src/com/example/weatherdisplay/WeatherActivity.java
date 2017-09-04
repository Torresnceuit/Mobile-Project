package com.example.weatherdisplay;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class WeatherActivity extends Activity {
	
	public static String inputText;
	public static GPSTracker mGPS;
	public static String cityName;
	public static String current_location; 
	public static double myLat;
	public static double myLong;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_weather);
	 
	    if (savedInstanceState == null) {
	        getFragmentManager().beginTransaction()
	                .add(R.id.container, new WeatherFragment())
	                .commit();
	    }
	    
	    
         
        
	    
	    mGPS = new GPSTracker(this);
	    GPSTracker mGPS = new GPSTracker(this);
	    //TextView location = (TextView) findViewById(R.id.location_field);
	    if(mGPS.canGetLocation())
	    {
	    	mGPS.getLocation();
	    	myLat= mGPS.getLatitude();
	    	myLong= mGPS.getLongitude();
	    	//location= "Lat"+mGPS.getLatitude()+"Lon"+mGPS.getLongitude();
	    	
	    	//List<Address> addresses;
	    	
			try {
				Geocoder geocoder = new Geocoder(this, Locale.getDefault());
				System.out.println("Address = ");
				List<Address> addresses = geocoder.getFromLocation(mGPS.getLatitude(),mGPS.getLongitude(),1);
				int len = addresses.get(0).getMaxAddressLineIndex();
				
				
				current_location =  "Your current location: "+addresses.get(0).getAddressLine(0);
				for(int i=1;i<=len;i++)
				{
					current_location += ", "+addresses.get(0).getAddressLine(i);
				}
				cityName = addresses.get(0).getAddressLine(len-1)+
						", "+addresses.get(0).getAddressLine(len);
				
				//cityName = "lat="+WeatherActivity.myLat+"&lon="+WeatherActivity.myLong;
				System.out.println("get here <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
				System.out.println("can get <<<<<<<<<<"+addresses);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	    	
	    }
	    else
	    {
	    	System.out.println("Unable");
	    }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.weather, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		if(id == R.id.change_city){
			showInputDialog();
		}
		return false;
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_weather,
					container, false);
			
			return rootView;
		}
	}
	
	private void showInputDialog(){
	    AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    builder.setTitle("Change city");
	    final EditText input = new EditText(this);
	    input.setInputType(InputType.TYPE_CLASS_TEXT);
	    builder.setView(input);
	    builder.setPositiveButton("Go", new DialogInterface.OnClickListener() {
	        @Override
	        public void onClick(DialogInterface dialog, int which) {
	        	System.out.println("chi tiet o day <<<<<<<<<<<<<<<<");
	        	inputText= Filter(input.getText().toString());
	        	if(inputText.toLowerCase().equals("ho chi minh"))
	        	{
	        		//inputText="Thanh pho Ho Chi Minh";
	        		inputText="thanh pho ho chi minh";
	        		
	        		changeCity(inputText);
	        	}
	        	else
	        	{
	        		changeCity(Filter(input.getText().toString()));
	        		
	        	}
	        }
	    });
	    builder.show();
	}
	 
	public void changeCity(String city){
	    WeatherFragment wf = (WeatherFragment)getFragmentManager().findFragmentById(R.id.container);
	    wf.changeCity(city);
	    
	    new CityPreference(this).setCity(city);
	}
	
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
}
