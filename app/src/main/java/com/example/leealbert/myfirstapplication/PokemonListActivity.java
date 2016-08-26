package com.example.leealbert.myfirstapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.leealbert.myfirstapplication.adapter.PokemonListViewAdapter;
import com.example.leealbert.myfirstapplication.model.OwnedPokemonDataManager;
import com.example.leealbert.myfirstapplication.model.OwnedPokemonInfo;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

//public class PokemonListActivity extends AppCompatActivity implements OnPokemonSelectedChangeListener {
public class PokemonListActivity extends CustomizedActivity implements OnPokemonSelectedChangeListener {
    PokemonListViewAdapter arrayAdapter;
    ArrayList<OwnedPokemonInfo> ownedPokemonInfos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_list);

        OwnedPokemonDataManager dataManager = new OwnedPokemonDataManager(this);
        dataManager.loadListviewData();

        ownedPokemonInfos = dataManager.getOwnedPokemonInfos();

        OwnedPokemonInfo[] initPokemonInfos = dataManager.getInitPokemonInfos();

        Intent srcIntent = getIntent();
        int selectedIndex = srcIntent.getIntExtra(MainActivity.selectedPokemonIndexKey,0);
        ownedPokemonInfos.add(0,initPokemonInfos[selectedIndex]);

        /*
        //get a list of pokemonNames for testing simple listview

        ArrayList<String> pokemonNames = new ArrayList<>();

        for(OwnedPokemonInfo ownedPokemonInfo:ownedPokemonInfos)
        {
            pokemonNames.add(ownedPokemonInfo.name);
        }
        */

        ListView listview = (ListView) findViewById(R.id.listView);
        //ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,pokemonNames);
        arrayAdapter = new PokemonListViewAdapter(this, R.layout.row_view_of_pokemon_list,ownedPokemonInfos);
        activityName = this.getClass().getSimpleName();
          arrayAdapter.pokemonSelectedChangeListener = this;
        listview.setAdapter(arrayAdapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        //return super.onCreateOptionsMenu(menu);

        if (arrayAdapter.selectedPokemons.isEmpty()) {
            return false;//not to show anything on action bar?
        } else {
            getMenuInflater().inflate(R.menu.selected_pokemon_action_bar, menu);
            return true;//decide whether to show the action bar or not
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int itemId = item.getItemId();
        if(itemId == R.id.action_delete) {
            for(OwnedPokemonInfo ownedPokemonInfo : arrayAdapter.selectedPokemons) {
                ownedPokemonInfos.remove(ownedPokemonInfo);

            }
            arrayAdapter.selectedPokemons.clear();
            arrayAdapter.notifyDataSetChanged();
            invalidateOptionsMenu();
            return true;
        }
        else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSelectedChange(OwnedPokemonInfo ownedPokemonInfo) {
        invalidateOptionsMenu(); //make system call onCreateOptionsMenu
    }
}
