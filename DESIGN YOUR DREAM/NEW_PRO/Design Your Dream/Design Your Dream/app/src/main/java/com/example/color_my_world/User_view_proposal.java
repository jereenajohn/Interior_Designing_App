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

public class User_view_proposal extends Activity implements JsonResponse, OnItemClickListener {
	ListView l1;
	String[] proposal_status,proposal_id,designer_id,designer_name,design_name,material_name,design_type,quantity,price_per_piece,requirement_date,proposal_date,price,details;
	SharedPreferences sh;
	public static String d_id,prop_id,amount,proposal_statuss;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_view_proposal);
		
		l1=(ListView)findViewById(R.id.lvproposal);
		l1.setOnItemClickListener(this);
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		
		JsonReq JR= new JsonReq();
		JR.json_response=(JsonResponse)User_view_proposal.this;
		String q="user_view_proposal/?logid="+sh.getString("logid", "");
		JR.execute(q);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_view_proposal, menu);
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
				designer_id= new String[ja.length()];
				proposal_id= new String[ja.length()];
				designer_name= new String[ja.length()];
				design_name= new String[ja.length()];
				material_name= new String[ja.length()];
				design_type= new String[ja.length()];
				quantity=new String[ja.length()];
				price_per_piece=new String[ja.length()];
				requirement_date=new String[ja.length()];
				proposal_date=new String[ja.length()];
				price=new String[ja.length()];
				proposal_status=new String[ja.length()];
				details=new String[ja.length()];

				
				
				
				for(int i=0;i<ja.length();i++)
				{	
					designer_id[i]=ja.getJSONObject(i).getString("designer_id");
					proposal_id[i]=ja.getJSONObject(i).getString("proposal_id");
					designer_name[i]=ja.getJSONObject(i).getString("designer_name");
					design_name[i]=ja.getJSONObject(i).getString("name");
					material_name[i]=ja.getJSONObject(i).getString("material_name");
					design_type[i]=ja.getJSONObject(i).getString("design_type");
					quantity[i]=ja.getJSONObject(i).getString("quantity");
					price_per_piece[i]=ja.getJSONObject(i).getString("price_per_piece");
					requirement_date[i]=ja.getJSONObject(i).getString("requirement_date");
					proposal_date[i]=ja.getJSONObject(i).getString("proposal_date");
					price[i]=ja.getJSONObject(i).getString("price");
					proposal_status[i]=ja.getJSONObject(i).getString("proposal_status");
			
					details[i]="designer_name : "+designer_name[i]+"\ndesign_name : "+design_name[i]+"\nmaterial_name : "+material_name[i]+"\ndesign_type : "+design_type[i]+"\nquantity : "+quantity[i]+"\nprice_per_piece : "+price_per_piece[i]+"\nrequirement_date : "+requirement_date[i]+"\nproposal_date : "+proposal_date[i]+"\nprice : "+price[i]+"\nproposal_status : "+proposal_status[i];
					
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
		prop_id = proposal_id[arg2];
		amount = price[arg2];
		proposal_statuss = proposal_status[arg2];

		if (proposal_statuss.equalsIgnoreCase("pending")) {


			final CharSequence[] items = {"Chat", "Make Payment", "Cancel"};


			AlertDialog.Builder builder = new AlertDialog.Builder(User_view_proposal.this);
			builder.setTitle("Select Option!");
			builder.setItems(items, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int item) {
					// TODO Auto-generated method stub
					if (items[item].equals("Chat")) {
						Intent il = new Intent(getApplicationContext(), ChatHere.class);
						startActivity(il);
					} else if (items[item].equals("Make Payment")) {
						Intent il = new Intent(getApplicationContext(), User_payment.class);
						startActivity(il);
					} else if (items[item].equals("Cancel")) {
						dialog.dismiss();
					}
				}
			});
			builder.show();

		}

		else{

			final CharSequence[] items = {"Chat",  "Cancel"};


			AlertDialog.Builder builder = new AlertDialog.Builder(User_view_proposal.this);
			builder.setTitle("Select Option!");
			builder.setItems(items, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int item) {
					// TODO Auto-generated method stub
					if (items[item].equals("Chat")) {
						Intent il = new Intent(getApplicationContext(), ChatHere.class);
						startActivity(il);
					}  else if (items[item].equals("Cancel")) {
						dialog.dismiss();
					}
				}
			});
			builder.show();

		}
	}

}