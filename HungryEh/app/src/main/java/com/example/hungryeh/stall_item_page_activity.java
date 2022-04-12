package com.example.hungryeh;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class stall_item_page_activity extends AppCompatActivity {
    stall myStall;
    String str_TimeslotSelection;
    TextView textView;
    NumberPicker numberPicker;
    TextView txtvw_schedule_selection;

    public stall_item_page_activity(){

    }

//    ActivityResultLauncher<Intent> activityLauncher = registerForActivityResult(
//            new ActivityResultContracts.StartActivityForResult(),
//            new ActivityResultCallback<ActivityResult>() {
//                @Override
//                public void onActivityResult(ActivityResult result) {
//                    if(result.getResultCode(70))
//
//                }
//            }
//    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.items_food_page);
        Intent intent = getIntent();

        //initialize shared pref
        initalizeIntent();

        TextView txtvw_foodpage_dish = findViewById(R.id.foodpage_dish);
        TextView txtvw_foodpage_stall = findViewById(R.id.foodpage_stall);
        TextView txtvw_foodpage_price= findViewById(R.id.cost);
        TextView txtvw_foodpage_allergens = findViewById(R.id.ind_allergens);
        TextView txtvw_foodpage_veg = findViewById(R.id.ind_veg);

/*
        stall stall = intent.getParcelableExtra("stall");
        String imageRes=stall.getImg();
        ImageView imageView=findViewById(R.id.imagein);
        imageview.image.setimageResource(imageRes)
        Glide.with(getApplicationContext()).load(stall.getImg()).into(imageView);
*/
        ImageView img_foodpage_image=findViewById(R.id.imagein);
        Glide.with(stall_item_page_activity.this)
                .load(this.myStall.img)
                .centerCrop()
                .into(img_foodpage_image);



        txtvw_foodpage_dish.setText(this.myStall.dishName);
        txtvw_foodpage_stall.setText(this.myStall.stallName);
        txtvw_foodpage_price.setText(this.myStall.price);
        txtvw_foodpage_allergens.setText(this.myStall.allergens);
        txtvw_foodpage_veg.setText(this.myStall.veg);




        txtvw_schedule_selection = findViewById(R.id.schedule_selection);

        txtvw_schedule_selection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openDialog();
            }
        });




    }

    private void initalizeIntent() {
        SharedPreferences sharedPreferences = getSharedPreferences("myStall", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Intent intent = getIntent();
        stall myStall_data = intent.getParcelableExtra("myStall_data");
        if( myStall_data != null ) {
            this.myStall = myStall_data;
            Gson gson = new Gson();
            String strobj_myStall = gson.toJson(myStall_data);
            editor.putString("strobj_myStall",strobj_myStall);
            editor.apply();
        }
        else {
            //SharedPreferences sharedPreferences = getSharedPreferences("strobj_myStall", MODE_PRIVATE);
            Gson gson = new Gson();
            String json = sharedPreferences.getString("strobj_myStall",null);
            Type type = new TypeToken<stall>() {}.getType();
            if (json != null & json != "" )
            {
                //this.myStall = gson.fromJson(json, type);
                stall newStall = gson.fromJson(json, type);
                this.myStall = newStall;
            }
        }
        String str_TimeslotSelection_value = intent.getStringExtra("str_TimeslotSelection");
        if( str_TimeslotSelection_value != null & str_TimeslotSelection_value != ""){
            this.str_TimeslotSelection = str_TimeslotSelection_value;
//            this.mySharedPreferences.edit().putString("str_TimeslotSelection",str_TimeslotSelection_value).apply();
            txtvw_schedule_selection = findViewById(R.id.schedule_selection);
            txtvw_schedule_selection.setText(str_TimeslotSelection);
            //Toast.makeText(this, "testing - " + str_TimeslotSelection, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void openDialog(){
        TimeslotDialog timeslotDialog = new TimeslotDialog();
        //todo get available timeslot for the day
        timeslotDialog.show(getSupportFragmentManager(),"Timeslot Dialog");
    }

}
