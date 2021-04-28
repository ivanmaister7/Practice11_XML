import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class FB2Reader {
    File[] listOfFiles;
    ArrayList<String> tegs = new ArrayList<>();
    FB2Reader(String path) throws IOException {
        tegs.add("DATE");
        tegs.add("PLACES");
        tegs.add("TITLE");
        tegs.add("BODY");

        listOfFiles = new File(path).listFiles();
        int id = 1;
        for (int i = 0; i < 2; i++) {
            FileReader fr = new FileReader(listOfFiles[i]);
            Scanner scan = new Scanner(fr);
            String temp = "";

            StringBuilder sb = new StringBuilder();
            boolean record = false;
            boolean skip = false;
            while (scan.hasNextLine()) {
                StringTokenizer st = new StringTokenizer(scan.nextLine(), " &;<>");
                while (st.hasMoreTokens()) {
                    temp = st.nextToken();
                    for(String teg: tegs){
                         if(temp.equals("/"+teg)){
                            record = false;
                        }
                    }
                    if(record){
                        if(temp.equals("D")||temp.equals("/D"))
                            continue;
                        sb.append(temp).append(" ");
                        skip = true;
                    }
                    for(String teg: tegs){
                        if(temp.equals(teg)){
                            record = true;
                        }
                    }
                }
                if(skip){
                    sb.append("\n");
                    skip = false;
                }
                if(temp.equals("/REUTERS")){
                    File ff = new File("reut/reut-"+id+".txt");
                    FileWriter nFile = new FileWriter(ff);
                    nFile.write(String.valueOf(sb));
                    nFile.close();
                    id++;
                    sb.delete(0,sb.length());
                }
            }
            //System.out.println(counterList.toString());
            fr.close();
        }
    }

    public static void main(String[] args) throws IOException {
        new FB2Reader("C:/Users/ВАНЯ/IdeaProjects/Practice8_Claster/books2");
    }
}
