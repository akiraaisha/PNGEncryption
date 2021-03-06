package com.jheto.pngencryption;

import com.jheto.pngencryption.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button buttonTest = (Button)findViewById(R.id.buttonTest);
		buttonTest.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				String data = getDataBitmap();
				if(data != null && data.length()>0) 
					Toast.makeText(getApplicationContext(), data, Toast.LENGTH_SHORT).show();
				else 
					Toast.makeText(getApplicationContext(), "Unknown Data", Toast.LENGTH_SHORT).show();
				
			}
		});
	}
	
	private String getDataBitmap(){
		String result = null;
		try{
			Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.eicar);
			if(bmp != null && bmp.getWidth()>0 && bmp.getHeight()>0){
				byte [] rawdata = PNGSerializer.decodeBinary(bmp);
				result = PNGSerializer.decodeText(rawdata);
			}
		}catch(Exception e){
			Log.e("Exception", e.toString());
		}
		return result;
	}
	
}
