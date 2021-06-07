package com.example.postpc_ex7;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class readyOrderActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ready_order);

        Button button = findViewById(R.id.readyGotItButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SandwichOrderApplication.getInstance().getSandwichOrder().setStatus(SandwichOrder.DONE);
                SandwichOrderApplication.getInstance().getSandwichOrder().updateDB();
            }
        });
    }
}
