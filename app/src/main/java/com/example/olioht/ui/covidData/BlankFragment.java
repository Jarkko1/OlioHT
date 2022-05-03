
package com.example.olioht.ui.covidData;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    private CovidCenter C;
    private View view;
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter recyclerAdapter;
    private AreaCovidData currentAreaData;
    private boolean pinned;
    private ArrayList<String> dataList;
    private Button pinUnpinButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_blank, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        /* Getting data */
        C = CovidCenter.getInstance();
        currentAreaData = C.getCurrentAreaData();
        pinned = C.isThisPinned(currentAreaData);

        /* Header text */
        TextView headerText = this.view.findViewById(R.id.testText);
        headerText.setText(currentAreaData.getArea().getLabel());

        /* Setting up pin and unpin button: */
        pinUnpinButton = this.view.findViewById(R.id.pinunpin);
        if (pinned == true) {
            pinUnpinButton.setText("Unpin");
        } else {
            pinUnpinButton.setText("Pin");
        }
        pinUnpinButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (pinned == true) {
                    C.removePinnedAreaData(currentAreaData);
                    pinned = false;
                    pinUnpinButton.setText("Pin");
                } else {
                    C.addPinnedAreaData(currentAreaData);
                    pinned = true;
                    pinUnpinButton.setText("Unpin");
                }
            }
        });

        /* Setting up recyclerview */
        recyclerView = (RecyclerView) this.view.findViewById(R.id.dataRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerAdapter = new MyRecyclerViewAdapter(getActivity(), currentAreaData.getCases());
        recyclerView.setAdapter(recyclerAdapter);
    }

    private void setPinUnPinButtonText() {
        Button pinUnPinButton = this.view.findViewById(R.id.pinunpin);
        if (C.isThisPinned(currentAreaData)) {
            pinUnPinButton.setText("Pin");
        } else {
            pinUnPinButton.setText("Unpin");
        }
        return;
    }
    /*
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
    }*/
}