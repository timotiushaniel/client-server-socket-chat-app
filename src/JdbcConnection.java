import java.sql.*;
import java.util.ArrayList;

public class JdbcConnection {
    private static int port = 3306;
    private static String url = "localhost";
    private static String database = "sakila";

    public static ArrayList<Option1> LoadOption1(String genre) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Option1> list = new ArrayList<Option1>();

        try {
            try {
                Class.forName("org.mariadb.jdbc.Driver").newInstance();
                conn = DriverManager.getConnection("jdbc:mysql://"+url+":"+port+"/"+database+"?serverTimezone=UTC",
                        "user", "userpass");
                System.out.println("<< Option 1 has run >>");
                String query = "SELECT film.title AS title, film_category.category_id AS category_id, category.name AS category_name " +
                        "FROM film, film_category, category " +
                        "WHERE film.film_id = film_category.film_id " +
                        "AND category.category_id = film_category.category_id " +
                        "AND film_category.category_id = ? ORDER BY rand() " +
                        "LIMIT 10";
                ps = conn.prepareStatement(query);
                ps.setString(1,genre);
                rs = ps.executeQuery();
                while(rs.next()){
                    String title = rs.getString("title");
                    String category_id = rs.getString("category_id");
                    String category_name = rs.getString("category_name");

                    list.add(new Option1(title, category_id,category_name));
                }
            }
            finally {
                if(rs != null)
                    rs.close();

                if(ps != null)
                    ps.close();

                if(conn != null)
                    conn.close();
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public static ArrayList<Option2> LoadOption2(String release_year){
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rst = null;
        ArrayList<Option2> list2 = new ArrayList<Option2>();

        try {
            try {
                Class.forName("org.mariadb.jdbc.Driver").newInstance();
                conn = DriverManager.getConnection("jdbc:mysql://"+url+":"+port+"/"+database+"?serverTimezone=UTC",
                        "user", "userpass");
                System.out.println("<< Option 2 has run >>");
                String querry2= "SELECT title AS title, release_year AS release_year " +
                        "FROM film WHERE release_year = ? " +
                        "ORDER BY rand() LIMIT 5";
                pstm = conn.prepareStatement(querry2);
                pstm.setString(1, release_year);
                rst = pstm.executeQuery();

                while(rst.next()){
                    String title = rst.getString("title");
                    String releaseYear = rst.getString("release_year");

                    list2.add(new Option2(title, releaseYear));
                }
            }
            finally {
                if(rst != null)
                    rst.close();

                if(pstm != null)
                    pstm.close();

                if(conn != null)
                    conn.close();
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return list2;
    }

    public static ArrayList<Option3> LoadOption3(String actorFirstName){
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rst = null;
        ArrayList<Option3> list3 = new ArrayList<Option3>();

        try {
            try {
                Class.forName("org.mariadb.jdbc.Driver").newInstance();
                conn = DriverManager.getConnection("jdbc:mysql://"+url+":"+port+"/"+database+"?serverTimezone=UTC",
                        "user", "userpass");
                System.out.println("<< Option 3 has run >>");
                String querry3= "SELECT actor.first_name AS fname, actor.last_name AS lname, film.title AS title\n" +
                        "FROM actor, film, film_actor\n" +
                        "WHERE actor.actor_id = film_actor.actor_id AND " +
                        "film.film_id = film_actor.film_id AND actor.first_name = ? ORDER BY rand() LIMIT 10";
                pstm = conn.prepareStatement(querry3);
                pstm.setString(1, actorFirstName);
                rst = pstm.executeQuery();

                while(rst.next()){
                    String fname = rst.getString("fname");
                    String lname = rst.getString("lname");
                    String title = rst.getString("title");

                    list3.add(new Option3(fname, lname, title));
                }
            }
            finally {
                if(rst != null)
                    rst.close();

                if(pstm != null)
                    pstm.close();

                if(conn != null)
                    conn.close();
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return list3;
    }
}
