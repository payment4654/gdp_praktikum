import gmbh.kdb.hsw.gdp.Game;
import gmbh.kdb.hsw.gdp.domain.*;

import java.util.List;
import java.util.Scanner;

public class MainMenu {

    GameDevStudioAdapter gameDevStudioAdapter;
    private static final int RUNDENZAHL = 3;
    private Scanner scan = new Scanner(System.in);
    GameDevStudio gameDevStudio;
    Game gameInstance;
    int counter = 0;

    public void setStudio(GameDevStudio studio) {
        this.gameDevStudio = studio;
        gameDevStudioAdapter = new GameDevStudioAdapter(gameDevStudio);
    }

    public void setGameInstance(Game gameInstance) {
        this.gameInstance = gameInstance;
    }
//TODO das gleiche wie mit Projects
    public boolean startRound() {
        printDay(false);
        return showMainRoundMenu(false);
    }

    public boolean showMainRoundMenu(boolean fromInside) {
        System.out.println(
                "AKTIONEN - Spiel beenden(a) / Nächste Runde(b) / Auswertungen anzeigen(c) / Entwickler anstellen(d)");
        String allowedInputChars = "abcd";
        String input = getInput(allowedInputChars);
        boolean areNoActionsLeft = checkIfNoActionsAreLeft(input);
        if (areNoActionsLeft) {
            return true;
        } else {
            chooseActionHauptmenu(input);
        }
        // is used to avoid multiple end day messages if method is called from another
        // menu
        if (!fromInside)
            printDay(true);

        resetCounter();
        return true;
    }

    private boolean chooseActionHauptmenu(String input) {

        switch (input) {
            case "a":
                System.out.println("------- SPIEL BEENDET -------");
                return false;

            case "c":
                showAuswertungenMenu();
                return true;

            case "d":
                showAnstellenMenu();
                return true;

            default:
                return true;
        }
    }

    //TODO das gleiche wie mit Projects
    private void showAnstellenMenu() {
        System.out.println("ENTWICKLER AUF JOBSUCHE");
        List<Application> applications = gameDevStudio.getApplications();

        printDeveloperMarket(applications);

        System.out.println("\n - Anstellen(a) / Hauptmenü(b)");

        checkInputAnstellen(applications, getInputAnstellenMenu());
        showMainRoundMenu(true);
    }

    private void printDeveloperMarket(List<Application> applications) {
        for (Application a : applications) {
            System.out.println("       Name: " + a.getDeveloper().getName().getName());
            System.out.println("          Anstellungsgebühr: " + a.getHireAgentFee().getValue().intValue() + "$");
            System.out.println("          Anstellungsbonus: " + a.getHireBonus().getValue().intValue() + "$");
        }
    }

    private void checkInputAnstellen(List<Application> applications, String input) {
        if (input.equals("a")) {
            gameDevStudio.acceptApplication(applications.get(0), gameDevStudio.getOffices().get(0));
            System.out.printf("ENTWICKLER %s ANGESTELLT \n", applications.get(0).getDeveloper().getName().getName());
            incrementCounter();
        }
    }


    //TODO AllowedInputChars übergeben und getInput nutzen
    private String getInputAnstellenMenu() {
        String input;
        do {
            input = scan.next();
        } while (!(input.equals("a") || input.equals("b")));
        return input;
    }

