/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.FilmBean;
import model.FilmothekModel;
import model.UserBean;

/**
 *
 * @author neumeister
 */
public class ClientControllerServlet extends HttpServlet {
    
    private FilmothekModel model;
   

    @Override
    /**
     * Quasi-Konstruktor-Methode von HttpServlet. Initialisiert die Datenbank
     * verbindung
     */
    public void init(){
        this.model = (FilmothekModel) this.getServletContext().getAttribute("database");
    }

    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        
        String userPath = request.getServletPath();

        if(userPath.equals("register")){
            // hier sind keie Datenbankoperationen nötig
        }else if(userPath.equals("/activate")){
            String activate = request.getParameter("activate");
            String id = request.getParameter("id");
            userPath = "/login";
        }
        // ist der Nutzer nicht angemeldet, schicke ihn zur Login-Seite
        else if(userPath.equals("/login") || session == null
                ||session.getAttribute("user") == null){
                userPath ="/login";
        }
        // verarbeite Zugriff auf die Filmlsite
        if(userPath.equals("/clientFilmListe")){
            // zeige die Admin-Liste, falls der User Admin-Rechte hat.
            UserBean user = (UserBean) session.getAttribute("user");
            if(user.getRole().equals(UserBean.ROLE_ADMIN)){
                response.sendRedirect("/~j81/adminFilmListe");
                return;
            }
            userPath = "/filmListe";
            this.clientFilmListe(request, response);
        }else if (userPath.equals("/renew")){
            this.renewal(response, request);
            response.sendRedirect("/~j81/clientFilmListe");
            return;
        }

        // lade die JSP, die das Ergebnis des Requests darstellt.
        String url = "/WEB-INF/view" + userPath + ".jsp";
        request.getRequestDispatcher(url).forward(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        String userPath = request.getServletPath();
        UserBean userRole;

        if(userPath.equals("/register")){
            register(request, response);
        }
        // ist der Nutzer nicht angemeldet, schicke ihn zur Login-Seite
        else if(userPath.equals("/login") || session == null
                || ! session.getAttribute("loggedIn").equals("true")){
                userPath = "/login";
                // versuche dich einzu loggen
                if(login(response, request)){
                    userRole = (UserBean) session.getAttribute("user");
                    // entscheide, ob die Seite für den Client oder den Admin angezeigt werden soll
                    if(userRole.getRole().equals(UserBean.ROLE_ADMIN)){
                        response.sendRedirect("/~j81/adminFilmListe");
                        return;
                    }else{
                        response.sendRedirect("/~j81/clientFilmListe");
                        return;
                    }
                }else{
                    userPath = "/login";
                }
        }
        // behandle die Abmeldung des Users
        else if(userPath.equals("/logout")){
            this.logout(request);
            response.sendRedirect("/~j81/index.jsp");
            return;
        }else if(userPath.equals("/clientFilmListe")){
             userPath = "/filmListe";
        }


        // lade die JSP, die das Ergebnis der Aktoin darstellt.
        String url = "/WEB-INF/view" + userPath + ".jsp";
        request.getRequestDispatcher(url).forward(request, response);

    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }

    /*
     * meldet den Client an. Schlägt der Login-Prozess fehl, gibt die Methode
     * false zurück und schreibt eine entsprechende Fehlermeldung in das Request
     * Attribut "loginError"
     */
    private boolean login(HttpServletResponse response, HttpServletRequest request) {
        String userMail = request.getParameter("email");
        String userPassword =  request.getParameter("password");
        UserBean user = this.model.getUser(userMail);
        HttpSession session;
        if(user != null){
            if (! user.getActive()){
                request.setAttribute("loginError", "Der Benutzer ist noch nicht aktiviert worden.");
                request.setAttribute("email", userMail);
                return false;
            }
            if(user.getPassword().equalsIgnoreCase(userPassword)){
                session = request.getSession();
                session.setAttribute("user", user);
                session.setAttribute("loggedIn", "true");
                return true;
            }
        }
        request.setAttribute("loginError", "Passwort und Benutzername stimmen nicht überein");
        request.setAttribute("email", userMail);
        return false;
    }

