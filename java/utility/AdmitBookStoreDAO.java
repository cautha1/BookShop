package utility;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.*;
import java.sql.*;
//import model.Book;
import model.Books;

public class AdmitBookStoreDAO {
     private Connection con;
    private EntityManagerFactory emf;
    private EntityManager em;
 
   
    private static final String PERSISTENCE_UNIT_NAME = "BookShopPU";
    private static EntityManagerFactory factory;

    public AdmitBookStoreDAO() {
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
       
    }

    public List<Books> getAllBooks() {
        EntityManager em = factory.createEntityManager();
        List<Books> book = em.createQuery("SELECT b FROM Books b", Books.class).getResultList();
        em.close();
        return book;
    }
        
    
  
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
//        try {
//            // Load the Driver class file
//            Class.forName("org.apache.derby.jdbc.ClientDriver");
//            System.err.println("Getting Connection!");
//            con = DriverManager.getConnection("jdbc:derby://localhost:1527/BooksDB",
//                    "user4", "password");
//
//            if (con != null) {
//                System.err.println("Got Connection!");
//            }// end if
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }// end catch
//
//    }// end of Contructor
//
//    // get info on all courses taught by a lecturer
//    public List getAllBooks() throws SQLException {
//        System.out.println("inside getAllBooks");
//        Statement statement = con.createStatement();
//        ResultSet rs = null;
//        List list = new ArrayList();
//
//        rs = statement.executeQuery("SELECT * FROM USER4.BOOKS ");
//        String isbn = "";
//        String title = "";
//        String author = "";
//        double price = 0.00;
//        while (rs.next()) {
//            System.out.println("rs has records");
//            isbn = rs.getString(1);
//            title = rs.getString(2);
//            author = rs.getString(3);
//            price = rs.getDouble(4);
//            list.add(new Book(isbn, title, author, price));
//        }
//        return list;
//    } // end getAllBooks

}
