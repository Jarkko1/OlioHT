
package com.example.olioht.ui.covidData;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.olioht.AreaCovidData;
import com.example.olioht.CovidCenter;
import com.example.olioht.CovidData;
import com.example.olioht.R;
import com.example.olioht.databinding.FragmentHomeBinding;
import com.example.olioht.databinding.FragmentSettingsBinding;

import java.util.ArrayList;

public class BlankFragment extends Fragment {

    private FragmentHomeBinding binding;
    private View view;
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter recyclerAdapter;
    private ArrayList<String> dataList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_blank, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        TextView testText = this.view.findViewById(R.id.testText);
        CovidCenter C = CovidCenter.getInstance();
        AreaCovidData areaCovidData = C.getSomeAreaCovidData();
        dataList = getCovidDataAsStringList(areaCovidData);
        recyclerView = (RecyclerView) this.view.findViewById(R.id.dataRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerAdapter = new MyRecyclerViewAdapter(getActivity(), dataList);
        recyclerView.setAdapter(recyclerAdapter);
        testText.setText(areaCovidData.getArea().getLabel());
    }

    private ArrayList<String> getCovidDataAsStringList(AreaCovidData areaCovidData) {
        ArrayList<String> dataList = new ArrayList<>();
        ArrayList<CovidData> covidDataList = areaCovidData.getCases();
        for (int i = (covidDataList.size() - 1); i >= 0; i--) {
            CovidData covidData = covidDataList.get(i);
            if (covidData.getValue() != null) {
                dataList.add(covidData.getLabel() + ":   " + covidData.getValue());
            }
        }
        return dataList;
    }
}