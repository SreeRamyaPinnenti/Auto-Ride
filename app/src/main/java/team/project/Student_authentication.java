package team.project;
import android.R.*;
import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.renderscript.Sampler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created by $Yaswanth.Pinnenti on 26-09-2018.
 */
public class Student_authentication extends Activity {
    private static final String TAG = "PhoneAuth";
    private EditText regd_no;
    private EditText phoneText;
    private EditText codeText;
    private Button verifyButton;
    private Button sendButton;
    private Button resendButton;
    //private Button signoutButton;
    private TextView statusText;
    public DatabaseReference databaseReference;
    private String phoneVerificationId;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            verificationCallbacks;
    private PhoneAuthProvider.ForceResendingToken resendToken;

    private FirebaseAuth fbAuth;
    ArrayList<String> arrayList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_authentication);
        regd_no = (EditText)findViewById(R.id.regdno);


        phoneText = (EditText) findViewById(R.id.phoneText);
        codeText = (EditText) findViewById(R.id.verifycode);
        verifyButton = (Button) findViewById(R.id.verifyButton);
        sendButton = (Button) findViewById(R.id.sendbutton);
        resendButton = (Button) findViewById(R.id.resendbutton);
        //  signoutButton = (Button) findViewById(R.id.signoutButton);
        // phoneText.setEnabled(false);
        verifyButton.setEnabled(false);
        resendButton.setEnabled(false);
        //signoutButton.setEnabled(false);

        fbAuth = FirebaseAuth.getInstance();
sendButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        sendCode(v);
    }
});

    }
    public void verifyRegd(){
        final String regd = regd_no.getText().toString().toUpperCase();
        databaseReference = FirebaseDatabase.getInstance().getReference("regdno");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String dataValue = (String)dataSnapshot.child("Value").getValue();
                arrayList.add(dataValue);
                for (String str : arrayList) {
                    if(str.equals(regd)){
                        Log.v("nsh", str);

                    }

                }

                Toast.makeText(getApplicationContext(),"Wrong Regd.No",Toast.LENGTH_SHORT).show();

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




    }
    public void sendCode(View view) {
        /*if(regd_no != null) {
            verifyRegd();

        }*/
        String phoneNumber = "+91"+phoneText.getText().toString();

        setUpVerificatonCallbacks();

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60,
                TimeUnit.SECONDS,
                this,
                verificationCallbacks);
    }

    private void setUpVerificatonCallbacks() {

        verificationCallbacks =
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                    @Override
                    public void onVerificationCompleted(
                            PhoneAuthCredential credential) {

                        // signoutButton.setEnabled(true);
                        statusText.setText("Signed In");
                        resendButton.setEnabled(false);
                        verifyButton.setEnabled(false);
                        codeText.setText("");
                        signInWithPhoneAuthCredential(credential);
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {

                        if (e instanceof FirebaseAuthInvalidCredentialsException) {
                            Log.d(TAG, "Invalid credential: "
                                    + e.getLocalizedMessage());
                        } else if (e instanceof FirebaseTooManyRequestsException) {
                            Log.d(TAG, "SMS Quota exceeded.");
                        }
                    }

                    @Override
                    public void onCodeSent(String verificationId,
                                           PhoneAuthProvider.ForceResendingToken token) {

                        phoneVerificationId = verificationId;
                        resendToken = token;

                        verifyButton.setEnabled(true);
                        sendButton.setEnabled(false);
                        resendButton.setEnabled(true);
                    }
                };
    }

    public void verifyCode(View view) {

        String code = codeText.getText().toString();

        PhoneAuthCredential credential =
                PhoneAuthProvider.getCredential(phoneVerificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        fbAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // signoutButton.setEnabled(true);
                            codeText.setText("");
                            statusText.setText("Signed In");

                            resendButton.setEnabled(false);
                            verifyButton.setEnabled(false);
                            FirebaseUser user = task.getResult().getUser();
                            Intent intent = new Intent(Student_authentication.this,Add_point.class);
                            startActivity(intent);
                            finish();
                            //  startActivity(intent);

                        } else {
                            if (task.getException() instanceof
                                    FirebaseAuthInvalidCredentialsException) {

                            }
                        }
                    }
                });
    }

    public void resendCode(View view) {

        String phoneNumber = "+91" +phoneText.getText().toString();

        setUpVerificatonCallbacks();

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60,
                TimeUnit.SECONDS,
                this,
                verificationCallbacks,
                resendToken);
    }

    public void signOut(View view) {
        fbAuth.signOut();
        statusText.setText("Signed Out");
        //signoutButton.setEnabled(false);
        sendButton.setEnabled(true);
    }
}
