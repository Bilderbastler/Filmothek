/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import java.io.IOException;
import java.util.ArrayList;
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
public class AminControllerServlet extends HttpServlet {
    
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
        // ist der Nutzer nicht angemeldet, schicke ihn zur Login-Seite
        if(session == null ||session.getAttribute("user") == null){
               response.sendRedirect("/~j81/login");
               return;
        }
        // prüfe, ob der User überhaupt Admin-Rechte hat
        UserBean user = (UserBean) session.getAttribute("user");
        if(! user.getRole().equals(UserBean.ROLE_ADMIN)){
            response.sendRedirect("/~j81/clientFilmListe");
            return;
        }
        // zeige die Filmliste für den Admin
        else if(userPath.equals("/adminFilmListe")){
             this.adminFilmListe(request, response);
             userPath = "/filmListe";

        // markiere einen Film als zurückgekommen
        }else if(userPath.equals("/return")){
            this.returnFilm(request, response);
            response.sendRedirect("/~j81/adminFilmListe");
            return;
        }else if(userPath.equals("/checkout")){
            this.checkout(request, response);
            response.sendRedirect("/~j81/adminFilmListe");
            return;
        }


        // lade die JSP, die das Ergebnis der Aktoin darstellt.
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
        // ist der Nutzer nicht angemeldet, schicke ihn zur Login-Seite
        if(session == null ||session.getAttribute("user") == null){
               response.sendRedirect("/~j81/login");
               return;
        }
        // prüfe, ob der User überhaupt Admin-Rechte hat
        UserBean user = (UserBean) session.getAttribute("user");
        if(! user.getRole().equals(UserBean.ROLE_ADMIN)){
            response.sendRedirect("/~j81/clientFilmListe");
            return;
        }
        if(userPath.equals("/adminFilmListe")){
             userPath = "/filmListe";
             this.adminFilmListe(request, response);
        }


         // lade die JSP, die das Ergebnis der Aktoin darstellt.
        String url = "/WEB-INF/view" + userPath + ".jsp";
        request.getRequestDispatcher(url).forward(request, response);
    }


      /**
     * Bereitet die nötigen Schritte zur Darstellung der Filmliste für den Client
     * damit die JSP sie darstellen kann.
     *
     * @param request
     * @param response
     */
    private void adminFilmListe(HttpServletRequest request, HttpServletResponse response) {
        ArrayList<FilmBean> films = this.model.getFilms();

        request.setAttribute("films", films);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    /**
     * führt die Aktion des Film-Zurückgebens aus
     * @param request
     * @param response
     */
    private void returnFilm(HttpServletRequest request, HttpServletResponse response) {
        int filmID = Integer.parseInt(request.getParameter("id"));
        this.model.returnFilm(filmID);
    }

    private void checkout(HttpServletRequest request, HttpServletResponse response) {
        int filmID = Integer.parseInt(request.getParameter("filmID"));
        int userID = Integer.parseInt(request.getParameter("userID"));
        this.model.checkoutFilm(filmID, userID);
    }

}
