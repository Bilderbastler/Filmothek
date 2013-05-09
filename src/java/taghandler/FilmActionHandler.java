/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package taghandler;

import java.util.ArrayList;
import java.util.Iterator;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import model.FilmBean;
import model.FilmothekModel;
import model.UserBean;

/**
 *
 * Dieser Tag erzeugt einen Button-artigen Link für die verschiedenen Aktionen,
 * die man auf Filmen ausführen kann: verlängern, verleihen und zurückgeben
 * @author neumeister
 */
public class FilmActionHandler extends SimpleTagSupport {
    private UserBean user;
    private FilmBean film;
    private String baseURL;

    /**
     * Called by the container to invoke this tag. 
     * The implementation of this method is provided by the tag library developer,
     * and handles all tag processing, body iteration, etc.
     */
    @Override
    public void doTag() throws JspException {
        JspWriter out = getJspContext().getOut();
        FilmothekModel model = new FilmothekModel();

        try {
            // bearbiete Optionen für normale Nutzer
            if(this.film.getBorrowerID() == this.user.getId()){
                // ist keine Verlängerung mehr möglich, zeige keinen Link an
                if(this.film.getRenewals() >= 2){
                    out.println("<b>maximal verlängert</b>");

                }else {
                    out.println(this.button("/renew?id="+this.film.getId(), "Verlängern"));
                }

            // zeige Optionen für den Admin an:
            }else if(this.user.getRole().equals(UserBean.ROLE_ADMIN)){
                // ist der Film an einen User ausgeliehen, erzeuge einen Zurückgeben-Button
                if(film.getBorrowerID() > 0){
                    out.println(this.button("/return?id="+this.film.getId(), "Zurück"));

                // ansonsten ein Ausleih-Formular
                }else{
                    out.println("<form action='"+this.baseURL+"/checkout'>"
                            + "<input type='hidden' name='filmID' value='"+this.film.getId()+"' />"
                            + this.createUserList()
                            + "<input type='submit' value='Ausleihen' />"
                            + "</form>");
                }


            // sonst zeige, das der Film verfügbar ist
            }else if (film.getBorrowerID() < 0){
                out.println("<b>verfügbar</b>");
            }
           

        } catch (java.io.IOException ex) {
            throw new JspException("Error in FilmActionHandler tag", ex);
        }
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public void setFilm(FilmBean film) {
        this.film = film;
    }

    public void setBaseURL(String baseURL) {
        this.baseURL = baseURL;
    }

    /**
     * Erzeugt einen String mit einem HTML-Link für den Aktions-Button
     * @param urlString der Aktions-STring
     * @param label die Beschriftung des Buttons
     * @return anchor-Tag
     */
    private String button(String urlString, String label){
        return "<a class='action' href='"+this.baseURL+urlString+"' />"+label+"</a>";
    }

    /**
     * Erzeugt eine HTML-Liste mit allen Benutzern der Filmothek.
     * 
     * @return
     */
    private String createUserList() {
        FilmothekModel model = new FilmothekModel();
        ArrayList<UserBean> users = model.getUsers();
        String userList = "<select name='userID' size='1'>";
        for (Iterator<UserBean> it = users.iterator(); it.hasNext();) {
            UserBean userBean = it.next();
            if(userBean.getRole().equals(UserBean.ROLE_USER)){
                userList += " <option value='"+userBean.getId()+"'>"+userBean.getName()+"</option>";
            }
        }
        userList += "</select>";
        return userList;

    }

}
