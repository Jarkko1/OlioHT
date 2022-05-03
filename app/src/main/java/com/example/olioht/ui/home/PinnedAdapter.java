package com.example.olioht.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.olioht.AreaData;
import com.example.olioht.Data;
import com.example.olioht.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PinnedAdapter extends RecyclerView.Adapter<PinnedAdapter.CustomViewHolder>{
    private ArrayList<AreaData> dataList;
    private Context mContext;

    public PinnedAdapter(Context context, ArrayList<AreaData> dataList) {
        this.dataList = dataList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public PinnedAdapter.CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pinned_item, viewGroup, false);
        PinnedAdapter.CustomViewHolder viewHolder = new PinnedAdapter.CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PinnedAdapter.CustomViewHolder customViewHolder, int i) {
        if (dataList.size() > i) {
            customViewHolder.textView1.setText(dataList.get(i).getArea().getLabel());
            ArrayList<Data> covidData = dataList.get(i).getCases();
            if (covidData.size() > 0) {
                Data covidCase = covidData.get(0);
                customViewHolder.textView2.setText(covidCase.getLabel() + ": " + covidCase.getValue());
            } else {
                customViewHolder.textView2.setText("");
            }
        }
    }

    @Override
    public int getItemCount() {
        System.out.println("data size: " + dataList.size());
        return dataList.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView textView1;
        protected TextView textView2;


        public CustomViewHolder(View view) {
            super(view);
            this.textView1 = (TextView) view.findViewById(R.id.areaLabelText);
            this.textView2 = (TextView) view.findViewById(R.id.areaDataText);
        }
    }
}
