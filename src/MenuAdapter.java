import java.util.Scanner;

import gmbh.kdb.hsw.gdp.Game;

public abstract class MenuAdapter {

    private static final int RUNDENZAHL = 3;
    private Scanner scan = new Scanner(System.in);

    public void printActionsLeft(int counter) {
        System.out.println((RUNDENZAHL - counter) + " Aktion(en) übrig");
    }

    public boolean checkIfNoActionsAreLeft(String input, int counter, Game gameInstance) {

        String allowedInputChars = "abcd";

        if (counter >= RUNDENZAHL) {
            System.out.println("Keine Aktionen übrig");
            resetCounter(counter);
            printDay(true, gameInstance);
            return true;
        } else {
            printActionsLeft(counter);
            getInput(allowedInputChars);
            return false;
        }
    }

    // counter++ ausgelagert
    public void incrementCounter(int counter) {
        counter++;
    }

    // counter reset ausgelagert
    public void resetCounter(int counter) {
        counter = 0;
    }

    // prints start/end of current day
    public void printDay(boolean end, Game gameInstance) {
        int spieltag = gameInstance.getDay().getNumber();
        // add "ENDE" to output if end is true, new line otherwise
        String umbruch = "\n\n";
        String endMessage = "";
        if (end) {
            endMessage = "ENDE";
            umbruch = "\n";
        }

        System.out.printf("%s---------- %s SPIELTAG %s ----------\n\n", umbruch, endMessage, spieltag);
    }

    public String getInput(String allowedInputChars) {
        String input = scan.next();
        checkIfInputIsValid(allowedInputChars, input);
        return input;
    }

    private String checkIfInputIsValid(String allowedInputChars, String input) {

        if (!allowedInputChars.contains(input) || input.length() != 1) {
            return input;
        } else
            return getInput(allowedInputChars);
    }
}
