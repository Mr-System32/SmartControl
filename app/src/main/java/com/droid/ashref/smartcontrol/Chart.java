package com.droid.ashref.smartcontrol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.hadiidbouk.charts.BarData;
import com.hadiidbouk.charts.ChartProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Chart extends AppCompatActivity {
    private ChartProgressBar mChart;
    private static ArrayList<DataList> lists;
    private static ArrayList<String> year = new ArrayList<>();
    private static PieChart pieChart;
    private static PieDataSet dataSet;
    private static ArrayList<Entry> NoOfEmp = new ArrayList<Entry>();
    private static RecyclerView recyclerView;
    public static RecycelerViewAdapter_Devices_Bill recycelerViewAdapter;
    private static Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
         context = Chart.this;

        pieChart = findViewById(R.id.piechart);
        recyclerView = findViewById(R.id.Recycler_Devices_Bill);

        recyclerView.setScrollContainer(false);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linear = new LinearLayoutManager(this);
        linear.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linear);
        lists = new ArrayList<DataList>();
        recycelerViewAdapter = new RecycelerViewAdapter_Devices_Bill(lists, context);

//        NoOfEmp.add(new Entry(945f, 0));
//        NoOfEmp.add(new Entry(1040f, 1));
//        NoOfEmp.add(new Entry(1133f, 2));
//        NoOfEmp.add(new Entry(1240f, 3));
//        NoOfEmp.add(new Entry(1369f, 4));
//        NoOfEmp.add(new Entry(1487f, 5));
//        NoOfEmp.add(new Entry(1501f, 6));
//        NoOfEmp.add(new Entry(1645f, 7));
//        NoOfEmp.add(new Entry(1578f, 8));
//        NoOfEmp.add(new Entry(1695f, 9));


        //   ArrayList year = new ArrayList();

//        year.add("2008");
//        year.add("2009");
//        year.add("2010");
//        year.add("2011");
//        year.add("2012");
//        year.add("2013");
//        year.add("2014");
//        year.add("2015");
//        year.add("2016");
//        year.add("2017");


        ArrayList<BarData> dataList = new ArrayList<>();

        BarData data = new BarData("Sep", 3.4f, "3.4€");
        dataList.add(data);

        data = new BarData("Oct", 8.0f, "80.0€");
        dataList.add(data);

        data = new BarData("Nov", 1.8f, "10.8€");
        dataList.add(data);

        data = new BarData("Dec", 7.3f, "70.3€");
        dataList.add(data);

        data = new BarData("Jan", 6.2f, "60.2€");
        dataList.add(data);

        data = new BarData("Feb", 3.3f, "30.3€");
        dataList.add(data);

        data = new BarData("Mar", 3.3f, "30.3€");
        dataList.add(data);
        data = new BarData("Apr", 2.3f, "20.3€");
        dataList.add(data);
        data = new BarData("May", 7.3f, "70.3€");
        dataList.add(data);
        data = new BarData("Jun", 6.3f, "50.3€");
        dataList.add(data);
        data = new BarData("Jul", 10.3f, "100.3€");
        dataList.add(data);
        data = new BarData("Aug", 2.3f, "10.3€");
        dataList.add(data);
        data = new BarData("Sep", 4.3f, "20.3€");
        dataList.add(data);


        mChart = (ChartProgressBar) findViewById(R.id.ChartProgressBar);

        mChart.setDataList(dataList);
        mChart.build();
//        mChart.setOnBarClickedListener((OnBarClickedListener) this);
        mChart.disableBar(dataList.size() - 1);

        mChart.enableBar(2);
    }

//    public void BtnPressed(View view) {
//        int id = view.getId();
//        switch (id) {
//            case R.id.BtnClear :
//                mChart.removeBarValues();
//                break;
//            case R.id.BtnReset :
//                mChart.resetBarValues();
//                break;
//            case R.id.BtnClearClick :
//                mChart.removeClickedBar();
//                break;
//        }
//    }


    public static class jsonTask_Devices extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            lists.clear();

            year.clear();
            NoOfEmp.clear();
        }

        URL url;
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream;
        BufferedReader bufferedReader;
        StringBuffer stringBuffer = new StringBuffer();

        @Override
        protected String doInBackground(String... strings) {
            try {
                url = new URL(SaveSettings.Url+"read_all.php?User_ID=" + SaveSettings.UserID);

                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();
                inputStream = httpURLConnection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = " ";

                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line);
                }
                String fullfile = stringBuffer.toString();
                JSONArray jsonArray = new JSONArray(fullfile);
                for (int i = 0; i <= jsonArray.length(); i++) {

                    JSONObject child = jsonArray.getJSONObject(i);
                    String ID = child.getString("ID");
                    String Name = child.getString("D_Name");
                    String Status = child.getString("D_status");
                    String Power = child.getString("D_Power");
                    String Port = child.getString("D_Port");
                    String Hours = child.getString("D_Hours");
                    String Photo = child.getString("D_Photo");

                    lists.add(new DataList(ID, Name, Status, Power, Port, Hours, Photo));

                    year.add(Name);

                    NoOfEmp.add(new Entry(Float.parseFloat(Power), i));

                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            RecycelerViewAdapter_Devices_Bill recycelerViewAdapter = new RecycelerViewAdapter_Devices_Bill(lists, context);
            recycelerViewAdapter.notifyDataSetChanged();
            recyclerView.setAdapter(recycelerViewAdapter);


            dataSet = new PieDataSet(NoOfEmp, "Number Of Devises");

            PieData dataa = new PieData(year, dataSet);
            pieChart.setData(dataa);
            dataSet.setColors(Colors.PASTEL_COLORS);

            pieChart.animateXY(5000, 5000);

        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        jsonTask_Devices jsonTaskDevices = new jsonTask_Devices();
        jsonTaskDevices.execute();
    }


    public void Backword(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}