    private String getInput(String allowedInputChars) {
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

    /**
     * end round if 3 actions have been made, ask for input otherwise
     * 
     * @return -
     */
    private boolean checkIfNoActionsAreLeft(String input) {

        String allowedInputChars = "abcd";

        if (counter >= RUNDENZAHL) {
            System.out.println("Keine Aktionen übrig");
            resetCounter();
            printDay(true);
            return true;
        } else {
            printActionsLeft();
            getInput(allowedInputChars);
            return false;
        }
    }

    
    

    // prints start/end of current day
    void printDay(boolean end) {
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

    //TODO das gleiche wie mit Projects
    // prints menu items for user to navigate in
    private void showAuswertungenMenu() {
        String allowedInputChars = "abcdefg";
        boolean areNoActionsLeft;
        String input;
        do {
            printActionsLeft();
            System.out.println(
                    "AUSWERTUNGEN - Event Log(a) / Büroübersicht(b) / Entwickler(c) / Projekte(d) / Laufende Kosten(e) / Tage bis Bankrott(f) / Hauptmenü(g)");

            input = getInput(allowedInputChars);
            areNoActionsLeft = checkIfNoActionsAreLeft(input);

            chooseActionAuswertung(input);

        } while (!input.equals("g") && !areNoActionsLeft);
    }

    private void printActionsLeft() {
        System.out.println((RUNDENZAHL - counter) + " Aktion(en) übrig");
    }

    private void chooseActionAuswertung(String input) {
        switch (input) {
            case "a":
                printEventlog();
                incrementCounter();
                break;
            case "b":
                printOffices();
                incrementCounter();
                break;
            case "c":
                printDevelopers();
                incrementCounter();
                break;
            case "d":
                printRunningProjects();
                incrementCounter();
                break;
            case "e":
                printRunningCosts();
                incrementCounter();
                break;
            case "f":
                printDaysToBankrupt();
                incrementCounter();
                break;
            case "g":
                showMainRoundMenu(true);
                break;
        }
    }

    // Ausgabe EventLog(a) ausgelagert
    private void printEventlog() {
        EventLog log = new EventLog();
        log.printEventLog(gameInstance);
    }
//TODO das gleiche wie mit Projects -> GameDevStudio
    // Ausgabe Büros(b) ausgelagert
    private void printOffices() {
        List<Office> offices = gameDevStudio.getOffices();
        for (Office o : offices) {
            System.out.println("       Name: " + o.getName().getName());
            System.out.println("          Lease: " + o.getLease());
        }
    }

    //TODO das gleiche wie mit Projects -> GameDevStudio
    // Ausgabe angestellte Entwickler(c) ausgelagert
    private void printDevelopers() {
        List<Office> offices = gameDevStudio.getOffices();
        for (Office o : offices) {
            for (Developer d : o.getDevelopers()) {
                printDevelopersInfo(d);
                tryHappiness(d);
            }
        }
    }

    // Try Catch aus printDevelopers ausgelagert
    private void tryHappiness(Developer d) {
        try {
            System.out
                    .println("          Arbeitet an: " + d.getWorkingOn().getName().getName());
        } catch (NullPointerException nullPointerException) {
            System.out.println("          Zufriedenheit: " + d.getHappiness().get());
        }
    }

    // Ausgabe Entwicklerinfos aus printDevelopers ausgelagert
    private void printDevelopersInfo(Developer d) {
        System.out.println("       Name: " + d.getName().getName());
        System.out.println("          Gehalt: " + d.getSalary().toString());
        System.out.println("          Einstellungstag: " + d.getDayOfHire().getNumber());
        System.out.println("          Coding Skills: " + d.getSkills().getCoding());
        System.out.println("          Design Skills: " + d.getSkills().getDesign());
        System.out.println("          Research Skills: " + d.getSkills().getResearch());
        System.out.println("          Testing Skills: " + d.getSkills().getTesting());
    }

    // Ausgabe laufende Projekte(d) ausgelagert
    private void printRunningProjects() {
        ProjectBoard board = gameDevStudio.getProjectBoard();
        List<Project> projects = board.get();
        ProjectAdapter.checkForRunningProjects(projects, gameInstance);
    }

    

    // Ausgabe laufende Kosten(e) ausgelagert
    private void printRunningCosts() {

        System.out
                .println("       Laufende Kosten: " + gameDevStudioAdapter.calculateOfficeAndDeveloperCosts(gameDevStudio).toString()
                        + "\n");
    }



    // Ausgabe Tage bis Bankrott(f) ausgelagert
    private void printDaysToBankrupt() {
        String daysLeftString = Double.toString(gameDevStudioAdapter.calculateDaysLeft());
        System.out.println("       Tage bis zum Bankrott: " + daysLeftString + "\n");
    }

    // counter++ ausgelagert
    private void incrementCounter() {
        counter++;
    }

    // counter reset ausgelagert
    private void resetCounter() {
        counter = 0;
    }
}
