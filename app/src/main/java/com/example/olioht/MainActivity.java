package com.example.olioht;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.olioht.ui.data.DataFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;

import com.example.olioht.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    SearchView searchView;
    DataCenter C;
    Settings S;
    SaveData SD;

    ListView listView;
    //ArrayList townList;
    ArrayAdapter<String> arrayAdapter;

    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this; //getApplicationContext();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        SD = SaveData.newInstance(this);
        C = DataCenter.getInstance();
        S = Settings.getInstance();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_search, R.id.navigation_settings)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
        searchView = (SearchView) findViewById(R.id.searchView);

    }

    public void test(View view) {
        /*
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        NavHostFragment navHostFragment = (NavHostFragment) supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main);
        NavController navController = navHostFragment.getNavController();
        NavDirections action = fragment_home_directions.actionHomeFragmentToBlankFragment();
        */
        //NavDirections action = BlankFragmentDirections.actionHomeFragmentToBlankFragment();
        //Navigation.findNavController(view).navigate(action);
        /*
        Fragment fragment = new BlankFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        //transaction.hide()
        transaction.replace(R.id.nav_host_fragment_activity_main, fragment);
        transaction.commit();

         */
        Navigation.findNavController(view).navigate(R.id.action_navigation_home_to_dataFragment);
    }

    public void setLang(String langCode){
        /* Sets the new Language for the app and restarts it */

        Locale locale = new Locale(langCode);
        locale.setDefault(locale);
        Resources resources = this.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config,resources.getDisplayMetrics());
        finish();
        startActivity(getIntent());
    }


}