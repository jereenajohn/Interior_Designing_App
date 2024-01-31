package com.example.color_my_world;

import org.json.JSONObject;


import com.squareup.picasso.Picasso;

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
import android.widget.ImageView;
import android.widget.Toast;

public class User_requirement extends Activity implements JsonResponse {
	EditText e1,e2;
	Button b1;
	ImageView img1;
	String type,qnty;
	SharedPreferences sh;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_requirement);
		
		e1=(EditText)findViewById(R.id.ettype);
		e2=(EditText)findViewById(R.id.etqnty);
		img1=(ImageView)findViewById(R.id.imgdesign);
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		
		
		   
			  String pth = "http://"+sh.getString("ip", "")+User_view_designs.dsgn_imd;
			  pth = pth.replace("~", "");
			   
			   Log.d("-------------", pth);
			   Picasso.with(getApplicationContext())
			           .load(pth)
			           .placeholder(R.drawable.ic_launcher)
			           .error(R.drawable.ic_launcher).into(img1);
		
		
		b1=(Button)findViewById(R.id.btreq);
		
		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				type=e1.getText().toString();
				qnty=e2.getText().toString();
				
				String q="";
				JsonReq jr= new JsonReq();
				jr.json_response=(JsonResponse) User_requirement.this;
				if(sh.getString("type","").toString().equalsIgnoreCase("room")) {
					 q = "send_requirement/?design_type=" + type + "&qnty=" + qnty + "&logid=" + sh.getString("logid", "") + "&design_id=" +  sh.getString("dsgn_id", "") + "&material_id=" + sh.getString("mat_id", "");
				}
				if(sh.getString("type","").toString().equalsIgnoreCase("curtain")) {
					q = "send_requirement/?design_type=" + type + "&qnty=" + qnty + "&logid=" + sh.getString("logid", "") + "&design_id=" +  sh.getString("dsgn_id", "") + "&material_id=" + sh.getString("mat_id", "");
				}
				q.replace("", "%20");
				jr.execute(q);
			}
		});

		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_requirement, menu);
		return true;
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
				Toast.makeText(getApplicationContext(), "Requirement Send Successfully", Toast.LENGTH_LONG).show();
				startActivity(new Intent(getApplicationContext(),User_view_requirement.class));
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


