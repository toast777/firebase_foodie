package com.chuck.android.firebase_foodie;

import android.content.Context;
import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chuck.android.firebase_foodie.models.FoodItem;
import com.chuck.android.firebase_foodie.models.Topping;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity" ;
    List<FoodItem> mFoodItems = new ArrayList<>();
    private FirebaseAuth mAuth;
    private TextView userName;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        DatabaseReference userRef = database.getReference("users");
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        currentUser.getUid();





        final TextView foodName = findViewById(R.id.foodName);
        final TextView foodPrice = findViewById(R.id.foodPrice);
        final TextView foodCustomPrice = findViewById(R.id.foodCustomPrice);
        final Button loginButton = findViewById(R.id.login_button);
        userName = findViewById(R.id.current_user);

        updateUI(currentUser);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), FirebaseSignIn.class);
                startActivity(intent);
            }
        });

//        this.name = name;
//        this.imgSrc = imgSrc;
//        this.price = price;
//        this.allowAddOns = allowAddOns;
//        this.numAddOns = numAddOns;


        //Add Restaurant as Child
//        FoodItem testItem = new FoodItem("Medium Peperoni Pizza","",10.00,false,0);
//        FoodItem testItem1 = new FoodItem("Medium Cheese Pizza","",9.00,false,0);
//        FoodItem testItem2 = new FoodItem("Medium Base Pizza","",9.00,true,0);
//        FoodItem testItem3 = new FoodItem("Large 2-topping Pizza","",10.00,false,2);
//
//        DatabaseReference kcfood = myRef.child("Overland Park").child("Pizza Hut").child("food items");
//
//        kcfood.push().setValue(testItem);
//        kcfood.push().setValue(testItem1);
//        kcfood.push().setValue(testItem2);
//        kcfood.push().setValue(testItem3);
//
//
//        Topping testTopping = new Topping("Pepperoni","",1.00);
//            Topping testTopping1 = new Topping("Sausage","",1.00);
//            Topping testTopping2 = new Topping("Ham","",1.00);
//            Topping testTopping3 = new Topping("Mushrooms","",1.00);
//
//            DatabaseReference kctop = myRef.child("Overland Park").child("Pizza Hut").child("toppings");
//        //String RestAddress = "1911 McGee St, Kansas City, MO 64108";
//            kctop.push().setValue(testTopping);
//            kctop.push().setValue(testTopping1);
//            kctop.push().setValue(testTopping2);
//            kctop.push().setValue(testTopping3);

//        DatabaseReference kcrest = myRef.child("Overland Park").child("Pizza Hut").child("locations");
//        String RestAddress = "8101 151st St, Overland Park, KS 66223";
//        String RestAddress2 = "8319 W 135th St, Overland Park, KS 66223";
//        kcrest.push().setValue(RestAddress);
//        kcrest.push().setValue(RestAddress2);
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String strDate = dateFormat.format(date);


        DatabaseReference kcrest = myRef.child("Regions");
        String RestAddress = "Kansas City";
        String RestAddress2 = "Overland Park";
        kcrest.child("Kansas City").setValue(strDate);
        kcrest.child("Overland Park").setValue(strDate);





        //myRef.child("Lees Summit").child( "foodItem").child(testItem2.getId()).setValue(testItem2);


        //myRef.setValue("Hello, World!");

        // Read from the database
        myRef.child("foodItem").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    FoodItem food = snapshot.getValue(FoodItem.class);
                    foodName.append(food.getName() + "\n");
                    foodPrice.append(Double.toString(food.getPrice()) + "\n");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "loadPost:onCancelled", error.toException());
            }
        });
    }
    private void updateUI(FirebaseUser user) {
        if (user != null) {
            userName.setText(user.getEmail());
            userName.append(user.getUid());

        } else {
            userName.setText("No Email");
        }
    }
}
