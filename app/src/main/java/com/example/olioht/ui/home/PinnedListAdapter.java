package com.example.olioht.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.olioht.AreaCovidData;
import com.example.olioht.CovidData;
import com.example.olioht.R;
import com.example.olioht.ui.covidData.MyRecyclerViewAdapter;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PinnedListAdapter extends RecyclerView.Adapter<PinnedListAdapter.CustomViewHolder>{
    private ArrayList<AreaCovidData> dataList;
    private Context mContext;

    public PinnedListAdapter(Context context, ArrayList<AreaCovidData> dataList) {
        this.dataList = dataList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public PinnedListAdapter.CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pinned_item, null);
        PinnedListAdapter.CustomViewHolder viewHolder = new PinnedListAdapter.CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PinnedListAdapter.CustomViewHolder customViewHolder, int i) {
        customViewHolder.textView1.setText(dataList.get(i).getArea().getLabel());
        ArrayList<CovidData> covidData = dataList.get(i).getCases();
        CovidData covidCase = covidData.get(covidData.size()-1);
        customViewHolder.textView2.setText(covidCase.getLabel() + ": " + covidCase.getValue());
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
