package com.example.olioht.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.olioht.AreaData;
import com.example.olioht.DataCenter;
import com.example.olioht.R;
import com.example.olioht.Settings;
import com.example.olioht.databinding.FragmentHomeBinding;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private View view;
    private DataCenter C;
    private RecyclerView recyclerView;
    private PinnedListAdapter recyclerAdapter;
    private ArrayList<AreaData> pinnedAreaData;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View root, @Nullable Bundle savedInstanceState) {
        C = DataCenter.getInstance();
        pinnedAreaData = C.getPinnedAreaCovidData();
        recyclerView = (RecyclerView) root.findViewById(R.id.pinnedRecyclerView);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        System.out.println(position);
                        ArrayList<AreaData> pinnedAreaData = C.getPinnedAreaCovidData();
                        AreaData currentAreaData = pinnedAreaData.get(position);
                        C.setCurrentAreaData(currentAreaData);
                        Navigation.findNavController(view).navigate(R.id.action_navigation_home_to_blankFragment);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
        if (recyclerView == null) {System.out.println("joo");}
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerAdapter = new PinnedListAdapter(getActivity(), pinnedAreaData);
        recyclerView.setAdapter(recyclerAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}