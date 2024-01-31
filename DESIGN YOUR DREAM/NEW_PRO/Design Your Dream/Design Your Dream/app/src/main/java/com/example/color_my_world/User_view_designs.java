package com.example.color_my_world;



import org.json.JSONArray;
import org.json.JSONObject;



import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class User_view_designs extends Activity implements JsonResponse, OnItemClickListener{
	ListView l1;
	SharedPreferences sh;
	String[] design_id,material_id,designer_id,photo,name,material,des,color,price,details;
	public static String d_id,dsgn_id,mat_id,dsgn_imd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_view_designs);
		
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		
		l1=(ListView)findViewById(R.id.lvdesign);
		l1.setOnItemClickListener(this);
		
		JsonReq JR= new JsonReq();
		JR.json_response=(JsonResponse)User_view_designs.this;
		String q="user_view_design/";
		JR.execute(q);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_view_designs, menu);
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
				design_id= new String[ja.length()];
				material_id= new String[ja.length()];
				designer_id= new String[ja.length()];
				photo= new String[ja.length()];
				name=new String[ja.length()];
				material=new String[ja.length()];
				des=new String[ja.length()];
				color=new String[ja.length()];
				price=new String[ja.length()];
				details=new String[ja.length()];
				
				
				
				for(int i=0;i<ja.length();i++)
				{	
					design_id[i]=ja.getJSONObject(i).getString("design_id");
					material_id[i]=ja.getJSONObject(i).getString("material_id");
					designer_id[i]=ja.getJSONObject(i).getString("designer_id");
					photo[i]=ja.getJSONObject(i).getString("photo");
					name[i]=ja.getJSONObject(i).getString("name");
					material[i]=ja.getJSONObject(i).getString("material_name");
					des[i]=ja.getJSONObject(i).getString("description");
					color[i]=ja.getJSONObject(i).getString("color");
					price[i]=ja.getJSONObject(i).getString("price_per_piece");
			
					details[i]="photo : "+photo[i]+"\nname : "+name[i]+"\nmaterial : "+material[i]+"\ndes : "+des[i]+"\ncolor : "+color[i]+"\nprice : "+price[i];
					
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

	@Override
	
	
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		 d_id = designer_id[arg2];
		 dsgn_id = design_id[arg2];
		 mat_id = material_id[arg2];
		 dsgn_imd = photo[arg2];
		
		final CharSequence[] items = {"View Designer","Send Requirement","Cancel"};
		
		
		AlertDialog.Builder builder = new AlertDialog.Builder(User_view_designs.this);
		builder.setTitle("Select Option!");
		builder.setItems(items, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int item) {
				// TODO Auto-generated method stub
				if (items[item].equals("View Designer"))
				{
					Intent il=new Intent(getApplicationContext(),User_view_designer.class);
					startActivity(il);
				}
				else if (items[item].equals("Send Requirement"))
				{
					Intent il=new Intent(getApplicationContext(),User_requirement.class);
					startActivity(il);
				}
				 else if (items[item].equals("Cancel")) {
	                    dialog.dismiss();
	                }
			}
		});
		builder.show();
		
	}
}