package com.example.olioht.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.olioht.DataCenter;
import com.example.olioht.R;
import com.example.olioht.databinding.FragmentSearchBinding;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    private FragmentSearchBinding binding;

    SearchView searchView;
    ListView listView;
    ArrayList<String> townList;
    ArrayAdapter<String> arrayAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }


    @Override
    public void onViewCreated(@NonNull View root, @Nullable Bundle savedInstanceState) {
        searchView = (SearchView) root.findViewById(R.id.searchView);
        DataCenter C = DataCenter.getInstance();
        townList = C.getAreaLabels();
        listView = (ListView) root.findViewById(R.id.listView);
        arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,android.R.id.text1, townList);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String areaLabel = adapterView.getItemAtPosition(i).toString();
                System.out.println("Search: " + areaLabel);
                C.searchData(areaLabel);
                Navigation.findNavController(view).navigate(R.id.action_navigation_search_to_dataFragment);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                SearchFragment.this.arrayAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                SearchFragment.this.arrayAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
