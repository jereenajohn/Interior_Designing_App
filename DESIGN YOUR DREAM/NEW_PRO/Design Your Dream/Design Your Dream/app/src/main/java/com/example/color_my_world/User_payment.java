package com.example.color_my_world;

import org.json.JSONObject;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class User_payment extends Activity implements JsonResponse{
	
	TextView t1;
	EditText e1,e2,e3,e4;
	Button b1;
	SharedPreferences sh;
	String amount,card,cvv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_payment);
		
		t1=(TextView)findViewById(R.id.tvamount);
		e1=(EditText)findViewById(R.id.editText1);
		e2=(EditText)findViewById(R.id.editText2);
		e3=(EditText)findViewById(R.id.editText3);
		e4=(EditText)findViewById(R.id.editText4);
		b1=(Button)findViewById(R.id.btpay);
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		
		t1.setText(User_view_proposal.amount);
		JsonReq JR= new JsonReq();
		JR.json_response=(JsonResponse)User_payment.this;
		String q="user_view_amount/?prop_id="+User_view_proposal.prop_id;
		JR.execute(q);
		
		
		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				amount=t1.getText().toString();

				card=e1.getText().toString();
				cvv=e4.getText().toString();

				if(card.equalsIgnoreCase("")|| card.length()!=16)
				{
					e1.setError("Enter your 16 digits card number");
					e1.setFocusable(true);
				}

				else if(cvv.equalsIgnoreCase("")|| cvv.length()!=3)
				{
					e4.setError("Enter your 3 digits C V V ");
					e4.setFocusable(true);
				}

				else {


					JsonReq jr = new JsonReq();
					jr.json_response = (JsonResponse) User_payment.this;
					String q = "user_payment/?logid=" + sh.getString("logid", "") + "&amount=" + User_view_proposal.amount + "&prop_id=" + User_view_proposal.prop_id;
					q.replace("", "%20");
					jr.execute(q);
				}
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_payment, menu);
		return true;
	}

	@Override
	public void response(JSONObject jo) {
		// TODO Auto-generated method stub
		try{
			String method=jo.getString("method");
			if(method.equalsIgnoreCase("user_view_amount"))
			{
		
				try{
					String status=jo.getString("status");
					if(status.equalsIgnoreCase("Success"))
					{
		//				JSONArray ja = (JSONArray) jo.getJSONArray("data");
		//                t1.setText(ja.getJSONObject(0).getString("total_amount"));
						String sh="";
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
			else if(method.equalsIgnoreCase("user_payment"))
			{
				try
				{
					String status=jo.getString("status");
					Toast.makeText(getApplicationContext(), status, Toast.LENGTH_LONG).show();
					if(status.equalsIgnoreCase("Success"))
					{
						Toast.makeText(getApplicationContext(), "Payment Successfully", Toast.LENGTH_LONG).show();
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
