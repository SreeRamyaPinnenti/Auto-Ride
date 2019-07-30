package team.project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by $Yaswanth.Pinnenti on 25-09-2018.
 */
public class Add_point extends Activity {
    Button addpoint,add_auto,available_autos,booked_auto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_point);
        addpoint=(Button)findViewById(R.id.addpoint);
        add_auto=(Button)findViewById(R.id.add_auto);
        available_autos=(Button)findViewById(R.id.available_auto);
        booked_auto = (Button)findViewById(R.id.booked_auto);
        addpoint.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent =new Intent(Add_point.this,Add_point1.class);
                                            startActivity(intent);
                                        }
                                    }
        );
        add_auto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Add_point.this,Auto_registration.class);
                startActivity(intent);
            }
        });
        available_autos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Add_point.this,Available_Auto.class);
                startActivity(intent);
            }
        });
        booked_auto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Add_point.this,Booked_Autos.class);
                startActivity(intent);
            }
        });

    }
}


