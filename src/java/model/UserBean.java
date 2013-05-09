/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.io.Serializable;

/**
 * Die Java-Bean für die User-Daten
 *
 * @author neumeister
 */
public class UserBean implements Serializable {

    public static final String ROLE_ADMIN = "admin";

    public static final String ROLE_USER =  "user";
    
    private String name;

    private int id;

    private int activationKey;

    private String email;

    /** das MD5 gehashte Passwort */
    private String password;

    private String role;

    private Boolean active;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
       this.id = id;
    }

    public int getActivationKey() {
        return activationKey;
    }

    public void setActivationKey(int activationKey) {
        this.activationKey = activationKey;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setActiveAsInt(int active){
        if(active == 0){
            this.active = false;
        }else{
            this.active = true;
        }
    }

    public int getActiveAsInt(){
        if(this.active){
            return 1;
        }else{
            return 0;
        }
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
