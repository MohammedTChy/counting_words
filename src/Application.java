import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Application {
    private static LinkedHashMap<String, Integer> wordCountSaver;
    private static BufferedReader readerInput;

    private static FileReader file;

    public static void main(String[] args) {


        listOfCommands(); //Lista commands som finns vid start

        Scanner Scanner = new Scanner(System.in);
        System.out.println();
        System.out.println("> Type a command:");
        System.out.print(" > ");
        String commandInput = Scanner.nextLine().toLowerCase().replaceAll("\\s+","");


        while (!commandInput.equals("exit")) { //Loop tills man skriver in för att avlusta programmet

            switch (commandInput) {
                case "add" -> addNewTextFile(Scanner, "no");
                case "addprint" -> addNewTextFile(Scanner, "yes");
                case "load" -> PreLoadedWord("no");
                case "loadprint" -> PreLoadedWord("yes");
                case "count" -> printOutWordsCounter();
                case "help" -> listOfCommands();
                default -> System.out.println(">Command doesn't exist");
            }

            System.out.println();
            System.out.println("> Type 'help' to show commands again ");
            System.out.println("> Type a command:");
            System.out.print("\n > ");
            commandInput = Scanner.nextLine().trim();
        }

        System.out.println("Word counter program ended");


    }

    private static void listOfCommands() {
        System.out.println();
        System.out.println("* add - add a text file to server, please copy the full true path of the file");
        System.out.println("* add print - add new path and print out the text from the file");
        System.out.println("* load - preload an existing file text from server");
        System.out.println("* load print - preload an existing file and print out the text from the file");
        System.out.println("* count - count top 10 most used words");
        System.out.println("* help - show commands again");
        System.out.println("* exit - Terminate the program");
        System.out.println("-----------------------------------------------------------------------------------");
        System.out.println("** Does not matter if the commands are lower case or upper case words");
    }

    private static void printOutWordsCounter() {
        ArrayList<Integer> values = new ArrayList<Integer>(wordCountSaver.values()); //Hämta in allt från wordCountSaver

        values.sort(Collections.reverseOrder()); //Sorter så att allt hammnar i störts storlket först

        int wordcheckPointer = -10; //Bara se till att ordet som man har redan räknat inte kommer med, default är en låg siffra bara för att

        System.out.println("> These are the top 10 most used word in the text file:");
        System.out.println();
        for (Integer wordBeingChecked : values.subList(0, 9)) { // Ta ut från listan top 10 ord.
            if (wordcheckPointer == wordBeingChecked) // Dubbel kollar bara om den har redan skrivit ut den redan, om det är så gå vidare i for loopen
            {
            } else {//Kör vidare om det inte är samma ord
                wordcheckPointer = wordBeingChecked; //Ladda att den pekar redan på den ordet så forsätt till nästa i for loopen


                for (String howManyTimes : wordCountSaver.keySet()) {
                    if (Objects.equals(wordCountSaver.get(howManyTimes), wordBeingChecked)) // Skrive ut antalet den upprepas och ordet.
                        System.out.println(wordBeingChecked + " " + howManyTimes);


                }
            }
        }
    }

    private static void addNewTextFile(Scanner input, String shouldPrint) {
        wordCountSaver = new LinkedHashMap<String, Integer>();

        System.out.println("> Please type the full true path of the text file");
        System.out.print("\n > ");
        String filePathInput = input.nextLine().trim();


        try {
            if(shouldPrint.equals("no")) {
                saveWordsAndCounter(filePathInput);
            } else if (shouldPrint.equals("yes")) {
                print(filePathInput);
                saveWordsAndCounter(filePathInput);
            }

        } catch (Exception e) {
            System.out.println("> Something went wrong with loading the text file check your path and the error code:" + e);
        }
    }

    private static void saveWordsAndCounter(String filePathInput) throws IOException {
        file = new FileReader(filePathInput);

        readerInput = new BufferedReader(file);
        String readInputFromBufferToString;

        while ((readInputFromBufferToString = readerInput.readLine()) != null) {
            readInputFromBufferToString = readInputFromBufferToString.toLowerCase(); // Få ner allt till lowercase så man vet att det är samma ord
            String[] tempSavedWordArray = readInputFromBufferToString.split("\\s+"); //Split varje ord och ta bort white space

            for (String tempSaveWordCount : tempSavedWordArray) {
                if (tempSaveWordCount.length() == 0) { //Bara en checker som gör att att går vidare i for loopen

                } else {

                    Integer howManyTimesAdd = wordCountSaver.get(tempSaveWordCount);

                    if (howManyTimesAdd == null) {
                        howManyTimesAdd = 1;
                    } else {
                        howManyTimesAdd++;
                    }

                    wordCountSaver.put(tempSaveWordCount, howManyTimesAdd);
                }
            }

        }
    }

    private static void print(String inputFile) {
        try {
            file = new FileReader(inputFile);
            readerInput = new BufferedReader(file);
            String readInputFromBufferToString;

            while ((readInputFromBufferToString = readerInput.readLine()) != null) {

                System.out.println(readInputFromBufferToString);

            }

        } catch (Exception e) {
            System.out.println(">Something went wrong with prnting out the textfile check the error code: " + e);
        }
    }

    private static void PreLoadedWord(String shouldPrint) {
        wordCountSaver = new LinkedHashMap<String, Integer>();
        System.out.println("Preloaded file is now loaded");
        String filePathInput = "src/islands_in_the_stream.txt";
        print(filePathInput);


        try {
            if(shouldPrint.equals("no")) {
                saveWordsAndCounter(filePathInput);
            } else if (shouldPrint.equals("yes")) {
                print(filePathInput);
                saveWordsAndCounter(filePathInput);
            }

        } catch (Exception e) {
            System.out.println("> Something went wrong with pre loading the file, please check the error code: " + e);
        }
    }


}

