package controller;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import dispatchers.*;
//import model.Book;
import model.Books;
import model.CartItem;
import utility.AdmitBookStoreDAO;
import dispatchers.TitleDispatcher;
import dispatchers.AddToCartDispatcher;
import dispatchers.CheckoutDispatcher;
import dispatchers.ContinueDispatcher;
import dispatchers.UpdateCartDispatcher;
import dispatchers.ViewCartDispatcher;
import dispatchers.IDispatcher;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class FrontController extends HttpServlet {
    
    @PersistenceContext(unitName = "BookShopPU")
    private EntityManager em;
    @Resource
    private javax.transaction.UserTransaction utx;
    


   // private final HashMap actions = new HashMap();
    private final HashMap<String, IDispatcher> actions = new HashMap(); // Use generics

    
  
   // private final HashMap<String, String> actions = new HashMap();
    //Initialize global variables
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        
        actions.put("view_titles", new TitleDispatcher());
        actions.put("add_to_cart", new AddToCartDispatcher());
        actions.put("checkout", new CheckoutDispatcher());
        actions.put("continue", new ContinueDispatcher());
        actions.put("update_cart", new UpdateCartDispatcher());
        actions.put("view_cart", new ViewCartDispatcher());
    }

    //Process the HTTP Get request
     @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.err.println("doGet()");
        doPost(request, response);

    }

    //Process the HTTP Post request
     @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        String requestedAction = request.getParameter("action");
        HttpSession session = request.getSession();

        if (requestedAction == null) {
            requestedAction = "view_titles";
        }

        IDispatcher action = actions.get(requestedAction);
        String nextPage = (action != null) ? action.execute(request, session) : "/jsp/error.jsp";
        dispatch(request, response, nextPage);
     
        
    }
    

          

    private void dispatch(HttpServletRequest request, HttpServletResponse response, String page) throws ServletException,
            IOException {
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }

    //Get Servlet information
     @Override
    public String getServletInfo() {
        return "controller.FrontController Information";
    }

    public void persist(Object object) {
        try {
            utx.begin();
            em.persist(object);
            utx.commit();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            throw new RuntimeException(e);
        }
    }
    
    protected Books find(String isbn) {
        try {
            utx.begin();
            Books b = (Books) em.find(Books.class, isbn);
            utx.commit();
            return b;
        } catch (Exception ex) {
            Logger.getLogger(FrontController.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex);
        }

    }
    
    protected void edit(String isbn, String title) {
        try {
            utx.begin();
            Books b = em.find(Books.class, isbn);
            b.setTitle(title);
            //em.flush();
            utx.commit();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            throw new RuntimeException(e);
        }
    }


}
