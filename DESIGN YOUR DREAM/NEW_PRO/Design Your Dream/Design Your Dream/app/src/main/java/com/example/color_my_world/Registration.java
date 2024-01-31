package com.example.color_my_world;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.app.DatePickerDialog;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

public class Registration extends Activity implements JsonResponse{
	EditText e1,e2,e3,e4,e5,e6,e7,e8,e9,e10;
	Button b1;
	String fname,lname,dob,house,place,pincode,phone,email,user,pass;
	String[] sid,sname;
	public static String sids;
	private int mYear, mMonth, mDay, mHour, mMinute;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);
		
		
		e1=(EditText)findViewById(R.id.etfirst);
		e2=(EditText)findViewById(R.id.etlast);
		e3=(EditText)findViewById(R.id.etdob);
		e4=(EditText)findViewById(R.id.ethouse);
		e5=(EditText)findViewById(R.id.etplace);
		e6=(EditText)findViewById(R.id.etpincode);
		e7=(EditText)findViewById(R.id.etphone);
		e8=(EditText)findViewById(R.id.etemail);
		e9=(EditText)findViewById(R.id.etuser);
		e10=(EditText)findViewById(R.id.etpass);

		final Calendar calendar=Calendar.getInstance() ;
		final int year = calendar.get(calendar.YEAR);
		final int month =calendar.get(calendar.MONTH);
		final  int day =calendar.get(calendar.DAY_OF_MONTH);


		e3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				DatePickerDialog dialog = new DatePickerDialog(Registration.this, new DatePickerDialog.OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker view, int year,
										  int monthOfYear, int dayOfMonth) {

						e4.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

					}
				}, mYear, mMonth, mDay);
				dialog.show();
			}
		});
		
		b1=(Button)findViewById(R.id.btregister);
		
		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				fname=e1.getText().toString();
				lname=e2.getText().toString();
				dob=e3.getText().toString();
				house=e4.getText().toString();
				place=e5.getText().toString();
				pincode=e6.getText().toString();
				phone=e7.getText().toString();
				email=e8.getText().toString();
				user=e9.getText().toString();
				pass=e10.getText().toString();

				if(fname.equalsIgnoreCase("")|| !fname.matches("[a-zA-Z ]+"))
				{
					e1.setError("Enter your firstname");
					e1.setFocusable(true);
				}
				else if(lname.equalsIgnoreCase("")|| !lname.matches("[a-zA-Z ]+"))
				{
					e2.setError("Enter your lastname");
					e2.setFocusable(true);
				}

				else if(house.equalsIgnoreCase("")|| !house.matches("[a-zA-Z ]+"))
				{
					e4.setError("Enter your house");
					e4.setFocusable(true);
				}

				else if(place.equalsIgnoreCase("")|| !place.matches("[a-zA-Z ]+"))
				{
					e5.setError("Enter your place");
					e5.setFocusable(true);
				}

				else if(pincode.equalsIgnoreCase("") || pincode.length()!=6)
				{
					e6.setError("Enter your pincode");
					e6.setFocusable(true);
				}

				else if(phone.equalsIgnoreCase("") || phone.length()!=10)
				{
					e7.setError("Enter your phone");
					e7.setFocusable(true);
				}
				else if(email.equalsIgnoreCase("") || !email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+"))
				{
					e8.setError("Enter your email");
					e8.setFocusable(true);
				}




				else if(user.equalsIgnoreCase(""))
				{
					e9.setError("Enter your username");
					e9.setFocusable(true);
				}
				else if(pass.equalsIgnoreCase(""))
				{
					e10.setError("Enter your password");
					e10.setFocusable(true);
				}else {


//			
					JsonReq jr = new JsonReq();
					jr.json_response = (JsonResponse) Registration.this;
					String q = "register/?firstname=" + fname + "&lastname=" + lname + "&dob=" + dob + "&house=" + house + "&place=" + place + "&pincode=" + pincode + "&phone=" + phone + "&email=" + email + "&user=" + user + "&pass=" + pass;
					q.replace(" ", "%20");
					jr.execute(q);
				}
				
			}
		});
	}
		


	@Override
	public void response(JSONObject jo) {
		// TODO Auto-generated method stub
		
		try
		{
			String status=jo.getString("status");
			Toast.makeText(getApplicationContext(), status, Toast.LENGTH_LONG).show();
			if(status.equalsIgnoreCase("success"))
			{
				Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_LONG).show();
				startActivity(new Intent(getApplicationContext(),Login.class));
			}
			
			else
			{
				Toast.makeText(getApplicationContext(), "Not Successfull", Toast.LENGTH_LONG).show();
			}
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), "Hai"+e, Toast.LENGTH_LONG).show();
		}
	
	}
	
	

}

