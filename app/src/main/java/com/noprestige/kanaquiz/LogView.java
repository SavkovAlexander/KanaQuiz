package com.noprestige.kanaquiz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

public class LogView extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_view);

        LogDailyRecord[] records = LogDatabase.DAO.getAllDailyRecords();

        LinearLayout layout = findViewById(R.id.log_view_layout);

        for (LogDailyRecord record : records)
        {
            DailyLogItem output = new DailyLogItem(getBaseContext());
            output.setFromRecord(record);
            layout.addView(output);
        }
    }
}
