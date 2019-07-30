package team.project;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import java.util.ArrayList;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by $Yaswanth.Pinnenti on 25-09-2018.
 */

    public class Add_point1 extends Activity {
        EditText add_point_text,update_point_text;
        Button addpoint1,updatepoint,deletepoint,clear;


       Spinner spinner;
       DatabaseReference databaseReference;
        ArrayList<String> list = new ArrayList<>();
        ArrayAdapter<String> adapter;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.add_point1);
            databaseReference = FirebaseDatabase.getInstance().getReference("point");
            spinner=(Spinner)findViewById(R.id.spinner);
            add_point_text = (EditText) findViewById(R.id.add_point_text);
            addpoint1 = (Button) findViewById(R.id.addpoint1);
            clear=(Button)findViewById(R.id.claerbtn);

            updatepoint=(Button)findViewById(R.id.updatepoint_btn);
            deletepoint=(Button)findViewById(R.id.deletepoint_btn);
            adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_single_choice,list);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            update_point_text=(EditText)findViewById(R.id.update);



            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    add_point_text.setText(list.get(position));


                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            databaseReference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    String value = dataSnapshot.child("name").getValue(String.class);
                    Log.v("hai","*************");
                    //String keys=dataSnapshot.child("name").getKey();
                    //keys1.add(keys);
                    System.out.println(value);
                    if (value != null && !value.isEmpty()) {
                        list.add(value);
                    }

                    System.out.println("***" + list + "*****");



                    adapter.notifyDataSetChanged();
                }
                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    String  value = dataSnapshot.child("name").getValue(String.class);
                    String key =dataSnapshot.child("value").getKey();
                    //int index = keys1.indexOf(key);
                    //list.set(index,value);


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
            clear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clearpoint();
                }
            });
            deletepoint.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    delete();
                }
            });
            updatepoint.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    update();
                }
            });
            addpoint1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    adddata();
                    //Toast.makeText(this, "pointer added", Toast.LENGTH_LONG).show();
                    Log.v("*****","before get data");


                }
            });
        }
        public void clearpoint(){

        }

        public void adddata() {
            String str1 = add_point_text.getText().toString().trim();
            if (!TextUtils.isEmpty(str1)) {

                String id = databaseReference.push().getKey();
                Add_Route add_route = new Add_Route(str1);
                databaseReference.child("point").child(id).setValue(add_route);
                add_point_text.setText("");
                Toast.makeText(this, "pointer added", Toast.LENGTH_LONG).show();


            } else {
                Toast.makeText(this, "pointer not added", Toast.LENGTH_LONG).show();
            }
        }
        public void delete(){
            final String str1 = add_point_text.getText().toString();
            int pos=spinner.getSelectedItemPosition();
            databaseReference.orderByChild("name").equalTo(str1).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        //System.out.println("####"+str2+"Welcome"+"##########");
                        snapshot.getRef().child("name").removeValue();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            if(pos>-1){
                adapter.remove(list.get(pos));
                adapter.notifyDataSetChanged();
                Toast.makeText(this, "pointer deleted", Toast.LENGTH_LONG).show();
            }
        }

        public  void update() {
            final String str1 = add_point_text.getText().toString();
            final String str2 = update_point_text.getText().toString();
            int pos=spinner.getSelectedItemPosition();
            databaseReference.orderByChild("name").equalTo(str1).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        System.out.println("####"+str2+"Welcome"+"##########");
                        snapshot.getRef().child("name").setValue(str2);
                        }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


            if(!str1.isEmpty() && str1.length()>0 && !str2.isEmpty()&&str2.length()>0){
                //databaseReference.child("names").child(str1).setValue(str2);
                adapter.remove(list.get(pos));
                adapter.insert(str2,pos);
                adapter.notifyDataSetChanged();
                Toast.makeText(this, "point updated", Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(this, "update column is empty", Toast.LENGTH_LONG).show();
            }

        }



   /*public void getdata(ArrayList<String> list) {



       databaseReference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               final List<String> array = new ArrayList<String>();
                   Log.v("on","data");

               for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                   String areaName = dataSnapshot1.child("point").getValue(String.class);
                   array.add(areaName);
                   Log.v("array:","array");
               }

               final Spinner spinner=(Spinner) findViewById(R.id.spinner);

               ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(Addd_Point1.this, android.R.layout.simple_spinner_item, array);
               dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
               spinner.setAdapter(dataAdapter);
              spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                   @Override
                   public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                   }

                   @Override
                   public void onNothingSelected(AdapterView<?> parent) {

                   }
               });
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }

       });


   }*/
    }


