package com.example.mmdusigrosz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class SimulatorActivity extends AppCompatActivity {

    boolean isRun = false;
    double commission;
    double speed;
    double simval;
    TimerTask timerTask;
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulator);

        final TextView borrowerinfo = findViewById(R.id.borrower_info);
        final TextView borrowerdebt = findViewById(R.id.borrower_debt);


        Button sim_button = findViewById(R.id.simulate_button);
        final Button pause_button = findViewById(R.id.pause_button);
        Bundle extras = getIntent().getExtras();
        final EditText speed_et = findViewById(R.id.speed_field);
        final EditText commission_et = findViewById(R.id.comm_field);
        final TextView simtext = findViewById(R.id.sim_textid); // "wyswietlacz"
        final double debt = extras.getDouble("Debt");

        borrowerinfo.setText("Symulacja dłużnika o imieniu:\n"+ extras.getString("Name"));
        borrowerdebt.setText("Dług: "+debt);


        simval = debt;

        sim_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                commission = Double.parseDouble(commission_et.getText().toString()); //prowizja
                speed = Double.parseDouble(speed_et.getText().toString()); //predkosc splaty dlugu

                borrowerinfo.onEditorAction(EditorInfo.IME_ACTION_DONE);
                borrowerdebt.onEditorAction(EditorInfo.IME_ACTION_DONE);

                if (!isRun) {
                    timer = new Timer();
                    timerTask = new TimerTask() {

                        @Override
                        public void run() {

                            isRun = true;
                            simval -= speed;
                            double tmp = simval * (commission/100) ;
                            simval -= tmp;

                            if(simval > 0) {
                                simtext.setText(String.format("%.2f", simval) + " zł");

                            } else {
                                simtext.setText("Dług spłacony!");
                            }
                        }
                    };
                    timer.scheduleAtFixedRate(timerTask,0,1000);
                }else
                    Toast.makeText(getApplicationContext(),"Symulacja już działa", Toast.LENGTH_SHORT).show();

            }
        });
        pause_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isRun){
                    timerTask.cancel();
                    timer.cancel();
                    timer.purge(); // Removes all cancelled tasks from this timer's task queue.
                    isRun = false;
                    Toast.makeText(getApplicationContext(),"Symulacja zatrzymana", Toast.LENGTH_SHORT).show();
                    simtext.setText("Symualacja zatrzymana");

                }else
                    Toast.makeText(getApplicationContext(),"Brak uruchomionej symulacji", Toast.LENGTH_SHORT).show();
            }
        });

    }
    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }
}
