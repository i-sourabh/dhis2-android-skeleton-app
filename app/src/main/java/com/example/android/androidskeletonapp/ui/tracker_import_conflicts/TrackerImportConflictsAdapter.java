package com.example.android.androidskeletonapp.ui.tracker_import_conflicts;

import android.view.ViewGroup;

import com.example.android.androidskeletonapp.ui.base.SubListItemHolder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.hisp.dhis.android.core.imports.TrackerImportConflict;

import java.util.ArrayList;
import java.util.List;

public class TrackerImportConflictsAdapter extends RecyclerView.Adapter<SubListItemHolder> {

    // TODO bind conflicts to sub_list_item.xml
    List<TrackerImportConflict> conflicts=new ArrayList<>();

    @NonNull
    @Override
    public SubListItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull SubListItemHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public  void setConflicts(List<TrackerImportConflict> conflicts)
    {
        this.conflicts=conflicts;
        notifyDataSetChanged();

    }
}