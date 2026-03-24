package com.example.mobile_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static List<String> taskList = new ArrayList<>();
    EditText etTask;
    Button btnAdd, btnView;
    TextView tvCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etTask = findViewById(R.id.etTask);
        btnAdd = findViewById(R.id.btnAdd);
        btnView = findViewById(R.id.btnView);

        // Add a count label dynamically if you want, or keep it simple
        // For now, we'll just update the View button text with count
        updateViewButtonText();

        btnAdd.setOnClickListener(v -> {
            String task = etTask.getText().toString();
            if (!task.isEmpty()) {
                taskList.add(task);
                Toast.makeText(MainActivity.this, "Task Added!", Toast.LENGTH_SHORT).show();
                etTask.setText("");
                updateViewButtonText();
            } else {
                Toast.makeText(MainActivity.this, "Please enter a task", Toast.LENGTH_SHORT).show();
            }
        });

        btnView.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateViewButtonText();
    }

    private void updateViewButtonText() {
        btnView.setText("View List (" + taskList.size() + ")");
    }
}