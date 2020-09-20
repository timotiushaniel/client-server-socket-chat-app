import java.util.ArrayList;

public class Response {
    public static void displayOption1(ArrayList<Option1> list) {
        System.out.println("");
        System.out.println("==============================================================");
        System.out.format("%10s%35s", "Film Title", "Category Name\n");
        System.out.println("==============================================================");
        for(int i=0; i<list.size(); i++) {
            Option1 data = list.get(i);
            System.out.println(data.toString());
        }
        System.out.println("==============================================================");
        System.out.println("");
    }

    public static void displayOption2(ArrayList<Option2> list2){
        System.out.println("");
        System.out.println("==============================================================");
        System.out.format("%10s%39s", "Film Title", "Release Year\n");
        System.out.println("==============================================================");
        for(int i=0; i<list2.size(); i++) {
            Option2 data2 = list2.get(i);
            System.out.println(data2.toString());
        }
        System.out.println("==============================================================");
        System.out.println("");
    }

    public static void displayOption3(ArrayList<Option3> list3){
        System.out.println("");
        System.out.println("================================================================================================");
        System.out.format("%10s%39s%39s", "Actor First Name", "Actor Last Name", "Film Title\n");
        System.out.println("================================================================================================");
        for(int i=0; i<list3.size(); i++) {
            Option3 data3 = list3.get(i);
            System.out.println(data3.toString());
        }
        System.out.println("================================================================================================");
        System.out.println("");
    }
}