    /**
     * Meldet den aktuellen Benutzer ab.
     * @param request
     */
    private void logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        session.removeAttribute("loggedIn");
    }

    /**
     * Behandelt den Login-Versuch
     * 
     * @param request
     * @param response
     */
    private void register(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();

        
        // Liste aller Fehlermeldungen
        ArrayList<String> errors = new ArrayList<String>();

        // Teste, ob die Mail gültig ist:
        String email = request.getParameter("email");
        if(email == null || email.length() == 0){
            errors.add("Bitte das E-Mail-Feld ausfüllen");
        }else if(! email.matches("[\\w.]*@haw-hamburg.de")){
            errors.add("Die E-Mail eingetragene E-Mail ist nicht gültig");
        }

        // ist ein Name angeben worden?
        String name = request.getParameter("name");
        if(name == null || name.length() == 0){
            errors.add("Bitte das Name-Feld ausfüllen");
        }

        // existiert für die angegebene Adresse schon ein Nutzer?
        if (this.model.getUser(email) != null){
            errors.add("Für diese Mail-Adresse besteht bereits ein Konto");
        }
        
        String password = request.getParameter("password");

        // prüfe ob das Passwort lang genug ist
        if(password.length() < 6){
            errors.add("Das Passwort muss mindestens 6 Zeichen lang sein");
        }
        String password2 = request.getParameter("password2");

        // prüfe, ob das Passwort das selbe ist wie in der Wiederholung des Passworts
        if(! password.equals(password2)){
            errors.add("Die Passwörter stimmen nicht überein");
        }

        
        // schicke die Fehlerliste an die JSP
        request.setAttribute("errors", errors);

        if(errors.isEmpty()){
            // sagt dem register jsp, dass die registrierung erfolgreich war
            request.setAttribute("registerStatus", "mail send");

            // erzeuge aktivierungs-Key
            int activationKey = (int) (Math.random() * 100000);

            // erzeuge neue User Bean (inaktiv) und schreibe sie in die Datenbank
            UserBean user = new UserBean();
            user.setActivationKey(activationKey);
     //       user.setActive(false);
            user.setActive(true); // in dieser Version bedarf es keiner Aktivierung des Accounts
            user.setEmail(email);
            user.setName(name);
            user.setPassword(password);
            user.setRole(UserBean.ROLE_USER);
            this.model.createUser(user);
            int id = this.model.getUser(email).getId();

            /*
             * Email-Versand entfält aufgrund fehlender Anbindung an Mail-Server …
             * … in dieser Version …
             */

            // finde den Hostnamen heraus:
            String url = request.getRequestURL().toString();
            String uri = request.getRequestURI();
            String host = url.substring(0, url.indexOf(uri));
            String mail = host+"/mail.php";
            String query = "?mail="+mail+"&name="+name+"&key="+activationKey+"&url="+host+"&id="+id;
            //schickt die Mail an den Nutzer:
        }else{
            // gebe die schon eingebenen Nutzerdaten wieder an das JSP zurück, damit der
            // User sie nicht erneut eintippen muss
            request.setAttribute("name", name);
            request.setAttribute("email", email);
        }

    }

    /**
     * Bereitet die nötigen Schritte zur Darstellung der Filmliste für den Client
     * damit die JSP sie darstellen kann.
     *
     * @param request
     * @param response
     */
    private void clientFilmListe(HttpServletRequest request, HttpServletResponse response) {
        ArrayList<FilmBean> films = this.model.getFilms();
        
        request.setAttribute("films", films);
    }

    /**
     * versuche, den Film um eine Woche zu verlängern.
     * @param response
     * @param request
     */
    private void renewal(HttpServletResponse response, HttpServletRequest request) {
        UserBean user = (UserBean) request.getSession().getAttribute("user");
        int userID = user.getId();
        int filmID =  Integer.parseInt(request.getParameter("id"));
        this.model.renewFilm(filmID, userID);

    }


}
