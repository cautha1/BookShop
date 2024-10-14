/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dispatchers;

/**
 *
 * @author cauth
 */
import java.util.Iterator;
import model.CartItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Set;

public class UpdateCartDispatcher implements IDispatcher {

    @Override
   
    public String execute(HttpServletRequest request, HttpSession session) {
            Map cart = null;
            CartItem item = null;
            String isbn = null;
            String nextPage = "/jsp/cart.jsp";
            cart = (Map) session.getAttribute("cart");
            String[] booksToRemove = request.getParameterValues("remove");
            if (booksToRemove != null) {
                for (int i = 0; i < booksToRemove.length; i++) {
                    cart.remove(booksToRemove[i]);
                }
            }
            Set entries = cart.entrySet();
            Iterator iter = entries.iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                isbn = (String) entry.getKey();
                item = (CartItem) entry.getValue();
                int quantity = Integer.parseInt((request.getParameter(isbn)));
                item.updateQuantity(quantity);
            }
            return nextPage;
    }
}