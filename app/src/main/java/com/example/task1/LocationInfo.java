package com.example.task1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.CandleStickChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LocationInfo extends AppCompatActivity {

    TextView tvNumber, tvDetails, tvName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_info);


        CandleStickChart chart = new CandleStickChart(this);

        CandleStickChart chart2 = new CandleStickChart(this);

        RelativeLayout rl = (RelativeLayout) findViewById(R.id.chart);
        rl.addView(chart,8000,600);

        RelativeLayout rl2 = (RelativeLayout) findViewById(R.id.chart2);
        rl.addView(chart2,8000,600);

        //HorizontalScrollView scrollView = (HorizontalScrollView) findViewById(R.id.chart_scroll);
        //scrollView.requestChildFocus(findViewById(R.id.lincharts), scrollView);


        chart.setHighlightPerDragEnabled(true);
        chart.setDrawBorders(true);
        chart.setBorderColor(getResources().getColor(R.color.colorAccent));

        chart2.setHighlightPerDragEnabled(true);
        chart2.setDrawBorders(true);
        chart2.setBorderColor(getResources().getColor(R.color.colorAccent));

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Details");

        //tvDetails = findViewById(R.id.tvDetails);
        tvName = findViewById(R.id.tvName);
        //tvNumber = findViewById(R.id.tvNumber);

        Intent intent = getIntent();

        tvName.setText(intent.getStringExtra("Name"));

        List<Visit> visits = new ArrayList<Visit>();
        visits.add(new Visit(10.0f, 7.12f, 9.05f));
        visits.add(new Visit(10.0f, 10.34f, 11.50f));
        visits.add(new Visit(11.0f, 11.34f, 14.50f));
        visits.add(new Visit(12.0f, 1.34f, 3.50f));
        visits.add(new Visit(12.0f, 5.34f, 7.50f));
        visits.add(new Visit(14.0f, 10.34f, 11.50f));
        visits.add(new Visit(16.0f, 13.41f, 16.34f));
        visits.add(new Visit(16.0f, 5.23f, 7.80f));
        visits.add(new Visit(20.0f, 15.0f, 17.32f));
        visits.add(new Visit(21.0f, 10.34f, 11.50f));
        visits.add(new Visit(24.0f, 3.12f, 7.21f));



        List<CandleEntry> entries = new ArrayList<CandleEntry>();

        for (Visit data : visits)
        {
            entries.add(new CandleEntry(data.getDate(), 20f, 5f, data.getStime(), data.getEtime()));
        }

        CandleDataSet dataSet = new CandleDataSet(entries, "Times at DeBart");
        dataSet.setColor(Color.rgb(80,80,80));
        dataSet.setDecreasingColor(getResources().getColor(R.color.colorPrimary));
        dataSet.setDecreasingPaintStyle(Paint.Style.FILL);
        dataSet.setIncreasingColor(getResources().getColor(R.color.colorPrimaryDark));
        dataSet.setIncreasingPaintStyle(Paint.Style.FILL);
        dataSet.setNeutralColor(Color.LTGRAY);
        dataSet.setShadowColor(140);    
        dataSet.setShadowWidth(0.8f);

         CandleDataSet dataSet2 = new CandleDataSet(entries, "Times at DeBart");
         dataSet2.setColor(Color.rgb(80,80,80));
         dataSet2.setDecreasingColor(getResources().getColor(R.color.colorPrimary));
         dataSet2.setDecreasingPaintStyle(Paint.Style.FILL);
         dataSet2.setIncreasingColor(getResources().getColor(R.color.colorPrimaryDark));
         dataSet2.setIncreasingPaintStyle(Paint.Style.FILL);
         dataSet2.setNeutralColor(Color.LTGRAY);
         dataSet2.setShadowColor(140);
         dataSet2.setShadowWidth(0.8f);



        CandleData candleData = new CandleData(dataSet);
        chart.setData(candleData);
        chart.invalidate();

        CandleData candleData2 = new CandleData(dataSet2);
        chart2.setData(candleData);
        chart2.invalidate();

    }
}