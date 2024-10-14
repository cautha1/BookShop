/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dispatchers;
//import model.Book;
import model.CartItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Books;
/**
 *
 * @author cauth
 */
public class AddToCartDispatcher implements IDispatcher {
    @Override
    public String execute(HttpServletRequest request, HttpSession session) {
        //HttpSession session;
        
        String nextPage = "/jsp/titles.jsp";

            Map cart = (Map) session.getAttribute("cart");
            String[] selectedBooks = request.getParameterValues("add");

            if (cart == null) {
                cart = new HashMap();
                for (int i = 0; i < selectedBooks.length; i++) {
                    String isbn = selectedBooks[i];
                    int quantity = Integer.parseInt(request.getParameter(isbn));
                    Books book = this.getBookFromList(isbn, session);
                    CartItem item = new CartItem(book);
                    item.setQuantity(quantity);
                    cart.put(isbn, item);
                }
                session.setAttribute("cart", cart);
            } else {
                for (int i = 0; i < selectedBooks.length; i++) {
                    String isbn = selectedBooks[i];
                    int quantity = Integer.parseInt(request.getParameter(isbn));
                    if (cart.containsKey(isbn)) {
                        CartItem item = (CartItem) cart.get(isbn);
                        item.setQuantity(quantity);
                    } else {
                        Books book = this.getBookFromList(isbn, session);
                        CartItem item = new CartItem(book);
                        item.setQuantity(quantity);
                        cart.put(isbn, item);
                    }
                }
            }
            return nextPage;
    }
    private Books getBookFromList(String isbn, HttpSession session) {
        List list = (List) session.getAttribute("books");
        Books aBook = null;
        for (int i = 0; i < list.size(); i++) {
            aBook = (Books) list.get(i);
            if (isbn.equals(aBook.getIsbn())) {
                break;
            }
        }
        return aBook;
    }
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
//        session = request.getSession();
//        Map<String, CartItem> cart = (Map<String, CartItem>) session.getAttribute("cart");
//        String[] selectedBooks = request.getParameterValues("add");
//        String nextPage = "/jsp/titles.jsp";
//
//        if (cart == null) {
//            cart = new HashMap();
//            session.setAttribute("cart", cart);
//        }
//
//        if (selectedBooks != null) {
//            for (String isbn : selectedBooks) {
//                int quantity = Integer.parseInt(request.getParameter(isbn));
//                Books book = (Books) session.getAttribute("books");
//                CartItem item = new CartItem(book);
//                item.setQuantity(quantity);
//                cart.put(isbn, item);
//            }
//        }
//        return nextPage;
//    }

//    public String execute(HttpServletRequest request, String datasource) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
}

