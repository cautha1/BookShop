/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dispatchers;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import model.Books;
import utility.AdmitBookStoreDAO;
/**
 *
 * @author cauth
 */
public  class TitleDispatcher implements IDispatcher {
    private EntityManagerFactory emf;
    private EntityManager em;
    @Override
    public String execute(HttpServletRequest request,HttpSession session) {
        emf = Persistence.createEntityManagerFactory("BookShopPU");
        em = emf.createEntityManager();
        List book = null;
        String nextPage = "/jsp/error.jsp";
        session = request.getSession();
        try {
            book = em.createNamedQuery("Books.findAll", Books.class).getResultList();
            session.setAttribute("books", book);
            emf.close();
            nextPage = "/jsp/titles.jsp";

        } catch (Exception ex) {
            request.setAttribute("result", ex.getMessage());
            nextPage = "/jsp/error.jsp";
        }
        return nextPage;
    }
        
//        try {
//            AdmitBookStoreDAO dao = new AdmitBookStoreDAO();
//            HttpSession session = request.getSession(true);
//            List book = dao.getAllBooks();
//            if (book != null) {
//                session.setAttribute("books", book);
//                nextPage = "/jsp/titles.jsp";
//            }
//        } catch (Exception ex) {
//            request.setAttribute("result", ex.getMessage());
//        }
//        return nextPage;
    

 
}