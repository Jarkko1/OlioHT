
package com.example.olioht.ui.data;

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

import com.example.olioht.AreaData;
import com.example.olioht.DataCenter;
import com.example.olioht.R;
import com.example.olioht.Settings;
import com.example.olioht.databinding.FragmentHomeBinding;

import java.util.ArrayList;

public class DataFragment extends Fragment {

    private FragmentHomeBinding binding;
    private Settings S;
    private int language;
    private DataCenter C;
    private View view;
    private RecyclerView recyclerView;
    private DataAdapter recyclerAdapter;
    private AreaData currentAreaData;
    private boolean pinned;
    private ArrayList<String> dataList;
    private Button pinUnpinButton;
    private String[] pin = {"Pin", "Kiinnitä"};
    private String[] unpin = {"Unpin", "Poista kiinnitys"};



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_data, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        /* Getting settings */
        S = Settings.getInstance();
        language = S.getLanguage();

        /* Getting data */
        C = DataCenter.getInstance();
        currentAreaData = C.getCurrentAreaData();
        pinned = C.isThisPinned(currentAreaData);

        /* Header text */
        TextView headerText = this.view.findViewById(R.id.testText);
        headerText.setText(currentAreaData.getArea().getLabel());

        /* Setting up pin and unpin button: */
        pinUnpinButton = this.view.findViewById(R.id.pinunpin);
        if (pinned == true) {
            pinUnpinButton.setText(unpin[language]);
        } else {
            pinUnpinButton.setText(pin[language]);
        }
        pinUnpinButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (pinned == true) {
                    C.removePinnedAreaData(currentAreaData);
                    pinned = false;
                    pinUnpinButton.setText(pin[language]);
                } else {
                    C.addPinnedAreaData(currentAreaData);
                    pinned = true;
                    pinUnpinButton.setText(unpin[language]);
                }
            }
        });

        /* Setting up recyclerview */
        recyclerView = (RecyclerView) this.view.findViewById(R.id.dataRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerAdapter = new DataAdapter(getActivity(), currentAreaData.getCases());
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
}