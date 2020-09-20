import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;

public class SocketServer {
    private static ServerSocket serverSocket;
    private static Socket clientSocket;
    private static BufferedReader in;
    private static PrintStream out;
    private static ObjectOutputStream objOut;
    private static int portNo = 7000;
    private static String host = "localhost";

    public static void main(String[] args) throws Exception {
        GsonBuilder gsonBuilder;
        Gson gson;
        try {
            serverSocket = new ServerSocket(portNo);
            System.out.println("Server is running.");
            System.out.println("Waiting for client's connection........");
        }
        catch(IOException e) {
            e.printStackTrace();
        }

        clientSocket = serverSocket.accept();
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new PrintStream(clientSocket.getOutputStream());

        //Mengirimkan respon untuk log in user
        String top = "***********************************************";
        out.println(top);
        out.flush();
        String welcoming = "***** WELCOME TO TIMOTIUS MOVIE GUIDE APP *****";
        out.println(welcoming);
        out.flush();
        String bottom = "***********************************************";
        out.println(bottom);
        out.flush();

        // Menerima request user log in dari client
        String username = "Username: ";
        out.println(username);
        out.flush();
        String usernameInput = in.readLine();
        //System.out.println(usernameInput);

        String password = "Password: ";
        out.println(password);
        out.flush();
        String passwordInput = in.readLine();
        //System.out.println(passwordInput);

        String under = "***********************************************";
        out.println(under);
        out.flush();

        // Proses verifikasi user login
        if(usernameInput != null && passwordInput != null){
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            int port = 3306;
            String url = "localhost";
            String database = "sakila";
            ArrayList<User> list = new ArrayList<User>();
            //System.out.println("connect");

            try {
                try {
                    // Proses membandingkan autentifikasi user dari client's request dengan data di db
                    Class.forName("org.mariadb.jdbc.Driver").newInstance();
                    conn = DriverManager.getConnection("jdbc:mysql://"+url+":"+port+"/"+database+"?serverTimezone=UTC",
                            "user", "userpass");
                    System.out.println("<< Option 1 has run >>");
                    String query = "SELECT * FROM user WHERE username = ? and password = ?";
                    ps = conn.prepareStatement(query);
                    ps.setString(1,usernameInput);
                    ps.setString(2,passwordInput);
                    rs = ps.executeQuery();
                    //System.out.println("process");

                    // Jika data user login tidak terdapat di db, maka server kirim respon warning
                    if(!rs.next()){
                        System.out.println("<< DATA NOT FOUND IN DATABASE >>");

                        out.println("wrongUsername");
                        out.flush();
                        out.println("wrongPassword");
                        out.flush();

                        out.println("Username atau Password Salah");
                    }
                    else{ // Jika data user login terdapat di db, maka execute query
                        do{
                            String usernameData = rs.getString("username");
                            String passwordData = rs.getString("password");

                            list.add(new User(usernameData, passwordData));
                            //System.out.println(list);
                            System.out.println("<< DATA FOUND IN DATABASE >>");
                        }while(rs.next());
                    }
                    // Mengambil data username dan password yang telah disimpan dalam array list object user
                    for (User user : list) {
                        String usernameFinal = user.getUsername();
                        String passwordFinal = user.getPassword();
                        // Jika input username dan password sama dengan data yang ada di array list, maka server akan melayani client
                        if (usernameInput.equals(usernameFinal) && passwordInput.equals(passwordFinal)) {
                            out.println(usernameFinal);
                            out.flush();
                            out.println(passwordFinal);
                            out.flush();
                            //System.out.println("MATCH");

                            out.println("OK");
                            out.flush();

                            boolean accept = true;
                            while(accept) {
                                try {
                                    gsonBuilder = new GsonBuilder();
                                    gson = gsonBuilder.create();

                                    sendMainMenu();

                                    /* Server Receives */
                                    // Terima dari Client ini tuh pilihan 1 atau 2
                                    String choice = in.readLine();

                                    /* Server Sends */
                                    // Kirim Respon (Display sub-menu) ke Client tergantung pilihannya 1 atau 2
                                    if(choice == null){
                                        System.out.print("");
                                    }
                                    else if(choice.equals("1")){
                                        sendMenuPilihan1();
                                    }
                                    else if(choice.equals("2")){
                                        sendMenuPilihan2();
                                    }
                                    else if(choice.equals("3")){
                                        sendMenuPilihan3();
                                    }

                                    String req_message = in.readLine();
                                    if(req_message != null){
                                        System.out.println(req_message);
                                        Request req = gson.fromJson(req_message, new TypeToken<Request>(){}.getType());
                                        String str_resp = "";

                                        switch(req.getCode()) {
                                            case(1):
                                                str_resp = gson.toJson(JdbcConnection.LoadOption1(req.getParam01()));
                                                out.println(str_resp);
                                                out.flush();
                                                break;

                                            case(2):
                                                str_resp = gson.toJson(JdbcConnection.LoadOption2(req.getParam01()));
                                                out.println(str_resp);
                                                out.flush();
                                                break;

                                            case(3):
                                                str_resp = gson.toJson(JdbcConnection.LoadOption3(req.getParam01()));
                                                out.println(str_resp);
                                                out.flush();
                                                break;

                                            default:
                                                str_resp = gson.toJson(new OtherChoice(req.getCode(), "Pilihan tidak tersedia!"));
                                                out.println(str_resp);
                                                out.flush();
                                                break;
                                        }
                                    }
                                    else{
                                        System.out.println("<< Client Disconnected >>\n");
                                        System.out.println("Server is running.");
                                        System.out.println("Waiting for client's request........");
                                        accept = false;
                                    }
                                }
                                catch(IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            System.out.println("<< UNKNOWN USER >>");
                        }
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
        }
    }

    private static void sendMainMenu(){
        String mainMenu = "Choices:";
        out.println(mainMenu);
        out.flush();
        String mainMenu2 = "1. Menampilkan Judul Film Berdasarkan Genre";
        out.println(mainMenu2);
        out.flush();
        String mainMenu3 = "2. Menampilkan Judul Film Berdasarkan Tahun";
        out.println(mainMenu3);
        out.flush();
        String mainMenu4 = "3. Menampilkan Judul Film Berdasarkan Nama Depan Actor";
        out.println(mainMenu4);
        out.flush();
        String mainMenu5 = "0. Exit";
        out.println(mainMenu5);
        out.flush();
        String mainMenu6 = "Enter your choice: ";
        out.println(mainMenu6);
        out.flush();
    }

    private static void sendMenuPilihan1(){
        String line1 = "Choose the Genre: ";
        out.println(line1);
        out.flush();
        String line2 = "1. Action             9. Foreign";
        out.println(line2);
        out.flush();
        String line3 = "2. Animation          10. Games";
        out.println(line3);
        out.flush();
        String line4 = "3. Children           11. Horror";
        out.println(line4);
        out.flush();
        String line5 = "4. Classics           12. Music";
        out.println(line5);
        out.flush();
        String line6 = "5. Comedy             13. New";
        out.println(line6);
        out.flush();
        String line7 = "6. Documentary        14. Sci-Fi";
        out.println(line7);
        out.flush();
        String line8 = "7. Drama              15. Sports";
        out.println(line8);
        out.flush();
        String line9 = "8. Family             16. Travel";
        out.println(line9);
        out.flush();
        String line10 = "Enter the Genre number (1-16):";
        out.println(line10);
        out.flush();
    }

    private static void sendMenuPilihan2(){
        String line1 = "Choose the Release Year: ";
        out.println(line1);
        out.flush();
        String line2 = "1. 2006         6. 2011";
        out.println(line2);
        out.flush();
        String line3 = "2. 2007         7. 2012";
        out.println(line3);
        out.flush();
        String line4 = "3. 2008         8. 2013";
        out.println(line4);
        out.flush();
        String line5 = "4. 2009         9. 2014";
        out.println(line5);
        out.flush();
        String line6 = "5. 2010         10. 2015";
        out.println(line6);
        out.flush();
        String line7 = "Enter the release year (1-10): ";
        out.println(line7);
        out.flush();
    }

    private static void sendMenuPilihan3(){
        String line1 = "Enter Actor First Name: ";
        out.println(line1);
        out.flush();
    }
}