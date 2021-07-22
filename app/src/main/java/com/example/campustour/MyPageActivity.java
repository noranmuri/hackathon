package com.example.campustour;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MyPageActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        Intent main_intent = getIntent();
        String userid = main_intent.getStringExtra("Userid");

        TextView UserIdText = (TextView) findViewById(R.id.UserName);
        UserIdText.setText(userid.toString());







        Button foot_btn = (Button) findViewById(R.id.button_foot);
        foot_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), FootBtnClickedActivity.class);
                intent.putExtra("Userid",userid.toString());
                startActivity(intent);
            }
        });

        Button quest_btn = (Button) findViewById(R.id.button_quest);
        quest_btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), QuestBtnClickedActivity.class);
                intent.putExtra("Userid",userid.toString());
                startActivity(intent);
            }
        });
    }
}