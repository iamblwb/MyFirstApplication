package com.example.leealbert.myfirstapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.leealbert.myfirstapplication.OnPokemonSelectedChangeListener;
import com.example.leealbert.myfirstapplication.R;
import com.example.leealbert.myfirstapplication.model.OwnedPokemonInfo;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leealbert on 16/8/25.
 */
public class PokemonListViewAdapter extends ArrayAdapter<OwnedPokemonInfo> implements OnPokemonSelectedChangeListener {



    public ArrayList<OwnedPokemonInfo> selectedPokemons = new ArrayList<>();
    public OnPokemonSelectedChangeListener pokemonSelectedChangeListener;

    LayoutInflater mInflater;
    int mRowLayoutId;
    public PokemonListViewAdapter(Context context, int resource, List<OwnedPokemonInfo> objects) {
        super(context, resource, objects);

        mRowLayoutId = resource;
        mInflater = LayoutInflater.from(context);
        ViewHolder.mAdapter = this;
    }

    @Override
    public View getView(int position, View rowView, ViewGroup parent) {
        OwnedPokemonInfo data = getItem(position);

        ViewHolder viewHolder = null;
        if(rowView == null)
        {
            rowView = mInflater.inflate(mRowLayoutId,null);
            viewHolder = new ViewHolder(rowView);
            rowView.setTag(viewHolder);//cache viewHolder
        }
        else
        {
            viewHolder = (ViewHolder)rowView.getTag();

        }

        viewHolder.setView(data);
        return rowView;

        //return super.getView(position, convertView, parent);
    }

    @Override
    public void onSelectedChange(OwnedPokemonInfo ownedPokemonInfo) {
        if(ownedPokemonInfo.isSelected)
        {
            selectedPokemons.add(ownedPokemonInfo);
        }
        else
        {
            selectedPokemons.remove(ownedPokemonInfo);
        }

        if(pokemonSelectedChangeListener != null) {
            pokemonSelectedChangeListener.onSelectedChange(ownedPokemonInfo);
        }

    }

    public static class ViewHolder implements View.OnClickListener
    {
        //or you can create new java class with type interface...

        View mRowView;
        ImageView mAppearanceImg;
        TextView mNameText;
        TextView mLevelText;
        TextView mCurrentHP;
        TextView mMaxHP;
        ProgressBar mHPBar;
        OwnedPokemonInfo mData;

        public static PokemonListViewAdapter mAdapter;


        public ViewHolder(View rowView)
        {
            mRowView = rowView;
            mAppearanceImg = (ImageView) rowView.findViewById(R.id.appearanceImg);

            mAppearanceImg.setOnClickListener(this);

            mNameText = (TextView) rowView.findViewById(R.id.nameText);
            mLevelText = (TextView) rowView.findViewById(R.id.levelText);
            mCurrentHP = (TextView) rowView.findViewById(R.id.currentHP);
            mMaxHP = (TextView) rowView.findViewById(R.id.maxHP);
            mHPBar = (ProgressBar) rowView.findViewById(R.id.hpBar);
        }


        //bind mRowView with data
        public void setView(OwnedPokemonInfo data)
        {
            mData = data;

            mRowView.setActivated(data.isSelected);

            mNameText.setText(data.name);
            mLevelText.setText(String.valueOf(data.level));
            mCurrentHP.setText(String.valueOf(data.currentHP));
            mMaxHP.setText(String.valueOf(data.maxHP));

            int progress = (int)(((float)data.currentHP/data.maxHP) * 100);
            mHPBar.setProgress(progress);

            //TODO: load image through library from Internet, universal image loader

            ImageLoader imageLoader = ImageLoader.getInstance();
            // Get singleton instance
            // Load image, decode it to Bitmap and display Bitmap in ImageView (or any other view
            //  which implements ImageAware interface)
            //imageLoader.displayImage("https://www.csie.ntu.edu.tw/~r03944003/listImg/" + String.valueOf(data.pokemonId) + ".png", mAppearanceImg);
            imageLoader.displayImage("https://www.csie.ntu.edu.tw/~r03944003/listImg/" + data.pokemonId + ".png", mAppearanceImg);

            //String path
            //path.format


        }

        public void setSelected()
        {
            mData.isSelected = !mData.isSelected;
            mRowView.setActivated(mData.isSelected);
            mAdapter.onSelectedChange(mData);
        }


        @Override
        public void onClick(View v) {
            int viewId = v.getId();
            if(viewId == R.id.appearanceImg)
            {
                setSelected();
            }
        }
    }


    public OwnedPokemonInfo getItemWithName(String pokemonName)
    {
        for(int i=0;i< getCount();i++)
        {
            OwnedPokemonInfo ownedPokemonInfo = getItem(i);
            if (ownedPokemonInfo.name.equals(pokemonName))
            {
                return ownedPokemonInfo;
            }
        }

        return null;
    }

    public void update(OwnedPokemonInfo newData)
    {
        OwnedPokemonInfo oldData = getItemWithName(newData.name);

        oldData.skills = newData.skills;
        oldData.name = newData.name;
        oldData.level = newData.level;
        oldData.currentHP = newData.currentHP;
        oldData.maxHP = newData.maxHP;

        //why can't oldData = newData here???

        notifyDataSetChanged();//reflect changes on ListView
    }
}
