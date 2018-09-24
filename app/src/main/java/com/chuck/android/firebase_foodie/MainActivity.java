package com.chuck.android.firebase_foodie;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chuck.android.firebase_foodie.models.FoodItem;
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
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity" ;
    List<FoodItem> mFoodItems = new ArrayList<>();
    private FirebaseAuth mAuth;
    private TextView userName;


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

        final FoodItem testItem,testItem2;




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
        testItem = new FoodItem("Peperoni Pizza","",60.00,true,0);
        DatabaseReference kc = myRef.child("Kansas City").child("Papa Johns").child("foodItem");

        kc.push().setValue(testItem);
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
