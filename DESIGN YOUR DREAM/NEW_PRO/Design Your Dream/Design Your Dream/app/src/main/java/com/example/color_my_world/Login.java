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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity implements JsonResponse {
	EditText e1,e2;
	Button b1,b2;
	String user,pass;
	public static String logid,type;
	SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        e1=(EditText)findViewById(R.id.etuser);
        e2=(EditText)findViewById(R.id.etpass);
        
        b1=(Button)findViewById(R.id.btlogin);
        b2=(Button)findViewById(R.id.btsignup);
        sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        
        b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				user=e1.getText().toString();
				pass=e2.getText().toString();

				if(user.equalsIgnoreCase(""))
				{
					e1.setError("Enter your username");
					e1.setFocusable(true);
				}
				else if(pass.equalsIgnoreCase(""))
				{
					e2.setError("Enter your password");
					e2.setFocusable(true);
				}else {

					JsonReq JR = new JsonReq();
					JR.json_response = (JsonResponse) Login.this;
					String q = "login/?username=" + user + "&password=" + pass;
					JR.execute(q);
				}
			}
		});
        b2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(),Registration.class));
				
			}
		});
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
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
	                logid = ja.getJSONObject(0).getString("login_id");
	                type = ja.getJSONObject(0).getString("usertype");
	                SharedPreferences.Editor ed = sh.edit();
	                ed.putString("logid", logid);
	                ed.commit();

	                //startService(new Intent(getApplicationContext(), LocationService.class));

	                if (type.equals("user"))
	                {
	                	Toast.makeText(getApplicationContext(), "Login Successfull....." , Toast.LENGTH_LONG).show();
//	                	startService(new Intent(getApplicationContext(),LocationService.class));
	                    startActivity(new Intent(getApplicationContext(), User_home.class));
	               
	                }
	                

	            } else {
	                Toast.makeText(getApplicationContext(), "Login failed.TRY AGAIN!!", Toast.LENGTH_LONG).show();
	            }
	        }  catch (Exception e) {
	            e.printStackTrace();
	            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
	        }
	}

	public void onBackPressed()
	{
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent b=new Intent(getApplicationContext(),IPsetting.class);
		startActivity(b);
	}

}
