package com.example.leealbert.myfirstapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.leealbert.myfirstapplication.model.OwnedPokemonDataManager;
import com.example.leealbert.myfirstapplication.model.OwnedPokemonInfo;

import java.util.ArrayList;

public class PokemonListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_list);

        OwnedPokemonDataManager dataManager = new OwnedPokemonDataManager(this);
        dataManager.loadListviewData();

        ArrayList<OwnedPokemonInfo> ownedPokemonInfos = dataManager.getOwnedPokemonInfos();

        //get a list of pokemonNames for testing simple listview

        ArrayList<String> pokemonNames = new ArrayList<>();

        for(OwnedPokemonInfo ownedPokemonInfo:ownedPokemonInfos)
        {
            pokemonNames.add(ownedPokemonInfo.name);
        }

        ListView listview = (ListView) findViewById(R.id.listView);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,pokemonNames);
        listview.setAdapter(arrayAdapter);

    }
}
