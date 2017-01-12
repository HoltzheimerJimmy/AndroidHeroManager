package be.ttic.hj.heroesmanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import be.ttic.hj.heroesmanager.activities.DetailsActivity;
import be.ttic.hj.heroesmanager.activities.Hero;
import be.ttic.hj.heroesmanager.activities.InsertActivity;
import be.ttic.hj.heroesmanager.db.HeroDAO;
import be.ttic.hj.heroesmanager.ui.HeroUIAdapter;

public class MainActivity extends AppCompatActivity {

    private Button btn_add_hero;
    private ListView lv_hero_list;
    private long id_hero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Log.i("HJtrace","onCreate");

        //bind click button façon Java, plus puissant
        //Go from Main to Insert
        btn_add_hero = (Button)findViewById(R.id.btn_add_hero);
        btn_add_hero.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent add_hero = new Intent(getApplicationContext(), InsertActivity.class);
                startActivity(add_hero);
            }
        });

        //Récupération de la listview
        lv_hero_list = (ListView)findViewById(R.id.lv_hero_list);

    }

    @Override
    protected void onResume() {
        super.onResume();
        //Log.i("HJtrace","onResume");

        //Liste des Hero
        HeroDAO list_hero = new HeroDAO(getApplicationContext());
        list_hero.openReadable();

        List<Hero> heroes = list_hero.getAllHeroes();

        list_hero.close();

        //Log de chaque hero dans la liste
        /*Log.i("HJtrace","Liste des super Heros : ");
        for (Hero h:heroes){
            Log.i("HJtrace",h.toString());
        }*/

        HeroUIAdapter ui_adapter = new HeroUIAdapter(getApplicationContext(),R.layout.hero_list_item,heroes);
        lv_hero_list.setAdapter(ui_adapter);

        //Envoi vers les détails du héro
         lv_hero_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                 //adapterView = ListView
                 //view = Item
                 //i = id dans la vue
                 //l = _id en DB

                 //Démarre un intent pour envoyé l'id vers l'activité détail
                 Intent detail = new Intent(getApplicationContext(), DetailsActivity.class);
                 //envoi d'un extra l'id de la colone id
                 detail.putExtra(HeroDAO.COLUMN_ID,l);
                 startActivity(detail);
             }
         });

        //Long click sur un item de la list view des hero
        lv_hero_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                id_hero = l;
                AlertDialog popUp = onLongClickPopUp();
                popUp.show();
                return true;
            }
        });
    }
    private AlertDialog onLongClickPopUp()
    {
        AlertDialog confirmDeleteHeroBox =new AlertDialog.Builder(this)
                //set message, title, and icon
                .setTitle(getResources().getString(R.string.popup_what_to_do_title))
                .setMessage(getResources().getString(R.string.popup_what_to_do_description))
                .setIcon(android.R.drawable.ic_dialog_info)
                .setPositiveButton(getResources().getString(R.string.btn_delete_hero), new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        //Démarre un intent pour envoyé l'id vers l'activité détail
                        Intent detail = new Intent(getApplicationContext(), DetailsActivity.class);
                        //envoi d'un extra l'id de la colone id
                        detail.putExtra(HeroDAO.COLUMN_ID,id_hero);
                        detail.putExtra("deleteHero",true);
                        startActivity(detail);
                    }

                })
                .setNegativeButton(getResources().getString(R.string.btn_update_hero), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //démarre l'activité d'update
                        //Démarre un intent pour envoyé l'id vers l'activité Insert afin d'update avec le même formulaire
                        Intent update = new Intent(getApplicationContext(), InsertActivity.class);
                        //envoi d'un extra l'id de la colone id
                        update.putExtra(HeroDAO.COLUMN_ID,id_hero);
                        startActivity(update);
                        dialog.dismiss();
                    }
                })
                .create();
        return confirmDeleteHeroBox;

    }

}
