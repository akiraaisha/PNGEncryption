package com.jheto.pngencryption;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;

public class PNGSerializer {

	private PNGSerializer() { }
	
	public static byte[] encodeText(String message)
    {
        byte[] bytes = null;
        try {
        	bytes = message.getBytes("UTF-8");
        } catch (Exception e) {
            bytes = null;
        }
        return bytes;
    }
	
	public static String decodeText(byte[] bytes)
    {
		String message = null;
        try {
        	message = new String(bytes, "UTF-8");
        } catch (Exception e) {
            message = null;
        }
        return message;
    }

	public static Bitmap encodeBinary(byte[] bytes)
    {
        Bitmap bmp = null;
        try {
        	int sqrt = (int)Math.ceil(Math.sqrt(bytes.length));
        	 bmp = Bitmap.createBitmap(sqrt, sqrt, Bitmap.Config.ARGB_8888);
        	 for (int w = 0, index = 0; w < sqrt; w++) {
                for (int h = 0; h < sqrt; h++) {
                    if (index < bytes.length) {
                        int code = (int)bytes[index];
                        int color = Color.argb(255, 0, 0, code);
                        bmp.setPixel(w, h, color);
                    }
                    else {
                    	int color = Color.argb(0, 0, 0, 0);
                        bmp.setPixel(w, h, color);
                    }
                    index++;
                }
            }
        }
        catch (Exception e)
        {
            bmp = null;
        }
        return bmp;
    }

	public static byte[] decodeBinary(Bitmap bmp)
    {
        byte[] bytes = null;
        try
        {
            int wSize = bmp.getWidth(), hSize = bmp.getHeight();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            for (int w = 0; w < wSize; w++) {
                for (int h = 0; h < hSize; h++) {
                    int color = bmp.getPixel(w, h);
                    if (Color.alpha(color) == 255) stream.write(Color.blue(color));
                    else break;
                }
            }
            bytes = stream.toByteArray();
            stream = null;
        }
        catch (Exception e)
        {
            bytes = null;
        }
        return bytes;
    }
	
	public static byte[] bitmapToArray(Bitmap bmp) {
        byte[] byteArray = null;
        try {
        	ByteArrayOutputStream stream = new ByteArrayOutputStream();
        	bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        	byteArray = stream.toByteArray();
        } catch (Exception e) {
            byteArray = null;
        }
        return byteArray;
    }
	
	public static Bitmap arrayToBitmap(byte[] array)
    {
        Bitmap bmp = null;
        try {
        	ByteArrayInputStream stream = new ByteArrayInputStream(array);
        	bmp = BitmapFactory.decodeStream(stream); 
        } catch (Exception e)        {
            bmp = null;
        }
        return bmp;
    }

	public static void doIt()
    {
        try {
        	String str = "X5O!P%@AP[4\\PZX54(P^)7CC)7}$EICAR-STANDARD-ANTIVIRUS-TEST-FILE!$H+H*";
            byte[] rawdata = PNGSerializer.encodeText(str);
            Bitmap bmp = PNGSerializer.encodeBinary(rawdata);
            byte[] serial = PNGSerializer.bitmapToArray(bmp);
            bmp = PNGSerializer.arrayToBitmap(serial);
            rawdata = PNGSerializer.decodeBinary(bmp);
            String text = PNGSerializer.decodeText(rawdata);
            if(text != null && text.length()>0) Log.e("text", text);
        }
        catch (Exception e) { }
    }
	
}
