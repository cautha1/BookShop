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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;
import model.CartItem;

public class ViewCartDispatcher implements IDispatcher {

    @Override
    public String execute(HttpServletRequest request, HttpSession session) {
       // HttpSession session;
        session = request.getSession();
        Map<String, CartItem> cart = (Map<String, CartItem>) session.getAttribute("cart");
        String nextPage = "/jsp/cart.jsp";

        if (cart == null || cart.isEmpty()) {
            nextPage = "/jsp/titles.jsp";
        }

        return nextPage;
    }
}