/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dispatchers;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.servlet.http.HttpSession;
/**
 *
 * @author cauth
 */
public class ContinueDispatcher  implements IDispatcher{
     @Override
    public String execute(HttpServletRequest request, HttpSession session) {
        String nextPage = "/jsp/titles.jsp";
        return nextPage;
    }
    
}
