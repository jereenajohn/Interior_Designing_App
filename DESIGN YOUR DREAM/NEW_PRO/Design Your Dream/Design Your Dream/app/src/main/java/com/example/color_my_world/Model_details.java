package com.example.color_my_world;



import com.squareup.picasso.Picasso;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Model_details extends Activity {
	
	TextView t1,t3,t4;
	Button b1;
	String weight,price;
	ImageView im1;
	SharedPreferences sh;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_model_details);

		sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

		t1=(TextView)findViewById(R.id.textViewpname);
//		t2=(TextView)findViewById(R.id.textViewdesc);
		t3=(TextView)findViewById(R.id.textViewprice);
		t4=(TextView)findViewById(R.id.textViewquantity);
		im1=(ImageView)findViewById(R.id.imageView1);
		
		b1=(Button)findViewById(R.id.button1);
//		b2=(Button)findViewById(R.id.button2);
		
		t1.setText(sh.getString("product", ""));

		t3.setText(sh.getString("price", ""));
//		t4.setText(UserViewItems.quantitys);

		String pth = "http://"+sh.getString("ip", "")+"/"+sh.getString("images", "");
     
        Picasso.with((getApplicationContext()))
                .load(pth)
                .placeholder(R.drawable.ic_launcher)
                .error(R.drawable.ic_launcher).into(im1);
        
		 b1.setOnClickListener(new View.OnClickListener()
		 {
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					
					startActivity(new Intent(getApplicationContext(),PHOTO.class));
				}
			});
//		 b2.setOnClickListener(new View.OnClickListener() {
//
//				@Override
//				public void onClick(View arg0) {
//					// TODO Auto-generated method stub
//
//					startActivity(new Intent(getApplicationContext(),Payment.class));
//				}
//			});
		
	}


}
