package be.ttic.hj.heroesmanager.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import be.ttic.hj.heroesmanager.R;
import be.ttic.hj.heroesmanager.activities.Hero;

/**
 * Created by Jimmy on 12/12/2016.
 */

public class HeroUIAdapter extends ArrayAdapter<Hero> {

    private int layoutID;

    public HeroUIAdapter(Context context, int layoutId, List<Hero> items) {
        super(context, layoutId, items);
        this.layoutID = layoutId;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Hero current = getItem(position);

        LayoutInflater inflater =  (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View hero_list_item = inflater.inflate(layoutID,parent,false);

        //Récupération des éléments de l'interface graphique
        ImageView image1 =  (ImageView)hero_list_item.findViewById(R.id.image1);
        TextView text1 = (TextView)hero_list_item.findViewById(R.id.text1);
        RatingBar rating1 = (RatingBar)hero_list_item.findViewById(R.id.rating1);

        //Définir la valeur des éléments
        int imageID = 0;
        if (current.is_masked()){
            imageID = R.drawable.masked_super_hero;
        }else{
            imageID = R.drawable.super_hero;
        }
        image1.setImageResource(imageID);
        text1.setText(current.getNickname());
        rating1.setRating(current.getRating());

        //retourner la vue modifiée
        return hero_list_item;
    }
     public long getItemId(int position){
         return getItem(position).getId_hero();
     }

}
