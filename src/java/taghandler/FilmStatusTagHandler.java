/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package taghandler;

import java.text.DateFormat;
import java.util.Locale;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import model.FilmBean;
import model.FilmothekModel;
import model.UserBean;

/**
 * Diese Klasse errechnet Rückgabe-Daten
 * 
 * @author neumeister
 */
public class FilmStatusTagHandler extends SimpleTagSupport {
    private UserBean user;
    private FilmBean film;
    private DateFormat df;

    /**
     * Called by the container to invoke this tag. 
     * The implementation of this method is provided by the tag library developer,
     * and handles all tag processing, body iteration, etc.
     */
    @Override
    public void doTag() throws JspException {
        JspWriter out = getJspContext().getOut();
        // sorge für Verbindung zur Datenbank
        FilmothekModel model = new FilmothekModel();
        UserBean borrower;

        try {

            // gibt das Rückgabedatum aus, falls der Film ausgeliehen ist.
            if(this.film.getBorrowerID() > 0){
                df = DateFormat.getDateInstance(DateFormat.LONG, Locale.GERMANY);
                out.println("Rückgabe: "+df.format(film.getReturnDate()));

                // ist der Benutzer der Admin, zeige ihm einen Mail-Link zum Ausleihenden User
                if(this.user.getRole().equals(UserBean.ROLE_ADMIN)){
                    out.println("<br />");
                    borrower = model.getUser(film.getBorrowerID());
                    out.println("Verliehen an: <a href='mailto:"+borrower.getEmail()+"'>"+borrower.getName()+"</a>");
                }
            }

            
        } catch (java.io.IOException ex) {
            throw new JspException("Error in FilmStatusTagHandler tag", ex);
        }
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public void setFilm(FilmBean film) {
        this.film = film;
    }

}
