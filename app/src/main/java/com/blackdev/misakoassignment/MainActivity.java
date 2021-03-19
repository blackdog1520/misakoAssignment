package com.blackdev.misakoassignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import static com.blackdev.misakoassignment.CustomScrollListener.PAGE_START;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Button resetButton;
    LinearLayout rootLayout;
    LinearLayoutManager linearLayoutManager;
    String TAG = "MainActivity:";
    private int currentPage = PAGE_START;
    private boolean isLastPage = false;
    private int totalPage = 50/10;
    private boolean isLoading = false;
    int itemCount = 0;

    List<RegionData> dataList = new ArrayList<>();
    List<RegionData> items = new ArrayList<>();
    RoomDB database;
    RecyclerViewAdapter adapter;

    void init() {
        recyclerView = findViewById(R.id.recyclerView);
        resetButton = findViewById(R.id.resetButton);
        rootLayout = findViewById(R.id.rootLayout);
        database = RoomDB.getInstance(this);
        linearLayoutManager = new LinearLayoutManager(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new RecyclerViewAdapter(MainActivity.this,new ArrayList<>());
        recyclerView.setAdapter(adapter);

        if(Utils.isNetworkAvailable(this)){
            // get all data from rest api
            Log.e(TAG,"Network Available");
            getList();
            if (dataList == null) {
                Snackbar.make(rootLayout, "Failed to fetch data", Snackbar.LENGTH_SHORT);
            }

        } else {
            Log.e(TAG,"Network not Available");
            dataList = database.regionDao().getAll();
            Log.e(TAG,"Size: "+dataList.size());
           // adapter.notifyDataSetChanged();
        }

        Log.e(TAG,"Setting View");
        recyclerView.addOnScrollListener(new CustomScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage++;
                doApiCall();
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }

        });

    }

    private void doApiCall() {
        ArrayList<RegionData> items = new ArrayList<>();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    itemCount++;
                    if(itemCount > dataList.size()-1) break;
                    RegionData postItem = dataList.get(itemCount);
                    items.add(postItem);
                }
                /**
                 * manage progress view
                 */
                if (currentPage != PAGE_START) adapter.removeLoading();
                adapter.addItems(items);
                if (currentPage < totalPage) {
                    adapter.addLoading();
                } else {
                    isLastPage = true;
                }
                isLoading = false;
            }
        }, 1500);
    }



    private void getList() {
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait..."); // set message
        progressDialog.show();
        Log.e(TAG,"Getting List");

        Api.getClient().getDataList(new Callback<List<POJO>>() {
            @Override
            public void success(List<POJO> pojos, Response response) {
                Log.e(TAG,"Got the list");
                progressDialog.dismiss();
                addToDataList(pojos);
                //dataList = pojos;
                Log.e(TAG,pojos.get(0).getCapital());
                doApiCall();
                //adapter.notifyDataSetChanged();
            }

            @Override
            public void failure(RetrofitError error) {
                Snackbar.make(rootLayout, "Failed", Snackbar.LENGTH_SHORT);
            }
        });
    }

    private void addToDataList(List<POJO> pojos) {
        for(int i=0; i<pojos.size(); i++) {
            RegionData data = new RegionData();
            data.setCountryName(pojos.get(i).getName());
            data.setCapitalName(pojos.get(i).getCapital());
            data.setBorders(pojos.get(i).getBorders());
            data.setPopulation(pojos.get(i).getPopulation());
            data.setRegionName(pojos.get(i).getSubregion());
            data.setSubRegionName(pojos.get(i).getSubregion());
            data.setUrl(pojos.get(i).getFlag());
            data.setImage(null);
            data.setFlagLoaded(false);
            ArrayList<Language> temp = pojos.get(i).getLanguages();
            ArrayList<String> lang = new ArrayList<>();
            for(int j=0; j<temp.size(); j++){
                lang.add(temp.get(j).getName());
            }
            data.setLanguages(lang);
            dataList.add(data);
            database.regionDao().insert(data);
        }
    }
}