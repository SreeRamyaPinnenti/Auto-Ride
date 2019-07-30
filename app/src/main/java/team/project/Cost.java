package team.project;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * Created by $Yaswanth.Pinnenti on 25-09-2018.
 */
public class Cost extends  Activity {
    Spinner from,to ;
    TextView cost;
    Button proceed;
    ArrayList<String> list= new ArrayList<>();
    ArrayAdapter<String> adapter;
    public DatabaseReference databaseReference;
    String From1="",To1="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cost);
        cost = (TextView) findViewById(R.id.cost1);
        from = (Spinner) findViewById(R.id.fromspinner);
        to = (Spinner) findViewById(R.id.tospinner);
        proceed = (Button) findViewById(R.id.proceed);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_single_choice,list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        from.setAdapter(adapter);
        //update_point_text=(EditText)findViewById(R.id.update);
        to.setAdapter(adapter);



        from.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                From1=list.get(position);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        to.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                To1=list.get(position);
                if(From1.equalsIgnoreCase(To1)){
                        Toast.makeText(Cost.this,"both source and destination are same",Toast.LENGTH_LONG).show();
                }
                else if(From1.equalsIgnoreCase("svecw")|| To1.equalsIgnoreCase("svecw")){
                    String chk;
                    if(From1.equalsIgnoreCase("svecw")){
                        chk=To1;
                    }
                    else{
                        chk=From1;
                    }
                    databaseReference.child("point").orderByChild("name").equalTo(chk).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                                String str2=snapshot.child("phase1").getValue(String.class);
                                str2="RS :"+str2;
                                cost.setText(str2);
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                else{

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        databaseReference.child("point").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                String value = dataSnapshot.child("name").getValue(String.class);
                Log.v("hai", "*************");
                System.out.println(value);
                if (value != null && !value.isEmpty()) {
                    list.add(value);
                }
                System.out.println("***" + list + "*****");
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String value = dataSnapshot.child("name").getValue(String.class);
                String key = dataSnapshot.child("value").getKey();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.child("name").getValue(String.class);
                list.remove(value);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.print("*************"+From1);
                if(From1.isEmpty() || From1.length()==0){
                    System.out.print("*************"+From1);
                    Toast.makeText(Cost.this,"Error",Toast.LENGTH_LONG).show();
                }
                else if(From1.equals(To1)){
                    Toast.makeText(Cost.this,"soucre and destinations",Toast.LENGTH_LONG).show();
                }
                else if(From1.equalsIgnoreCase("svecw")|| To1.equalsIgnoreCase("svecw")){
                    Toast.makeText(Cost.this,"Ride Starts now",Toast.LENGTH_LONG).show();

                }
                else{
                    Toast.makeText(Cost.this,"source or destination must be SVECW",Toast.LENGTH_LONG).show();
                }
            }

        });

    }


}


