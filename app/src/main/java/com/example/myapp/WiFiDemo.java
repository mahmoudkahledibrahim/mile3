package com.example.myapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WiFiDemo extends AppCompatActivity implements View.OnClickListener {
    WifiManager wifi;
    ListView lv;
    TextView textStatus;
    Button buttonScan;
    int size = 0;
    List<ScanResult> results;
    String ITEM_KEY = "key";
    ArrayList<HashMap<String, String>> arraylist = new ArrayList<HashMap<String, String>>();
    SimpleAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wi_fi_demo);

        textStatus = (TextView) findViewById(R.id.textStatus);
        buttonScan = (Button) findViewById(R.id.buttonScan);
        buttonScan.setOnClickListener(this);
        lv = (ListView)findViewById(R.id.list);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(WiFiDemo.this, Signout.class));
                finish();
            }
        });

        wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifi.isWifiEnabled() == false){
            Toast.makeText(getApplicationContext(), "wifi is disabled..making it enabled", Toast.LENGTH_LONG).show();
            wifi.setWifiEnabled(true);
        }
        this.adapter = new SimpleAdapter(WiFiDemo.this, arraylist, R.layout.row, new String[] { ITEM_KEY }, new int[] { R.id.list_value });
        lv.setAdapter(this.adapter);

        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context c, Intent intent) {
                results = wifi.getScanResults();
                size = results.size();
            }
        }, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
    }

    public void onClick(View view){
//        if(view == buttonScan){
        arraylist.clear();
        wifi.startScan();
        List<ScanResult> results = wifi.getScanResults();
        Toast.makeText(this, "..Scanning..", Toast.LENGTH_SHORT).show();
        for (ScanResult result : results){
            Toast.makeText(this, result.SSID,Toast.LENGTH_SHORT).show();
        }
        try {
            size = size - 1;
            while (size >= 0) {
                String temp =results.get(size).SSID.substring(0,3);
                String mac =wifi.getConnectionInfo().getMacAddress();
                 if(temp.equalsIgnoreCase("esp")) {
                    HashMap<String, String> item = new HashMap<String, String>();
                    item.put(ITEM_KEY, "SSID : " + results.get(size).SSID + "\n" + " MAC : " + mac);
                    arraylist.add(item);
                    size--;
                    adapter.notifyDataSetChanged();
               }
               else
               size--;
          }
        }
        catch (Exception e)
        { }
//      }
    }
}