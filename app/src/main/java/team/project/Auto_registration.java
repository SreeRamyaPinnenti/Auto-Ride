package team.project;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;



/**
 * Created by $Yaswanth.Pinnenti on 25-09-2018.
 */
public class Auto_registration extends  Activity{
    EditText autono,licence,name,mobile,adhaar;
    Button register;
    ArrayList<String> autolist = new ArrayList<>();
    ArrayAdapter<String> adapter;
    DatabaseReference databaseReference;
    ListView autolist_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auto_registration);
        autono=(EditText)findViewById(R.id.auto_no);
        licence = (EditText)findViewById(R.id.licence);
        name = (EditText)findViewById(R.id.name);
        mobile = (EditText)findViewById(R.id.mobile);
        adhaar = (EditText)findViewById(R.id.adhaar);
        autolist_view=(ListView)findViewById(R.id.auto_list);
        register = (Button)findViewById(R.id.auto_register);
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_single_choice,autolist);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        autolist_view.setAdapter(adapter);
        autolist_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Auto_registration.this,Viewdetails.class);
                intent.putExtra("autoid",autolist.get(position));
                startActivity(intent);
            }
        });
        databaseReference= FirebaseDatabase.getInstance().getReference();

        databaseReference.child("auto").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Auto_registration1 value = dataSnapshot.getValue(Auto_registration1.class);
                autolist.add(value.auto_no);
                //System.out.print("****");
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_auto();
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void add_auto() {
        String str1 = autono.getText().toString().trim();
        String str2=licence.getText().toString().trim();
        String str3=name.getText().toString().trim();
        String str4=mobile.getText().toString().trim();
        String str5=adhaar.getText().toString().trim();

        if (!TextUtils.isEmpty(str1)) {

            //String id = databaseReference.child("auto").push().getKey();
            //String id1 = databaseReference.child("Available_autos").push().getKey();
            Auto_registration1 auto_registration1 = new Auto_registration1(str1,str2,str3,str4,str5);
            databaseReference.child("auto").child(str1).setValue(auto_registration1);
            databaseReference.child("Available_autos").child(str1).setValue(auto_registration1);
            autono.setText("");

            Toast.makeText(this, "Auto added Sucessfully", Toast.LENGTH_LONG).show();


        } else {
            Toast.makeText(this, "failed to add auto", Toast.LENGTH_LONG).show();
        }
    }

}


