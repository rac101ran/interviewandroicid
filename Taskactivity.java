package com.example.taskactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Taskactivity extends AppCompatActivity {
   Button bsend;
   EditText heading,descp;
   static ArrayList<String> ar;
   Map<String,Object> tasks;
   DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taskactivity);
        bsend=findViewById(R.id.sendtask);
        heading=findViewById(R.id.headid);
        descp=findViewById(R.id.descid);
        tasks=new HashMap<>();
        ref= FirebaseDatabase.getInstance().getReference("TASKS");
        bsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 if(!TextUtils.isEmpty(heading.getText().toString()) && !TextUtils.isEmpty(descp.getText().toString())) {
                     tasks.put(heading.getText().toString(),descp.getText().toString());
                     ref.updateChildren(tasks).addOnCompleteListener(new OnCompleteListener<Void>() {
                         @Override
                         public void onComplete(@NonNull Task<Void> task) {
                             if(task.isSuccessful()) {
                                 Toast.makeText(Taskactivity.this, "Task added.", Toast.LENGTH_SHORT).show();
                             }else {
                                 Toast.makeText(Taskactivity.this,"Failed", Toast.LENGTH_SHORT).show();
                             }
                         }
                     });
                 }
            }
        });
    }

}