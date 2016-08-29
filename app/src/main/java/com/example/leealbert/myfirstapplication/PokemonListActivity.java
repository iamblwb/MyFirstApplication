package com.example.leealbert.myfirstapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.leealbert.myfirstapplication.adapter.PokemonListViewAdapter;
import com.example.leealbert.myfirstapplication.model.OwnedPokemonDataManager;
import com.example.leealbert.myfirstapplication.model.OwnedPokemonInfo;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

//public class PokemonListActivity extends AppCompatActivity implements OnPokemonSelectedChangeListener {
public class PokemonListActivity extends CustomizedActivity implements OnPokemonSelectedChangeListener, AdapterView.OnItemClickListener{
    public final static int detailActivityRequestCode = 1;
    public final static String ownedPokemonInfoKey = "ownedPokemonInfoKey";

    PokemonListViewAdapter arrayAdapter;
    ArrayList<OwnedPokemonInfo> ownedPokemonInfos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_list);

        OwnedPokemonDataManager dataManager = new OwnedPokemonDataManager(this);
        dataManager.loadListviewData();
        dataManager.loadPokemonTypes();

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
        listview.setOnItemClickListener(this);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        OwnedPokemonInfo ownedPokemonInfo = arrayAdapter.getItem(position);
        Intent intent = new Intent();
        intent.setClass(PokemonListActivity.this, DetailActivity.class);
        intent.putExtra(ownedPokemonInfoKey, ownedPokemonInfo);
        startActivityForResult(intent,detailActivityRequestCode);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode == detailActivityRequestCode)//this result came from detail activity
        {
            if (resultCode == DetailActivity.savePokemonIntoComputer)
            {
                String pokemonName = data.getStringExtra(OwnedPokemonInfo.nameKey);

                if(arrayAdapter != null)
                {
                    OwnedPokemonInfo ownedPokemonInfo = arrayAdapter.getItemWithName(pokemonName);
                    arrayAdapter.remove(ownedPokemonInfo);

                    //alternatives
                    //ownedPokemonInfos.remove(ownedPokemonInfo);
                    //arrayAdapter.notifyDataSetChanged();


                    Toast.makeText(this,"已經被存到電腦裡",Toast.LENGTH_SHORT).show();
                }
            }
            else if(resultCode == DetailActivity.levelUpPokemon)
            {
                String pokemonName = data.getStringExtra(OwnedPokemonInfo.nameKey);

                if(arrayAdapter != null)
                {
                    OwnedPokemonInfo ownedPokemonInfo = arrayAdapter.getItemWithName(pokemonName);
                    ownedPokemonInfo.level++;
                    arrayAdapter.update(ownedPokemonInfo);

                    //alternatives
                    //ownedPokemonInfos.remove(ownedPokemonInfo);
                    //arrayAdapter.notifyDataSetChanged();


                    Toast.makeText(this,"Level up",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
