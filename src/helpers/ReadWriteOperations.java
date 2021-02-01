package helpers;

import java.io.*;
import java.util.ArrayList;

// Read from and write to any file database
public class ReadWriteOperations {

    /**
     * Save list to a file db
     *
     * @param fileName the name of the file you want to save to
     * @param list the array list to be saved
     */
    public static void saveToFileDb(String fileName, ArrayList<?> list){
        try{
            FileOutputStream fileOutput = new FileOutputStream(fileName);
            ObjectOutputStream out = new ObjectOutputStream(fileOutput);
            // write list to file
            out.writeObject(list);
            out.flush();
            // close stream
            out.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    };

    /**
     * Read from file db
     *
     * @param fileName the name of the file you want to read from
     * @return an array list from the database. Can return an empty array is there is no data
     */
    public static ArrayList<?> readFromFileDb(String fileName){
        ArrayList<?> list = new ArrayList<>();
        try{
            FileInputStream fileInput = new FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(fileInput);
            list = (ArrayList<?>)in.readObject();
            in.close();
        } catch(Exception e){
            e.printStackTrace();
        }
        return list;
    };
}
