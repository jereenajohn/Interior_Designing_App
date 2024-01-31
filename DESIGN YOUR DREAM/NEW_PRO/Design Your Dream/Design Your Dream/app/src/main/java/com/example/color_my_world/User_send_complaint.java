package com.example.color_my_world;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class User_send_complaint extends Activity implements JsonResponse{
	EditText e1;
	Button b1;
	ListView l1;
	String[] complaint,reply,date,details;
	String cmplnt;
	SharedPreferences sh;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_send_complaint);
		
		e1=(EditText)findViewById(R.id.etcomplaint);
		l1=(ListView)findViewById(R.id.lvcomplaint);
		b1=(Button)findViewById(R.id.btcomplaint);
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		
		JsonReq JR= new JsonReq();
		JR.json_response=(JsonResponse)User_send_complaint.this;
		String q="view_complaint/?logid="+sh.getString("logid", "");
		JR.execute(q);
		

		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				cmplnt=e1.getText().toString();
				
				
				JsonReq jr= new JsonReq();
				jr.json_response=(JsonResponse) User_send_complaint.this;
				String q="send_complaint/?Complaint="+cmplnt+"&logid="+sh.getString("logid","");
				q.replace("", "%20");
				jr.execute(q);
				
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_send_complaint, menu);
		return true;
	}

	@Override
	public void response(JSONObject jo) {
		// TODO Auto-generated method stub
		
		try{
			String method=jo.getString("method");
			if(method.equalsIgnoreCase("view_complaintxxxx"))
			{
		
		try{
			String status=jo.getString("status");
			if(status.equalsIgnoreCase("success"))
			{
				JSONArray ja=(JSONArray)jo.getJSONArray("data");
				complaint=new String[ja.length()];
				reply=new String[ja.length()];
				date= new String[ja.length()];
				
				details=new String[ja.length()];
				
				
				
				for(int i=0;i<ja.length();i++)
				{
					complaint[i]=ja.getJSONObject(i).getString("complaint");
					reply[i]=ja.getJSONObject(i).getString("reply");
					date[i]=ja.getJSONObject(i).getString("complaint_date");
					
					details[i]="Complaint : "+complaint[i]+"\nReply : "+reply[i]+"\nDate : "+date[i];
					Toast.makeText(getApplicationContext(), "haii", Toast.LENGTH_LONG).show();
					Toast.makeText(getApplicationContext(), "haii"+details, Toast.LENGTH_LONG).show();
					
				}
				//driver_list.setAdapter(new ArrayAdapter<String>(getApplicationContext(),R.layout.spin,ConfirmRide.name_s));
				l1.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,details));
			}
			else
			{
				Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_LONG).show();
			}
		}
		catch(Exception e){
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), "haii"+e, Toast.LENGTH_LONG).show();
		}
		
	}
			if(method.equalsIgnoreCase("send_complaint"))
			{
				try
				{
					String status=jo.getString("status");
					Toast.makeText(getApplicationContext(), status, Toast.LENGTH_LONG).show();
					if(status.equalsIgnoreCase("success"))
					{
						Toast.makeText(getApplicationContext(), "Complaint Send Successfully", Toast.LENGTH_LONG).show();
						startActivity(new Intent(getApplicationContext(),User_home.class));
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
	catch(Exception e){
		e.printStackTrace();
		Toast.makeText(getApplicationContext(), "haii"+e, Toast.LENGTH_LONG).show();
	}
		
	}

}
