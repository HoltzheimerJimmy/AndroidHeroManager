package be.ttic.hj.heroesmanager.activities;

import android.content.Intent;
import android.media.Rating;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.Toast;

import java.util.Date;

import be.ttic.hj.heroesmanager.MainActivity;
import be.ttic.hj.heroesmanager.R;
import be.ttic.hj.heroesmanager.db.DBHelper;
import be.ttic.hj.heroesmanager.db.HeroDAO;

public class InsertActivity extends AppCompatActivity {

    private long id_hero;
    // instancié les  différents champs éditables
    private EditText et_first_name,et_last_name,et_nickname,et_city, et_origin, et_first_release_date;
    private CheckBox cb_is_masked;
    private RatingBar rb_rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        //récupération des diffétents champs éditables
        et_first_name = (EditText) findViewById(R.id.et_first_name);
        et_last_name = (EditText) findViewById(R.id.et_last_name);
        et_nickname = (EditText) findViewById(R.id.et_nickname);
        et_city = (EditText) findViewById(R.id.et_city);
        et_origin = (EditText) findViewById(R.id.et_origin);
        et_first_release_date = (EditText) findViewById(R.id.et_first_release_date);
        cb_is_masked = (CheckBox) findViewById(R.id.cb_is_masked);
        rb_rating = (RatingBar) findViewById(R.id.rb_rating);

        //En venant de l'activité Detail
        //Récupération de l'intent puis de l'extra envoyé + valeur absurde en cas d'erreur
        id_hero = getIntent().getLongExtra(HeroDAO.COLUMN_ID,-999);
        if (id_hero>0){
            //Recupération du Hero avec l'id reçu
            HeroDAO hero = new HeroDAO(getApplicationContext());
            hero.openReadable();
            Hero detail_hero = hero.getHeroById(id_hero);
            //si il y a un hero on peuple les champs d'édition
            et_first_name.setText(detail_hero.getFirstname());
            et_last_name.setText(detail_hero.getLastname());
            et_nickname.setText(detail_hero.getNickname());
            et_city.setText(detail_hero.getCity());
            et_origin.setText(detail_hero.getOrigin());
            et_first_release_date.setText(detail_hero.getFirst_release_date());
            cb_is_masked.setChecked(detail_hero.is_masked());
            rb_rating.setRating(detail_hero.getRating());
        }
    }

    //Bind click façon Android , valable que sur les bouttons
    public void btn_send_hero_onClick(View v) {

        String firstname = et_first_name.getText().toString();

        String lastname = et_last_name.getText().toString();

        String nickname = et_nickname.getText().toString();

        String city = et_city.getText().toString();

        String origin = et_origin.getText().toString();

        String release_date = et_first_release_date.getText().toString();

        Boolean is_masked = cb_is_masked.isChecked();

        float rating = rb_rating.getRating();

        //Ajout du hero
        Hero hero = new Hero(firstname, lastname, nickname, city, origin, release_date, is_masked, rating);
        //Log.i("HJtrace", hero.toString());

        //Récupération du contexte et ouverture de la db
        HeroDAO adapter = new HeroDAO(getApplicationContext());
        adapter.openWritable();
        //si il y a un id on fais l'update
        if(id_hero>0){
            hero.setID_hero(id_hero);
            if (adapter.update(hero)) {
                //finir cette activité et reviens a la dernière de la stack => Detail ou main si venu de long click
                finish();
            } else {
                //TODO: afficher message erreur update Hero
            }
        }else //sinon on fais un insertion
        {
            if (adapter.insert(hero)) {
                //finir cette activité et reviens a la dernière de la stack => main
                finish();
            } else {
                //TODO: afficher message erreur insert Hero
            }
        }
        //Fermeture de la connexion db
        adapter.close();
    }
}
