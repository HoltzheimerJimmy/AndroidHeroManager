package be.ttic.hj.heroesmanager.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import be.ttic.hj.heroesmanager.MainActivity;
import be.ttic.hj.heroesmanager.R;
import be.ttic.hj.heroesmanager.db.HeroDAO;

public class DetailsActivity extends AppCompatActivity {

    private Button btn_update_hero;
    private Button btn_delete_hero;
    private long id_hero;
    private boolean deleteHero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

    }

    @Override
    protected void onResume() {
        super.onResume();

        //Récupération de l'intent puis de l'extra envoyé + valeur absurde en cas d'erreur
        id_hero = getIntent().getLongExtra(HeroDAO.COLUMN_ID,-999);
        deleteHero = getIntent().getBooleanExtra("deleteHero",false);
        if (id_hero>0){
            //Recupération du Hero avec l'id reçu
            HeroDAO hero = new HeroDAO(getApplicationContext());
            hero.openReadable();
            Hero detail_hero = hero.getHeroById(id_hero);



            //Récupération des éléments de l'interface graphique
            ImageView is_masked =  (ImageView)findViewById(R.id.iv_is_masked);
            TextView nickname = (TextView)findViewById(R.id.tv_nickname);
            RatingBar rating = (RatingBar)findViewById(R.id.rb_rating);
            TextView firstname = (TextView)findViewById(R.id.tv_first_name_hero);
            TextView lastname = (TextView)findViewById(R.id.tv_last_name_hero);
            TextView city = (TextView)findViewById(R.id.tv_city_hero);
            TextView origin = (TextView)findViewById(R.id.tv_origin_hero);
            TextView first_release_date = (TextView)findViewById(R.id.tv_first_release_date_hero);

            //Définir la valeur des éléments
            int imageID = 0;
            if (detail_hero.is_masked()){
                imageID = R.drawable.masked_super_hero;
            }else{
                imageID = R.drawable.super_hero;
            }
            is_masked.setImageResource(imageID);
            nickname.setText(detail_hero.getNickname());
            rating.setRating(detail_hero.getRating());
            firstname.setText(detail_hero.getFirstname());
            lastname.setText(detail_hero.getLastname());
            city.setText(detail_hero.getCity());
            origin.setText(detail_hero.getOrigin());
            first_release_date.setText(detail_hero.getFirst_release_date());

            //si l'intent a été appeller du long click de la main activity on déclenche directement le pop up de deletion
            if(deleteHero){
                //Quand on clique sur delete on ouvre le popup de confirmation
                AlertDialog popUp = deleteHero();
                popUp.show();
            }
            //Log.i("HJtrace",detail_hero.toString());

            //Go from Detail to Update
            btn_update_hero = (Button)findViewById(R.id.btn_update_hero);
            btn_update_hero.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    //Démarre un intent pour envoyé l'id vers l'activité Insert afin d'update avec le même formulaire
                    Intent update = new Intent(getApplicationContext(), InsertActivity.class);
                    //envoi d'un extra l'id de la colone id
                    update.putExtra(HeroDAO.COLUMN_ID,id_hero);
                    startActivity(update);
                }
            });

            //invoke Delete
            btn_delete_hero = (Button)findViewById(R.id.btn_delete_hero);
            btn_delete_hero.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    //Quand on clique sur delete on ouvre le popup de confirmation
                    AlertDialog popUp = deleteHero();
                    popUp.show();
                }
            });

        }
    }
    private AlertDialog deleteHero()
    {
        AlertDialog confirmDeleteHeroBox =new AlertDialog.Builder(this)
                //set message, title, and icon
                .setTitle(getResources().getString(R.string.popup_confirm_delete_hero_title_message))
                .setMessage(getResources().getString(R.string.popup_confirm_delete_hero_description_message))
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(getResources().getString(R.string.btn_confirm_delete_hero), new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        HeroDAO delete_hero = new HeroDAO(getApplicationContext());
                        delete_hero.openWritable();
                        delete_hero.deleteHeroById(id_hero);
                        delete_hero.close();
                        dialog.dismiss();
                        //finish de l'activité
                        finish();
                    }

                })
                .setNegativeButton(getResources().getString(R.string.btn_cancel_delete_hero), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //do nothing
                        dialog.dismiss();
                    }
                })
                .create();
        return confirmDeleteHeroBox;

    }
}
