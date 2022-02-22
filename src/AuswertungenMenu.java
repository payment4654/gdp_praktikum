import java.util.List;

import gmbh.kdb.hsw.gdp.Game;
import gmbh.kdb.hsw.gdp.domain.GameDevStudio;
import gmbh.kdb.hsw.gdp.domain.Project;
import gmbh.kdb.hsw.gdp.domain.ProjectBoard;

public class AuswertungenMenu extends MenuAdapter {

    private Game gameInstance;
    private GameDevStudioAdapter gameDevStudioAdapter;
    private GameDevStudio gameDevStudio;
    private DeveloperAdapter developerAdapter;
    MainMenu mainMenu;
    

    

    public AuswertungenMenu(Game gameInstance, GameDevStudio gameDevStudio) {
        this.gameInstance = gameInstance;
        this.gameDevStudio = gameDevStudio;
        gameDevStudioAdapter = new GameDevStudioAdapter(gameDevStudio);
        developerAdapter = new DeveloperAdapter();
    }

    // prints menu items for user to navigate in
    public void showAuswertungenMenu(int counter) {
        String allowedInputChars = "abcdefg";
        boolean areNoActionsLeft;
        String input;
        do {
            printActionsLeft(counter);
            System.out.println(
                    "AUSWERTUNGEN - Event Log(a) / Büroübersicht(b) / Entwickler(c) / Projekte(d) / Laufende Kosten(e) / Tage bis Bankrott(f) / Hauptmenü(g)");

            input = getInput(allowedInputChars);
            areNoActionsLeft = checkIfNoActionsAreLeft(input, counter, gameInstance);

            chooseActionAuswertung(input, counter);

        } while (!input.equals("g") && !areNoActionsLeft);
    }

    private void chooseActionAuswertung(String input, int counter) {
        switch (input) {
            case "a":
                printEventlog();
                incrementCounter(counter);
                break;
            case "b":
                printOffices();
                incrementCounter(counter);
                break;
            case "c":
                printDevelopers();
                incrementCounter(counter);
                break;
            case "d":
                printRunningProjects();
                incrementCounter(counter);
                break;
            case "e":
                printRunningCosts();
                incrementCounter(counter);
                break;
            case "f":
                printDaysToBankrupt();
                incrementCounter(counter);
                break;
            case "g":
                mainMenu.showMainRoundMenu(true);
                break;
        }
    }

    // Ausgabe EventLog(a) ausgelagert
    private void printEventlog() {
        EventLog log = new EventLog();
        log.printEventLog(gameInstance);
    }
    // Ausgabe Büros(b) ausgelagert
    private void printOffices() {
        gameDevStudioAdapter.printOffices();
    }

    // Ausgabe angestellte Entwickler(c) ausgelagert
    private void printDevelopers() {
        developerAdapter.printDevelopers();
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

    
    
}