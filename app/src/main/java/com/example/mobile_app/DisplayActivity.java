package com.example.mobile_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class DisplayActivity extends AppCompatActivity {

    ListView listView;
    TextView tvCount;
    Button btnClearAll;
    List<String> tasks;
    TaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        listView = findViewById(R.id.taskListView);
        tvCount = findViewById(R.id.tvTaskCount);
        btnClearAll = findViewById(R.id.btnClearAll);

        tasks = MainActivity.taskList;
        adapter = new TaskAdapter();
        listView.setAdapter(adapter);

        updateCount();

        btnClearAll.setOnClickListener(v -> {
            tasks.clear();
            adapter.notifyDataSetChanged();
            updateCount();
            Toast.makeText(this, "All tasks cleared!", Toast.LENGTH_SHORT).show();
        });
    }

    private void updateCount() {
        tvCount.setText("Total Tasks: " + tasks.size());
    }

    // Custom Adapter Class
    class TaskAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return tasks.size();
        }

        @Override
        public Object getItem(int position) {
            return tasks.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(DisplayActivity.this)
                        .inflate(R.layout.list_item, parent, false);
            }

            TextView taskText = convertView.findViewById(R.id.taskText);
            CheckBox checkBox = convertView.findViewById(R.id.checkBox);
            Button btnDelete = convertView.findViewById(R.id.btnDelete);

            String task = tasks.get(position);
            taskText.setText(task);

            // CheckBox Logic (Strike through text)
            checkBox.setOnCheckedChangeListener(null); // Prevent recycling issues
            checkBox.setChecked(false); // Reset state
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    taskText.setPaintFlags(taskText.getPaintFlags() | android.graphics.Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    taskText.setPaintFlags(taskText.getPaintFlags() & (~android.graphics.Paint.STRIKE_THRU_TEXT_FLAG));
                }
            });

            // Delete Button Logic
            btnDelete.setOnClickListener(v -> {
                tasks.remove(position);
                notifyDataSetChanged();
                updateCount();
                Toast.makeText(DisplayActivity.this, "Task deleted", Toast.LENGTH_SHORT).show();
            });

            return convertView;
        }
    }
}