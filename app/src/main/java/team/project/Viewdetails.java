package team.project;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by $Yaswanth.Pinnenti on 25-09-2018.
 */
public class Viewdetails extends Activity{
    TextView autoid, autoname, licence, adhaar, mobile;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewdetails);
        autoid = (TextView) findViewById(R.id.autoid);
        autoname = (TextView) findViewById(R.id.autoname);
        licence = (TextView) findViewById(R.id.licence);
        adhaar = (TextView) findViewById(R.id.adhaarnumber);
        mobile = (TextView) findViewById(R.id.mobile);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        String str=getIntent().getExtras().getString("autoid");
        databaseReference.child("auto").orderByChild("auto_no").equalTo(str).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Auto_registration1 auto_registration1 = dataSnapshot.getValue(Auto_registration1.class);
                System.out.println("**********");
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    //System.out.println("####"+str2+"Welcome"+"##########");
                    String s= snapshot.child("auto_no").getValue(String.class);
                    System.out.println("####"+s+"Welcome"+"##########");
                    licence.setText(s);
                    s= snapshot.child("licence").getValue(String.class);
                    autoid.setText(s);
                    System.out.println("####"+s+"Welcome"+"##########");
                    s= snapshot.child("name").getValue(String.class);
                    System.out.println("####"+s+"Welcome"+"##########");
                    autoname.setText(s);
                    s= snapshot.child("mobile_no").getValue(String.class);
                    System.out.println("####"+s+"Welcome"+"##########");
                    //  s=snapshot.child("mobile_").getValue();
                    System.out.println("####"+s+"Welcome"+"##########");
                    mobile.setText(s);
                    s= snapshot.child("adhar_no").getValue(String.class);
                    System.out.println("####"+s+"Welcome"+"##########");
                    adhaar.setText(s);

                }
                //licence.setText(auto_registration1.licence);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
