/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Diese Klasse realisiert den Datenbankzugriff über JDBC. Sie ist als Singleton-
 * Klasse konzipiert, es existiert also immer nur eine Instanz der Klasse in der
 * gesamten Anwendung
 *
 * @author neumeister
 */
public class DatabaseConnection {
    
    private static DatabaseConnection instance;

    private Connection connection;
    private Statement statement;

    /**
     * Liefert eine Instanz der Datenbank Verbindungsklasse
     *
     * @return die Singleton-Instanz von DatabaseConnection
     */
    public static synchronized DatabaseConnection getInstance(){
        if (DatabaseConnection.instance == null){
            DatabaseConnection.instance = new DatabaseConnection();
        }
        return DatabaseConnection.instance;
    }
    /**
     * privater Konstruktor
     */
    private DatabaseConnection() {
        // Eigenschaften
  
        String password = "4xd3";
      String user = "j81";
//        String password = "";
 //       String user = "root";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/j81", user, password);
            statement = connection.createStatement();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Schließt die Datenbank-Verbindung
     */
    public void closeConnection(){
        try {
            this.clone();
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Liefert ein Prparted Statement für eine sicherere Datenbank-Interaktion als mit den CRUD Methoden.
     * @param statement Die Abfrage mit ? als Platzhalter für Argumente 
     * @return oder NULL
     */
    public PreparedStatement createStatement(String statement){
        PreparedStatement con = null;
        try {
            con = this.connection.prepareStatement(statement);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }
 ////////////////////////////////////////////////////////////////////////////////////////////////
 //         C. R. U. D.
 ////////////////////////////////////////////////////////////////////////////////////////////////

   /**
    * schreibe in die Datenbank
    *
    * @param query die SQL-Befehle
    */
    public void create(String query){
        try {
            this.statement.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * lese aus der Datenbank
     *
     * @param query  die SQL-Befehle
     * @return die Ergebnisse der Abfrage als Result-Set oder NULL
     */
    public ResultSet read(String query){
        try {
            ResultSet result = this.statement.executeQuery(query);
            result.last();
            int rows = result.getRow();
            if(rows < 1){
                return null;
            }
            result.beforeFirst();
            return result;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, ""
                    + "SQL-String: "+query, ex);
            return null;
        }
    }

    /**
     * Erneuere den Inhalt der Datenbank
     *
     * @param query  die SQL-Befehle
     */
    public void update(String query){
        try {
            this.statement.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * lösche Einträge aus der Datenbank
     * @param query  die SQL-Befehle
     */
    public void delete(String query){
        try {
            this.statement.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    


   

}
