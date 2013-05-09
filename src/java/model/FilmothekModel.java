/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author neumeister
 */
public class FilmothekModel {
    private DatabaseConnection db;

    public FilmothekModel() {
        this.db = DatabaseConnection.getInstance();
    }

    /**
     * Erzeugt neuen Benutzer-Eintrag in der Datenbank
     */
    public void createUser(UserBean user){
        try {
            String queryString = "INSERT INTO user values('?', '?', '?', '?', ?, ?, NULL);";
            PreparedStatement statement = this.db.createStatement(queryString);
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getRole());
            statement.setInt(5, user.getActiveAsInt());
            statement.setInt(6, user.getActivationKey());
            
            statement.executeQuery();
            /*
            this.db.create("INSERT INTO user values('"+user.getName()+"', '"+user.getEmail()
                    +"', '"+user.getPassword()+"', '"+user.getRole()+"', "+user.getActiveAsInt()
                    +", "+user.getActivationKey()+", NULL);");
             */
        } catch (SQLException ex) {
            Logger.getLogger(FilmothekModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Sucht einen Bentuzer aus der Datenbank heraus
     * 
     * @param die Mail des Nutzers
     * @return UserBean oder NULL falls es den User nicht gibt
     */
    public UserBean getUser(String email){
        try {
            String queryString = "SELECT * FROM user WHERE email like '?' LIMIT 1 ;";
            PreparedStatement statement = this.db.createStatement(queryString);
            statement.setString(1, email);
            /*
            String sql = "SELECT * FROM user WHERE email like '" + email + "' LIMIT 1 ;";
            ResultSet result = this.db.read(sql);
             */
            ResultSet result = statement.executeQuery();
            if(result == null){
                return null;
            }
            if(result.next()){
                return this.createUserBean(result);
            }else{
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(FilmothekModel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     * Sucht den Nutzer aus der Datenbank heraus
     * @param id die Datenbank-Id des Nutzers
     * @return UserBean oder NULL falls der User nicht vorhanden ist
     */
    public UserBean getUser(int id){
        try {
            /*
            String sql = "SELECT * FROM user WHERE id = " + id + " LIMIT 1 ;";
            ResultSet result = this.db.read(sql);
             */
            String queryString = "SELECT * FROM user WHERE id = ? LIMIT 1 ;";
            PreparedStatement statement = this.db.createStatement(queryString);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            if(result.next()){
                return this.createUserBean(result);
            }else{
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(FilmothekModel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     * Erzeugt eine Bean aus dem Resultset von User Abfragen.
     * 
     * @param result
     * @return UserBean oder NULL
     */
    private UserBean createUserBean(ResultSet result){
         try {
            //gebe null zurück, falls kein User vorhanden ist
            if (result == null || result.getFetchSize() < 0){
                return null;
            }else{
                UserBean user = new UserBean();
                        user.setId(result.getInt("id"));
                        user.setActive(result.getInt("active") == 1);
                        user.setActivationKey(result.getInt("activationKey"));
                        user.setEmail(result.getString("email"));
                        user.setName(result.getString("name"));
                        user.setPassword(result.getString("password"));
                        user.setRole(result.getString("role"));
                    return user;
            }
        } catch (SQLException ex) {
            Logger.getLogger(FilmothekModel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    

    /**
     * legt die Datenbank-Verbindung lahm
     */
    public void shutdown() {
        this.db.closeConnection();
    }

    public ArrayList<FilmBean> getFilms() {
        ResultSet rs = this.db.read("select * from filme");
        return this.createListOfFilmBeans(rs);
    }

    private ArrayList<FilmBean> createListOfFilmBeans(ResultSet rs) {
        try {
            if (rs == null || rs.getFetchSize() < 0) {
                return null;
            } else {
                ArrayList<FilmBean> films = new ArrayList<FilmBean>();
                FilmBean film = new FilmBean();
                while (rs.next()) {
                    film = this.createFilmBean(rs);
                    films.add(film);
                }
                return films;
            }
        } catch (SQLException ex) {
            Logger.getLogger(FilmothekModel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
       
    }

    /**
     * Erzeugt eine einzelne Filmbean
     *
     * Achtung! Methode ruft nicht selber next() auf dem Resultset auf !!
     * 
     * @param rs
     * @return FilmBean
     */
    private FilmBean createFilmBean(ResultSet rs) {
        try {
            FilmBean film = new FilmBean();
            film.setBorrowerID(rs.getInt("borowerID"));
            film.setCountry(rs.getString("country"));
            film.setDirector(rs.getString("director"));
            film.setId(rs.getInt("id"));
            film.setImageURL(rs.getString("imageUrl"));
            film.setName(rs.getString("name"));
            film.setReleaseYear(rs.getInt("releaseYear"));
            film.setRuntime(rs.getInt("runtime"));
            film.setTrailerURL(rs.getString("trailerUrl"));
            film.setUrl(rs.getString("url"));
            film.setWriter(rs.getString("writer"));
            film.setReturnDate(rs.getDate("returnDate"));
            film.setRenewals(rs.getInt("renewal"));
            return film;
        } catch (SQLException ex) {
            Logger.getLogger(FilmothekModel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     * Verlängert den Film eines Benutzers um eine Woche
     *
     * @param filmID
     * @param userID
     */
    public void renewFilm(int filmID, int userID) {
        FilmBean film = this.getFilm(filmID);
        if(film.getBorrowerID() == userID && film.getRenewals() < 2){
            film.setRenewals(film.getRenewals() + 1);
            film.setReturnDate(new Date(film.getReturnDate().getTime()+(1000*60*60*24*7)));
            this.updateFilm(film);
        }
    }

    /**
     * Holt einen Film aus der Datenbank
     * 
     * @param filmID
     * @return
     */
    public FilmBean getFilm(int filmID) {
        try {
            //ResultSet rs = this.db.read("select * from filme where id = " + filmID + " limit 1;");
            String queryString = "select * from filme where id = ? limit 1;";
            PreparedStatement statement = this.db.createStatement(queryString);
            statement.setInt(1, filmID);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return this.createFilmBean(rs);
            } else {
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(FilmothekModel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    /**
     * aktualisiet einen Film in der Datenbank
     *
     * @param film
     */
    private void updateFilm(FilmBean film) {
        try {
            java.sql.Date date = new java.sql.Date(film.getReturnDate().getTime());
            String queryString = "update filme set borowerID = ?, returnDate = '?', "
                    + "renewal = ?  where id = ? ;";;
            PreparedStatement statement = this.db.createStatement(queryString);
            statement.setInt(1, film.getBorrowerID());
            statement.setDate(2, date);
            statement.setInt(3, film.getRenewals());
            statement.setInt(4, film.getId());
            statement.executeUpdate();
            /*
            String sql = "update filme set borowerID = "+film.getBorrowerID()+", "+
                    "returnDate = '" + date.toString()+"', "+
                    "renewal = " + film.getRenewals()
                    +"  where id = "+film.getId()+" ;";
            this.db.update(sql);
             */
        } catch (SQLException ex) {
            Logger.getLogger(FilmothekModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * update einen Film-Datensatz so dass er als nicht mehr ausgliehen verzeichnet wird
     *
     * @param filmID
     */
    public void returnFilm(int filmID) {
        FilmBean film = this.getFilm(filmID);
        // setzte Ausleiher ID auf 0 um anzuzeigen, dass es keinen Ausleiher mehr gibt
        film.setBorrowerID(0);
        // speichere die veränderte Bean in der DAtenbank
        this.updateFilm(film);
    }

    public ArrayList<UserBean> getUsers() {
        try {
            ArrayList<UserBean> users = new ArrayList<UserBean>();
            String sql = "SELECT * from user;";
            ResultSet rs = this.db.read(sql);
            while (rs.next()) {
                users.add(this.createUserBean(rs));
            }
            return users;
        } catch (SQLException ex) {
            Logger.getLogger(FilmothekModel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
    }

    /**
     * Markiere einen Film in der DAtenbank als Ausgliehen an den gegebenen Nutzer
     * @param filmID
     * @param userID
     */
    public void checkoutFilm(int filmID, int userID) {
        FilmBean film = this.getFilm(filmID);
        // notiere die ID des Nutzers, der den Film ausgeliehen bekommt.
        film.setBorrowerID(userID);
        //setzte das Rückgabedatum auf eine Woche in der Zukunft
        film.setReturnDate(new Date(System.currentTimeMillis()+ (7 * 24 * 60 * 60 * 1000)));
        // update die Datenbank:
        this.updateFilm(film);
    }


}
