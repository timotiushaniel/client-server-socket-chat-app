import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;


public class SocketClient2 {
    private static Socket clientSocket;
    private static PrintStream out;
    private static BufferedReader in;
    private static ObjectInputStream objIn;
    private static Scanner sc;
    private static int portNo = 7000;
    private static String host = "localhost";

    public static void main(String[] args) {
        int choice = -1;
        try {
            try {
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();

                clientSocket = new Socket(host, portNo);
                out = new PrintStream(clientSocket.getOutputStream());
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                //LOGIN
                System.out.println(in.readLine());
                System.out.println(in.readLine());
                System.out.println(in.readLine());

                String username = in.readLine();
                System.out.print(username);
                sc = new Scanner(System.in);
                sc.useDelimiter("\n");
                String usernameInput = sc.next();
                out.println(usernameInput);
                out.flush();

                String password = in.readLine();
                System.out.print(password);
                sc = new Scanner(System.in);
                sc.useDelimiter("\n");
                String passwordInput = sc.next();
                out.println(passwordInput);
                out.flush();

                System.out.println(in.readLine());

                String usernameData = in.readLine();
                String passwordData = in.readLine();
                //System.out.println(usernameData);
                //System.out.println(passwordData);

                if(usernameData.equals(usernameInput) && passwordData.equals(passwordInput)){
                    // Approval message from server
                    String msg = in.readLine();
                    System.out.println("\n"+msg+"\n");

                    while(true) {
                        mainMenuResponse();

                        sc = new Scanner(System.in);
                        sc.useDelimiter("\n");
                        choice = sc.nextInt();
                        int sendChoice = choice;
                        if(choice != 3) {
                            if(clientSocket != null && out != null && in != null) {
                                /* Client Sends */
                                // Kirim ke server pilihannya 1 atau 2
                                out.println(sendChoice);
                                out.flush();

                                // Terima Respon dari server ini pilihan 1 atau 2
                                if(choice == 1){
                                    System.out.println("");
                                    menuPilihan1Response();
                                    Request req = request(sc, choice);
                                    String str_req = gson.toJson(req);
                                    out.println(str_req);
                                    out.flush();
                                }
                                else if(choice == 2){
                                    System.out.println("");
                                    menuPilihan2Response();
                                    Request req = request(sc, choice);
                                    String str_req = gson.toJson(req);
                                    out.println(str_req);
                                    out.flush();
                                }
                                else{
                                    Request req = request(sc, choice);
                                    String str_req = gson.toJson(req);
                                    out.println(str_req);
                                    out.flush();
                                }

                                /* Client Receives */
                                switch(choice) {
                                    case(1):
                                        String str_resp = in.readLine();
                                        ArrayList<Option1> list1 = gson.fromJson(str_resp, new TypeToken<ArrayList<Option1>>(){}.getType());
                                        if(list1.isEmpty()){
                                            System.out.println("-");
                                        }
                                        else{
                                            Response.displayOption1(list1);
                                        }
                                        break;

                                    case(2):
                                        String str_resp2 = in.readLine();
                                        ArrayList<Option2> list2 = gson.fromJson(str_resp2, new TypeToken<ArrayList<Option2>>(){}.getType());
                                        if(list2.isEmpty()){
                                            System.out.println("-");
                                        }
                                        else{
                                            Response.displayOption2(list2);
                                        }
                                        break;

                                    default:
                                        String str_resp3 = in.readLine();
                                        OtherChoice otherChoice = gson.fromJson(str_resp3, new TypeToken<OtherChoice>(){}.getType());
                                        System.out.println("\n"+otherChoice.getMessage()+"\n");
                                        break;
                                }
                            }
                        }
                        else
                            break;
                    }
                }
                else{
                    //System.out.println("dc");
                    String msg = in.readLine();
                    System.out.println("==============================\n"+msg+"\n==============================\n");
                }
            }
            finally {
                out.close();
                in.close();
                clientSocket.close();
                System.out.println("\nYou're Disconnected!");
            }
        }
        catch(UnknownHostException e) {
            e.printStackTrace();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static void mainMenuResponse() throws IOException {
        String mainMenu = in.readLine();
        System.out.println(mainMenu);
        String mainMenu2 = in.readLine();
        System.out.println(mainMenu2);
        String mainMenu3 = in.readLine();
        System.out.println(mainMenu3);
        String mainMenu4 = in.readLine();
        System.out.println(mainMenu4);
        String mainMenu5 = in.readLine();
        System.out.print(mainMenu5);
    }

    private static void menuPilihan1Response() throws IOException {
        String line1 = in.readLine();
        System.out.println(line1);
        String line2 = in.readLine();
        System.out.println(line2);
        String line3 = in.readLine();
        System.out.println(line3);
        String line4 = in.readLine();
        System.out.println(line4);
        String line5 = in.readLine();
        System.out.println(line5);
        String line6 = in.readLine();
        System.out.println(line6);
        String line7 = in.readLine();
        System.out.println(line7);
        String line8 = in.readLine();
        System.out.println(line8);
        String line9 = in.readLine();
        System.out.println(line9);
        String line10 = in.readLine();
        System.out.print(line10);
    }

    private static void menuPilihan2Response() throws IOException {
        String line1 = in.readLine();
        System.out.println(line1);
        String line2 = in.readLine();
        System.out.println(line2);
        String line3 = in.readLine();
        System.out.println(line3);
        String line4 = in.readLine();
        System.out.println(line4);
        String line5 = in.readLine();
        System.out.println(line5);
        String line6 = in.readLine();
        System.out.println(line6);
        String line7 = in.readLine();
        System.out.print(line7);
    }

    private static Request request(Scanner sc, int choice) throws IOException {
        Request req = null;
        switch(choice) {
            case(1):
                String genre = sc.next();
                req = new Request(choice, genre, "", "");
                break;

            case(2):
                String year = sc.next();
                if(year.equals("1")){
                    year = "2006";
                    req = new Request(choice, year, "","");
                }
                else{
                    req = new Request(choice, year, "","");
                }
                break;

            default:
                req = new Request(choice, "", "", "");
                break;
        }
        return req;
    }
}
