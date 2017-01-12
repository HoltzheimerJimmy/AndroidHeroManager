package be.ttic.hj.heroesmanager.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import be.ttic.hj.heroesmanager.activities.Hero;

/**
 * Created by Jimmy on 08/12/2016.
 */

public class HeroDAO {
    //Nom de la table
    public static final String TABLE_HERO = "hero";
    //Colones de la table
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_FIRST_NAME = "first_name";
    public static final String COLUMN_LAST_NAME = "last_name";
    public static final String COLUMN_NICKNAME = "nickname";
    public static final String COLUMN_CITY = "city";
    public static final String COLUMN_ORIGIN = "origin";
    public static final String COLUMN_FIRST_RELEASE_DATE = "first_release_date";
    public static final String COLUMN_IS_MASKED = "is_masked";
    public static final String COLUMN_RATING = "rating";

    //Requête de création de la table
    public static final String CREATE_REQUEST = "CREATE TABLE " + HeroDAO.TABLE_HERO
            +"("
            +COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
            +COLUMN_FIRST_NAME+" TEXT, "
            +COLUMN_LAST_NAME+" TEXT, "
            +COLUMN_NICKNAME+" TEXT NOT NULL UNIQUE, "
            +COLUMN_CITY+" TEXT, "
            +COLUMN_ORIGIN+" TEXT, "
            +COLUMN_FIRST_RELEASE_DATE+" TEXT, "
            +COLUMN_IS_MASKED+" INTEGER, "
            +COLUMN_RATING+" REAL "
            +");";

    //Requête d'upgrade de la table
    //TODO modifier la requête d'upgrade
    public static final String UPGRADE_REQUEST = "DROP TABLE " + HeroDAO.TABLE_HERO;

    private DBHelper dbHelper;
    private Context context;
    private SQLiteDatabase db;

    //Création du contexte pour ouvrir la connexion db via le DBHelper
    public HeroDAO(Context c){
        dbHelper = new DBHelper(c);
        //on fixe le contexte pour openWritable et open Readable
        this.context = c;
    }

    //Correction
   /* public  HeroDAO(Context c){
        DBHelper helper = new DBHelper(c,DBHelper.DB_NAME,null,DBHelper.DB_VERSION);
    }*/

    public HeroDAO openWritable(){
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public HeroDAO openReadable(){
        dbHelper = new DBHelper(context);
        db = dbHelper.getReadableDatabase();
        return this;
    }

    public void close(){
        db.close();
        dbHelper.close();
    }

    //Ajout d'un nouveau hero
    public boolean insert(Hero h){
        ContentValues cv = heroToContentValue(h);
        long id = db.insert(TABLE_HERO, null, cv);
        //l'insertion nous retourne l'id de l'élément insérer
        //on set l'id de notre objet hero courant
        h.setID_hero(id);
        //si l'id est suppérieur a 0 l'insertion a réussi
        return id > 0;
    }

    //Update d'un  hero
    public boolean update(Hero h){
        ContentValues cv = heroToContentValue(h);
        String[] params = new String[]{String.valueOf(h.getId_hero())};
        long id = db.update(TABLE_HERO, cv,COLUMN_ID+"= ?",params);
        //l'insertion nous retourne l'id de l'élément insérer
        //on set l'id de notre objet hero courant
        h.setID_hero(id);
        //si l'id est suppérieur a 0 l'insertion a réussi
        return id > 0;
    }

    public List<Hero> getAllHeroes(){
        //création d'une liste vide
        List<Hero> heroes = new ArrayList<>();
        //query de récupération de toute la table hero trié par ordre alphabétique de nickname
        Cursor allHeroesCursor = db.query(TABLE_HERO,null,null,null,null,null,COLUMN_NICKNAME);
        //si il y a au moins 1 élément on récupère la valeurs des colones
        if (allHeroesCursor.moveToFirst()){
            do {
                Hero hero = cursorToHero(allHeroesCursor);
                //ajout du hero a la liste de hero
                heroes.add(hero);
            }while(allHeroesCursor.moveToNext());//répéter la boucle jusqu'a ce qu'il y ai un suivant
        }
        //fermer le curseur
        allHeroesCursor.close();
        //retourne le tableau de heros ou un tableau vide
    return heroes;
    }

    //Recupérer un hero en fonction de son ID
    public Hero getHeroById(long id){
        //Création d'un hero vide a retourner
        Hero hero = new Hero();
        //Récupération dans un curseur du héro qui a l'id reçu en paramètre
        Cursor one_hero = db.query(TABLE_HERO,null,COLUMN_ID+"="+id,null,null,null,null);

        //si il y a un hero avec cet id , on va au début du curseur
        if(one_hero.moveToFirst()){
            hero = cursorToHero(one_hero);
        }
        //fermer le curseur
        one_hero.close();
        //on retourne le hero
        return hero;
    }

    public boolean deleteHeroById(long id){
        //Delete le hero dont l'id est passer en paramètre
        String[] params = new String[]{String.valueOf(id)};
        return db.delete(TABLE_HERO, COLUMN_ID + "= ?", params) > 0;
    }


    private Hero cursorToHero(Cursor c) {
        Long id = c.getLong(c.getColumnIndex(COLUMN_ID));
        String firstname = c.getString(c.getColumnIndex(COLUMN_FIRST_NAME));
        String lastname = c.getString(c.getColumnIndex(COLUMN_LAST_NAME));
        String nickname = c.getString(c.getColumnIndex(COLUMN_NICKNAME));
        String city = c.getString(c.getColumnIndex(COLUMN_CITY));
        String origin = c.getString(c.getColumnIndex(COLUMN_ORIGIN));
        String first_release_date = c.getString(c.getColumnIndex(COLUMN_FIRST_RELEASE_DATE));
        boolean is_masked = c.getInt(c.getColumnIndex(COLUMN_IS_MASKED) )== 1 ? true : false;
        float rating = c.getFloat(c.getColumnIndex(COLUMN_RATING));
        //création d'un hero avec la valeur récupérée
        Hero hero= new Hero(id,firstname,lastname,nickname,city,origin,first_release_date,is_masked,rating);
        return hero;
    }

    private ContentValues heroToContentValue(Hero h) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_FIRST_NAME,h.getFirstname());
        cv.put(COLUMN_LAST_NAME,h.getLastname());
        cv.put(COLUMN_NICKNAME,h.getNickname());
        cv.put(COLUMN_CITY,h.getCity());
        cv.put(COLUMN_ORIGIN,h.getOrigin());
        cv.put(COLUMN_FIRST_RELEASE_DATE, h.getFirst_release_date());
        cv.put(COLUMN_IS_MASKED,h.is_masked()? 1: 0);//opérateur ternaire pour insérer 1 quand c'est true et 0 quand c'est false car le type de la colone est integer
        cv.put(COLUMN_RATING,h.getRating());
        return cv;
    }

}
