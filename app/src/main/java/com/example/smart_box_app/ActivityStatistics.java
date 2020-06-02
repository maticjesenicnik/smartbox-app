package com.example.smart_box_app;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ActivityStatistics extends AppCompatActivity {
    RecyclerView recyclerView;
    MyApplication myApplication;
    AdapterStatistics adapterStatistics;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_statistics);
        recyclerView = findViewById(R.id.statisticsList);
        myApplication = (MyApplication) getApplication();
        adapterStatistics = new AdapterStatistics(myApplication);

        recyclerView.setAdapter(adapterStatistics);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
