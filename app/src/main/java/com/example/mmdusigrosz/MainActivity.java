package com.example.mmdusigrosz;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Borrower> borrowers = new ArrayList<>();
    ArrayAdapter aa;
    ListView lv;
    private final int LAUNCH_ADD_ACTIVITY = 1;
    private final int LAUNCH_EDIT_ACTIVITY = 2;

    double sum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        lv = findViewById(R.id.main_listview);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(view.getContext(), BorrowerActivity.class);
                startActivityForResult(i, LAUNCH_ADD_ACTIVITY);

            }
        });


        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
                adb.setMessage("Czy na pewno chcesz usunąć dłużnika?");
                adb.setNegativeButton("Anuluj", null);
                adb.setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Borrower martyr = borrowers.get(position);
                        sum-=martyr.getDept();
                        setSumText(sum);
                        aa.remove(martyr);
                        aa.notifyDataSetChanged();

                        Toast toast=Toast.makeText(getApplicationContext(),"Dłużnik usunięty",Toast.LENGTH_SHORT);
                        toast.show();

                    }
                });
                adb.show();

                return true;
            }

        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent(view.getContext(), BorrowerActivity.class);
                Borrower edit_borrower = borrowers.get(position);
                Bundle extras = new Bundle();
                extras.putString("Name", edit_borrower.getName());
                extras.putString("Debt", String.valueOf(edit_borrower.getDept()));
                extras.putInt("Position", position);
                Log.w("Dane dluznika main: ", ""+edit_borrower.getName() + edit_borrower.getDept());
                i.putExtras(extras);
                startActivityForResult(i, LAUNCH_EDIT_ACTIVITY);

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Bundle extras = data.getExtras();

        if (extras != null){
            if (requestCode == LAUNCH_ADD_ACTIVITY){

                String debt = extras.getString("Debt");
                String name = extras.getString("Name");
                double iDebt = Double.parseDouble(debt);
                sum += iDebt;
                setSumText(sum);

                Borrower borrower = new Borrower(name, iDebt);
                borrowers.add(borrower);
                aa = new ArrayAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, borrowers);

                lv.setAdapter(aa);

            }
            if(requestCode == LAUNCH_EDIT_ACTIVITY){

                int position = extras.getInt("Position");
                borrowers.get(position).setName(extras.getString("Name"));
                String debt = extras.getString("Debt");
                double iDebt = Double.parseDouble(debt);
                sum-=iDebt;
                setSumText(sum);
                borrowers.get(position).setDept(iDebt);
                aa.notifyDataSetChanged();

            }
        }
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
            Toast toast=Toast.makeText(getApplicationContext(),"Maciej Mirkiewicz s16426",Toast.LENGTH_SHORT);
            toast.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setSumText(double sum){
        TextView sum_text = findViewById(R.id.sum_id);
        sum = Math.round(sum * 100.0) / 100.0;
        sum_text.setText("SUMA: "+sum);
    }
}
