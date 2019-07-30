package team.project;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by $Yaswanth.Pinnenti on 01-12-2018.
 */
public class Auto_student_details extends Activity {
    DatabaseReference databaseReference,databaseReference1;
    private static final int REQUEST_CALL = 1;
    // TextView phonenumber;
    TextView auto_no,name,mobile;
    Auto_registration1 auto_registration2;
    ImageView call;
    String str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auto_details_student);
        auto_no=(TextView)findViewById(R.id.autono);
        mobile=(TextView)findViewById(R.id.mobile_no);
        name=(TextView)findViewById(R.id.avl_name);
        databaseReference1= FirebaseDatabase.getInstance().getReference("booked_autos");
        databaseReference= FirebaseDatabase.getInstance().getReference();
        str=getIntent().getExtras().getString("autoid");
        call = (ImageView) findViewById(R.id.callbuttton);

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phonecall();
                open(v);
            }
        });

        databaseReference.child("").orderByChild("auto_no").equalTo(str).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                auto_registration2 = dataSnapshot.getValue(Auto_registration1.class);
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    String s= snapshot.child("auto_no").getValue(String.class);
                    System.out.println("*********************");
                    String lic=snapshot.child("licence").getValue(String.class);
                    System.out.println("####"+s+"Welcome"+"##########");
                    auto_no.setText(s);
                    //System.out.println("####"+s+"Welcome"+"##########");
                    String n= snapshot.child("name").getValue(String.class);
                    //System.out.println("####"+s+"Welcome"+"##########");
                    name.setText(s);
                    String m= snapshot.child("mobile_no").getValue(String.class);
                    //System.out.println("####"+s+"Welcome"+"##########");
                    mobile.setText(s);
                    String ad=snapshot.child("adhar_no").getValue(String.class);
                    /*String auto_no, String licence, String name, String mobile_no, String adhar_no*/
                    add(s,lic,n,m,ad);
                     snapshot.getRef().removeValue();
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public void add(String s, String lic,String n,String m,String ad){
        System.out.println("add");
        Auto_registration1 auto_registration1=new Auto_registration1(s,lic,n,m,ad);
        databaseReference1.child(s).setValue(auto_registration1);
    }
    public void open(View view){

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure,You wanted to make decision");
        alertDialogBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Toast.makeText(Auto_student_details.this, "booking competed", Toast.LENGTH_LONG).show();
                        databaseReference.child("auto").orderByChild("auto_no").equalTo(str).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Auto_registration1 auto_registration1 = dataSnapshot.getValue(Auto_registration1.class);
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    String s = snapshot.child("auto_no").getValue(String.class);
                                    System.out.println("******auto registration  completed**********"+s);
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                        System.out.println("******auto registration  completed1**********");

                    }
                });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    private void phonecall() {
        String number = mobile.getText().toString();
        if(number.trim().length()>0) {
            if(ContextCompat.checkSelfPermission(Auto_student_details.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(Auto_student_details.this,new String[]{android.Manifest.permission.CALL_PHONE},REQUEST_CALL);
            } else {
                String dail =  "tel:" +number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dail)));
            }

        }else {
            Toast.makeText(this,"Enter phone number",Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(requestCode == REQUEST_CALL) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                phonecall();
            }else {
                Toast.makeText(this,"PERMISSION DENIED",Toast.LENGTH_LONG).show();

            }
        }
    }


}


