package com.example.taskactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {
ListView list;
SharedPreferences pref;
ArrayList<String> arr,desc;
TextView numoftasks,selectviewtask;
DatabaseReference ref;
String nexttask;
    ArrayAdapter<String> arrayAdapter;
    long mintimeleft;
    TextView upcomingtask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list=findViewById(R.id.listviewid);

        arr=new ArrayList<>();
        desc=new ArrayList<>();

        arr.clear();
        desc.clear();

        numoftasks=findViewById(R.id.taskscount);
        selectviewtask=findViewById(R.id.textView3);
        upcomingtask=findViewById(R.id.upcomingid);

        mintimeleft=Long.MAX_VALUE;
        ref=FirebaseDatabase.getInstance().getReference("TASKS");
        ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Iterator<DataSnapshot> itr = snapshot.getChildren().iterator();
                    numoftasks.setText(String.valueOf(snapshot.getChildrenCount()));
                    while (itr.hasNext()) {
                        DataSnapshot data = itr.next();
                           if(!arr.contains(data.getKey().toString())) {
                               //  a check for duplicates
                               arr.add(data.getKey().toString());
                               desc.add(data.child("desc").getValue().toString());
                               if(mintimeleft>Long.parseLong(data.child("time").getValue().toString())) {
                                    mintimeleft=Long.parseLong(data.child("time").getValue().toString());
                                      nexttask=data.getKey().toString();
                               }
                           }

                    }

                    arrayAdapter.notifyDataSetChanged();
                    upcomingtask.setText(nexttask);
                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


           arrayAdapter= new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arr);
            list.setAdapter(arrayAdapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if(position<arr.size()) {
                         new AlertDialog.Builder(MainActivity.this).setIcon(R.drawable.ic_baseline_work_24).setTitle(arr.get(position)).
                                 setMessage(desc.get(position)).show();
                    }

                }
            });

    }
    public void addtask(View view) {
      Intent i=new Intent(MainActivity.this,Taskactivity.class);
      startActivity(i);
    }
}