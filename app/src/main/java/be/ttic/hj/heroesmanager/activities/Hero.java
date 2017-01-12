package be.ttic.hj.heroesmanager.activities;

/**
 * Created by Jimmy on 08/12/2016.
 */

public class Hero {
    private String firstname;
    private String lastname;
    private String nickname;
    private String city;
    private String origin;
    private String first_release_date;
    private boolean is_masked;
    private float rating;
    private long id_hero;

    public Hero(){}

    public Hero(String firstname, String lastname, String nickname, String city, String origin, String first_release_date, boolean is_masked, float rating) {
        setFirstname(firstname);
        setLastname(lastname);
        setNickname(nickname);
        setCity(city);
        setOrigin(origin);
        setFirst_release_date(first_release_date);
        setIs_masked(is_masked);
        setRating(rating);
    }

    public Hero(long id_hero, String firstname, String lastname, String nickname, String city, String origin, String first_release_date, boolean is_masked, float rating) {
       this(firstname,lastname,nickname,city,origin,first_release_date,is_masked,rating);
        setID_hero(id_hero);

    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getFirst_release_date() {
        return first_release_date;
    }

    public void setFirst_release_date(String first_release_date) {
        this.first_release_date = first_release_date;
    }

    public boolean is_masked() {
        return is_masked;
    }

    public void setIs_masked(boolean is_masked) {
        this.is_masked = is_masked;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public long getId_hero() {
        return id_hero;
    }

    public void setID_hero(long id){
        this.id_hero =  id;
    }

    @Override
    public String toString() {
        return "hero{" +
                " id_hero= " + id_hero +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", nickname='" + nickname + '\'' +
                ", city='" + city + '\'' +
                ", origin='" + origin + '\'' +
                ", first_release_date='" + first_release_date + '\'' +
                ", is_masked=" + is_masked +
                ", rating=" + rating +
                '}';
    }
}
