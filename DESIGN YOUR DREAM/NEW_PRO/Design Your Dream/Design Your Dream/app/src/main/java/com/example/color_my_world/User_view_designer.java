package com.example.color_my_world;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class User_view_designer extends Activity implements JsonResponse {
	
	TextView t1,t2,t3,t4,t5;
	
	SharedPreferences sh;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_view_designer);
		t1=(TextView)findViewById(R.id.tvcompany);
		t2=(TextView)findViewById(R.id.tvdesigner);
		t3=(TextView)findViewById(R.id.tvplace);
		t4=(TextView)findViewById(R.id.tvphone);
		t5=(TextView)findViewById(R.id.tvemail);
	
		
		
		
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		
		
		JsonReq JR= new JsonReq();
		JR.json_response=(JsonResponse)User_view_designer.this;
		String q="user_view_designer/?d_id="+sh.getString("d_id", "") ;
		JR.execute(q);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_message_designer, menu);
		return true;
	}

	@Override
	public void response(JSONObject jo) {
		// TODO Auto-generated method stub
		
		
		 try {
	            String status = jo.getString("status");
	            Log.d("result", status);

	            if (status.equalsIgnoreCase("success")) {
	                JSONArray ja = (JSONArray) jo.getJSONArray("data");
	                t1.setText(ja.getJSONObject(0).getString("company_name"));
	                t2.setText(ja.getJSONObject(0).getString("d_name"));
	                t3.setText(ja.getJSONObject(0).getString("place"));
	                t4.setText(ja.getJSONObject(0).getString("phone"));
	                t5.setText(ja.getJSONObject(0).getString("email"));
	                
	                
	                //startService(new Intent(getApplicationContext(), LocationService.class));

	               
	            } else {
	                Toast.makeText(getApplicationContext(), "Failed!!", Toast.LENGTH_LONG).show();
	            }
	        }  catch (Exception e) {
	            e.printStackTrace();
	            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
	        }
		
	}

}
