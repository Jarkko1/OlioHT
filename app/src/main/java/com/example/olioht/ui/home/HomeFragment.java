package com.example.olioht.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.olioht.AreaCovidData;
import com.example.olioht.CovidCenter;
import com.example.olioht.R;
import com.example.olioht.databinding.FragmentHomeBinding;
import com.example.olioht.ui.covidData.MyRecyclerViewAdapter;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private View view;
    private CovidCenter C;
    private RecyclerView recyclerView;
    private PinnedListAdapter recyclerAdapter;
    private ArrayList<AreaCovidData> pinnedAreaCovidData;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View root, @Nullable Bundle savedInstanceState) {
        C = CovidCenter.getInstance();
        pinnedAreaCovidData = C.getPinnedAreaCovidData();
        recyclerView = (RecyclerView) root.findViewById(R.id.pinnedRecyclerView);
        if (recyclerView == null) {System.out.println("joo");}
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerAdapter = new PinnedListAdapter(getActivity(), pinnedAreaCovidData);
        recyclerView.setAdapter(recyclerAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}