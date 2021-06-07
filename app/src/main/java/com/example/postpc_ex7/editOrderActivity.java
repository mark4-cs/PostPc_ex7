package com.example.postpc_ex7;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class editOrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_order);
        SandwichOrderApplication sandwichOrderApplication = SandwichOrderApplication.getInstance();
        SandwichOrder sandwichOrder = sandwichOrderApplication.getSandwichOrder();

        FloatingActionButton editOrderButton = findViewById(R.id.edit_setNewOrderButton);
        CheckBox checkBoxHummus = findViewById(R.id.edit_checkBoxHummus);
        checkBoxHummus.setChecked(sandwichOrder.isHummus());
        CheckBox checkBoxTahini = findViewById(R.id.edit_checkBoxTahini);
        checkBoxTahini.setChecked(sandwichOrder.isTahini());
        EditText editTextInsertcomment = findViewById(R.id.edit_editTextInsertcomment);
        editTextInsertcomment.setText(sandwichOrder.getComment());
        SeekBar picklesBar = findViewById(R.id.edit_picklesBar);
        picklesBar.setProgress(sandwichOrder.getPickles());
        TextView amountOfPicklesTitle = findViewById(R.id.edit_amountOfPicklesTitle);
        setPicklesEditMsg(sandwichOrder.getPickles());
        TextView editTextInsertCustomerName = findViewById(R.id.edit_editTextInsertCustomerName);
        editTextInsertCustomerName.setText(sandwichOrder.getCustomerName());
        Button edit_deleteOrderButton = findViewById(R.id.edit_deleteOrderButton);

        editOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sandwichOrderApplication.getSandwichOrder().setComment(editTextInsertcomment.getText().toString());
                sandwichOrderApplication.getSandwichOrder().setCustomerName(editTextInsertCustomerName.getText().toString());
                sandwichOrderApplication.getSandwichOrder().setHummus(checkBoxHummus.isChecked());
                sandwichOrderApplication.getSandwichOrder().setTahini(checkBoxTahini.isChecked());
                sandwichOrderApplication.getSandwichOrder().setPickles(Integer.parseInt(amountOfPicklesTitle.getText().toString().split(" ")[2]));
                sandwichOrderApplication.getSandwichOrder().updateDB();
            }
        });

        edit_deleteOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SandwichOrderApplication.getInstance().deleteSandwichOrder();
            }
        });

        picklesBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {setPicklesEditMsg(i);}
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    private void setPicklesEditMsg(int i){
        TextView amountOfPicklesTitle = findViewById(R.id.edit_amountOfPicklesTitle);
        if (i == 0){amountOfPicklesTitle.setText("Pickles amount: " + i + "  No pickles for me!");}
        else if (i == 10){amountOfPicklesTitle.setText("Pickles amount: " + i + "  Give me all the pickles!");}
        else {amountOfPicklesTitle.setText("Pickles amount: " + i);}
    }

}
