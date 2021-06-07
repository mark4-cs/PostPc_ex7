package com.example.postpc_ex7;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class newOrderActivity extends AppCompatActivity {
    SandwichOrderApplication sandwichOrderApplication = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order);
        sandwichOrderApplication = SandwichOrderApplication.getInstance();

        FloatingActionButton setNewOrderButton = findViewById(R.id.setNewOrderButton);
        CheckBox checkBoxHummus = findViewById(R.id.checkBoxHummus);
        CheckBox checkBoxTahini = findViewById(R.id.checkBoxTahini);
        EditText editTextInsertcomment = findViewById(R.id.editTextInsertcomment);
        SeekBar picklesBar = findViewById(R.id.picklesBar);
        TextView amountOfPicklesTitle = findViewById(R.id.amountOfPicklesTitle);
        TextView editTextInsertCustomerName = findViewById(R.id.editTextInsertCustomerName);


        setNewOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SandwichOrder s = new SandwichOrder(editTextInsertCustomerName.getText().toString(),
                        Integer.parseInt(amountOfPicklesTitle.getText().toString().split(" ")[2]),
                        checkBoxHummus.isChecked(), checkBoxTahini.isChecked(), editTextInsertcomment.getText().toString(), SandwichOrder.WAITING);
                s.updateDB();
                sandwichOrderApplication.setSandwichOrder(s);
            }
        });
        picklesBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (i == 0){amountOfPicklesTitle.setText("Pickles amount: " + i + "  No pickles for me!");}
                else if (i == 10){amountOfPicklesTitle.setText("Pickles amount: " + i + "  Give me all the pickles!");}
                else {amountOfPicklesTitle.setText("Pickles amount: " + i);}}
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }
}