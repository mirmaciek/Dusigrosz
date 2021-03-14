package com.example.mmdusigrosz;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class BorrowerActivity extends AppCompatActivity {

    EditText nameedit, debtedit;
    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrower);

        nameedit = findViewById(R.id.name_field);
        debtedit = findViewById(R.id.debt_field);
        Button cancel_button = findViewById(R.id.borrower_cancel_button);

        extras = getIntent().getExtras();

        if (extras != null) {
            String ex_name = extras.getString("Name");
            String ex_debt = extras.getString("Debt");
            nameedit.setText(ex_name);
            debtedit.setText(ex_debt);

        }



        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelActivity();
            }

        });

        Button save_button = findViewById(R.id.borrower_save_button);
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sName = nameedit.getText().toString().trim();
                String sDebt = debtedit.getText().toString().trim();

                if (sName.matches("") || sDebt.matches("")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Upewnij się, czy dane są prawidłowe", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                } else {
                    Intent returnIntent = new Intent();
                    Bundle tmp_extras = new Bundle();
                    tmp_extras.putString("Name", sName);
                    tmp_extras.putString("Debt", sDebt);

                    if (extras != null){
                        tmp_extras.putInt("Position", extras.getInt("Position"));

                        returnIntent.putExtras(tmp_extras);
                        setResult(2, returnIntent);
                    }else {
                        returnIntent.putExtras(tmp_extras);
                        setResult(1, returnIntent);
                    }
                    finish();


                }

            }
        });
        Button simulate_button = findViewById(R.id.simulate_button);
        simulate_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                String sName = nameedit.getText().toString().trim();
                String sDebt = debtedit.getText().toString().trim();
                if (sName.matches("") || sDebt.matches("")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Brak danych do symulacji!", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                } else {

                    Intent intent = new Intent(v.getContext(), SimulatorActivity.class);
                    Bundle extras = new Bundle();
                    extras.putString("Name", sName);
                    double iDebt = Double.parseDouble(sDebt);
                    extras.putDouble("Debt", iDebt);
                    intent.putExtras(extras);
                    startActivity(intent);
                    finish();

                }
            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Toast toast = Toast.makeText(getApplicationContext(), "Maciej Mirkiewicz s16426", Toast.LENGTH_SHORT);
            toast.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        cancelActivity();
    }

    private void cancelActivity() {

        String sName = nameedit.getText().toString().trim();
        String sDebt = debtedit.getText().toString().trim();

        if (!sName.matches("") || !sDebt.matches("")) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(BorrowerActivity.this);
            builder.setMessage("Na pewno anulować wprowadzone dane?");
            builder.setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent returnIntent = new Intent();
                    setResult(Activity.RESULT_CANCELED, returnIntent);
                    finish();
                }
            });
            builder.setNegativeButton("Nie", null);
            AlertDialog dialog = builder.create();
            dialog.show();
        } else{
            Intent returnIntent = new Intent();
            setResult(Activity.RESULT_CANCELED, returnIntent);
            finish();
        }

    }
}
