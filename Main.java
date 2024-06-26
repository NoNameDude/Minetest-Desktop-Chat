import java.io.File;  // Import the File class
import java.io.FileNotFoundException;
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;
import javax.swing.JOptionPane;

class Main extends Thread{
    //check lengh of table and return true or false
    public boolean is_empty(String[] table) {
        if (table.length > 0) {
            return true;
        } else {
            return false;
        }
    }

    //does file has input?
    public static boolean is_file_empty(String test) {
        File logFile = new File(test);
        if(logFile.length() == 0) {
            return true;
        } else {
            return false;
        }
    }

    //write into file function
    public static void write_into_file(String[] input, String filepath) {
        for (String i : input)  {
            try {
                FileWriter myWriter = new FileWriter(filepath, true);
                myWriter.write(i + "\n");
                myWriter.close();
                //System.out.println("Successfully wrote to the file.");
            } catch (IOException e) {
                //System.out.println("An error occurred.");
                e.printStackTrace();
            }
        } 
    }

    //read from file
    public static String[] read_from_file(String filepath) {
        String[] text_array = new String[1]; //empty array
        int num = 0;
        try {
            File myObj = new File(filepath);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine(); 
                //System.out.println(data + " | Test");
                //Resize Array
                text_array = Arrays.copyOf(text_array, num+1);
                text_array[num] = data;
                num = num+1;

            }
            myReader.close();
            //myObj.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return text_array;
    }

    //empty file
    public static void empty_file(String filepath) {
        try {
            PrintWriter writer = new PrintWriter(filepath);
            writer.print("");
            // other operations
            writer.close();
        } catch(FileNotFoundException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    
    //Thread for game input
    public void run() {
        while(true) {
            //Path to filename2.txt
            String filepath2 = "";
            //Has file an input?
            boolean has_input = is_file_empty(filepath2);
            //System.out.println("Test");
            if (has_input==false) {
                String[] text_array = read_from_file(filepath2);
                for (String message : text_array) {
                    System.out.println(message);
                }
            } 
            //Make sure to empty file after reading
            empty_file(filepath2);
        }
    }


    public static void main(String[] args) throws IOException {
      //Path to filename.txt
        String filepath = "";
        boolean Prog = true;
        Main thread = new Main();
        thread.start();
        try (Scanner myObj = new Scanner(System.in)) {
            while(Prog) { 
                String input = JOptionPane.showInputDialog("Enter message");  // Read user input
                FileWriter myWriter = new FileWriter(filepath, true);
                myWriter.write(input + "\n");
                myWriter.close();
            }
        } 
    }
} 
