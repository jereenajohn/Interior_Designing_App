package com.example.color_my_world;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class User_home extends Activity {
	Button b1,b2,b3,b4,b5,b6;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_home);
		
		b1=(Button)findViewById(R.id.btdesign);
		b2=(Button)findViewById(R.id.btfeed);
		b3=(Button)findViewById(R.id.btcomplaint);
		b4=(Button)findViewById(R.id.btreq);
		b5=(Button)findViewById(R.id.btprop);
		b6=(Button)findViewById(R.id.btlogout);
		
		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				final CharSequence[] items = {"Curtain","Room","Cancel"};


				AlertDialog.Builder builder = new AlertDialog.Builder(User_home.this);
				builder.setTitle("Select Option!");
				builder.setItems(items, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int item) {
						// TODO Auto-generated method stub
						if (items[item].equals("Curtain"))
						{
							Intent il=new Intent(getApplicationContext(),User_view_curtain_designs.class);
							startActivity(il);
						}
						else if (items[item].equals("Room"))
						{
							Intent il=new Intent(getApplicationContext(),User_view_room_designs.class);
							startActivity(il);
						}
						else if (items[item].equals("Cancel")) {
							dialog.dismiss();
						}
					}
				});
				builder.show();
				
//				startActivity(new Intent(getApplicationContext(),User_view_designs.class));
			}
		});
		
		b2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				startActivity(new Intent(getApplicationContext(),User_send_feedback.class));
			}
		});
		
		
		b3.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				startActivity(new Intent(getApplicationContext(),User_send_complaint.class));
			}
		});
		
		b4.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				startActivity(new Intent(getApplicationContext(),User_view_requirement.class));
			}
		});
		
		b5.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				startActivity(new Intent(getApplicationContext(),User_view_proposal.class));
			}
		});
		
		b6.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				startActivity(new Intent(getApplicationContext(),Login.class));
			}
		});
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_home, menu);
		return true;
	}

	public void onBackPressed()
	{
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent b=new Intent(getApplicationContext(),User_home.class);
		startActivity(b);
	}

}
