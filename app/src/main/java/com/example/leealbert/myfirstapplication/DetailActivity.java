package com.example.leealbert.myfirstapplication;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import com.example.leealbert.myfirstapplication.model.OwnedPokemonInfo;
import com.nostra13.universalimageloader.core.ImageLoader;

public class DetailActivity extends AppCompatActivity {

    public static int savePokemonIntoComputer = 1;
    public static int levelUpPokemon = 2;
    OwnedPokemonInfo ownedPokemonInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent srcIntent = getIntent();
        ownedPokemonInfo = srcIntent.getParcelableExtra(PokemonListActivity.ownedPokemonInfoKey);
        setUI(ownedPokemonInfo);

    }

    void setUI(OwnedPokemonInfo ownedPokemonInfo)
    {
        setContentView(R.layout.activity_detail);

        ((TextView)findViewById(R.id.nameText)).setText(ownedPokemonInfo.name);
        ((TextView)findViewById(R.id.levelText)).setText(String.valueOf(ownedPokemonInfo.level));
        ((TextView)findViewById(R.id.currentHP)).setText(String.valueOf(ownedPokemonInfo.currentHP));
        ((TextView)findViewById(R.id.maxHP)).setText(String.valueOf(ownedPokemonInfo.maxHP));
        int progress = (int)(((float)ownedPokemonInfo.currentHP/ownedPokemonInfo.maxHP)*100);
        ((ProgressBar)findViewById(R.id.hpBar)).setProgress(progress);

        TextView type1Text = (TextView)findViewById(R.id.type1Text);

        if(ownedPokemonInfo.type_1 != -1)
        {
            type1Text.setText(ownedPokemonInfo.typeNames[ownedPokemonInfo.type_1]);
        }
        else
        {
            type1Text.setText("");
        }

        TextView type2Text = (TextView)findViewById(R.id.type2Text);
        if(ownedPokemonInfo.type_2 != -1)
        {
            type2Text.setText(ownedPokemonInfo.typeNames[ownedPokemonInfo.type_2]);
        }
        else
        {
            type2Text.setText("");
        }

        Resources resources = this.getResources();

        for(int i=1;i<=ownedPokemonInfo.maxNumSkills;i++)
        {
            int skillResId = resources.getIdentifier(String.format("skill%dText", i),"id",getPackageName());
            TextView skillTextView = ((TextView)findViewById(skillResId));
            if (ownedPokemonInfo.skills[i-1] !=null)
            {
                skillTextView.setText(ownedPokemonInfo.skills[i-1]);
            }
            else
            {
                skillTextView.setText("");
            }
        }

        String url = String.format("http://www.csie.ntu.edu.tw/~r03944003/detailImg/%d.png",ownedPokemonInfo.pokemonId);

        ImageLoader.getInstance().displayImage(url, (ImageView) findViewById(R.id.appearanceImg));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.detail_action_bar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();

        if(itemId == R.id.action_save)
        {
            Intent intent = new Intent();
            intent.putExtra(ownedPokemonInfo.nameKey,ownedPokemonInfo.name);
            setResult(savePokemonIntoComputer,intent);
            finish();//or press back button
            return true;
        }
        else if(itemId == R.id.action_level_up)
        {
            Intent intent = new Intent();
            intent.putExtra(ownedPokemonInfo.nameKey,ownedPokemonInfo.name);
            setResult(levelUpPokemon,intent);
            //finish();//or press back button
            ((TextView)findViewById(R.id.levelText)).setText(String.valueOf(ownedPokemonInfo.level+1));
            return true;
        }
        else
        {
            return super.onOptionsItemSelected(item);
        }
    }
}
