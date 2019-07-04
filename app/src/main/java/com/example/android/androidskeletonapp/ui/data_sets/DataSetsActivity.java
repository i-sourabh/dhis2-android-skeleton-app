package com.example.android.androidskeletonapp.ui.data_sets;

import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.example.android.androidskeletonapp.R;
import com.example.android.androidskeletonapp.data.Sdk;
import com.example.android.androidskeletonapp.ui.base.ListActivity;

import org.hisp.dhis.android.core.d2manager.D2Manager;
import org.hisp.dhis.android.core.dataset.DataSet;

import java.util.List;

public class DataSetsActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUp(R.layout.activity_data_sets, R.id.dataSetsToolbar, R.id.dataSetsRecyclerView);
        // TODO List DataSets
        observeDataSets();

    }
    private void observeDataSets(){
        DataSetsAdapter dataSetsAdapter=new DataSetsAdapter();
        recyclerView.setAdapter(dataSetsAdapter);

        LiveData<PagedList<DataSet>> dataSets= Sdk.d2()
                .dataSetModule()
                .dataSets
                .withStyle()
                .getPaged(20);
        dataSets.observe(this,pagedDataSets ->{
            dataSetsAdapter.submitList(pagedDataSets);
            findViewById(R.id.dataSetsNotificator).setVisibility(
                    pagedDataSets.isEmpty() ? View.VISIBLE: View.GONE
            );
        });
    }


}
