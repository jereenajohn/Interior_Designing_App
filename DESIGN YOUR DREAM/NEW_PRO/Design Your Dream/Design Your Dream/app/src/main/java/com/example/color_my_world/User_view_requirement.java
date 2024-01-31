package com.example.color_my_world;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class User_view_requirement extends Activity implements JsonResponse{
	ListView l1;
	String[] design_name,material,design_type,quantity,date,details;
	SharedPreferences sh;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_view_requirement);
		
		l1=(ListView)findViewById(R.id.lvrq);
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		
		JsonReq JR= new JsonReq();
		JR.json_response=(JsonResponse)User_view_requirement.this;
		String q="user_view_rq/?logid="+sh.getString("logid", "");
		JR.execute(q);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_view_requirement, menu);
		return true;
	}

	@Override
	public void response(JSONObject jo) {
		// TODO Auto-generated method stub
		
		try{
			String status=jo.getString("status");
			if(status.equalsIgnoreCase("success"))
			{
				JSONArray ja=(JSONArray)jo.getJSONArray("data");
				design_name= new String[ja.length()];
				material= new String[ja.length()];
				design_type= new String[ja.length()];
				quantity= new String[ja.length()];
				date=new String[ja.length()];
				details=new String[ja.length()];
				

				for(int i=0;i<ja.length();i++)
				{	
					design_name[i]=ja.getJSONObject(i).getString("name");
					material[i]=ja.getJSONObject(i).getString("material_name");
					design_type[i]=ja.getJSONObject(i).getString("design_type");
					quantity[i]=ja.getJSONObject(i).getString("quantity");
					date[i]=ja.getJSONObject(i).getString("requirement_date");
								
					details[i]="design_name : "+design_name[i]+"\nmaterial : "+material[i]+"\ndesign_type : "+design_type[i]+"\nquantity : "+quantity[i]+"\ndate : "+date[i];
					
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

}
