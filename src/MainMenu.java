import gmbh.kdb.hsw.gdp.Game;
import gmbh.kdb.hsw.gdp.domain.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class MainMenu {

    private static final int RUNDENZAHL = 3;
    private Scanner scan = new Scanner(System.in);
    GameDevStudio gameDevStudio;
    Game gameInstance;
    int counter = 0;

    public void setStudio(GameDevStudio studio) {
        this.gameDevStudio = studio;
    }

    public void setGameInstance(Game gameInstance) {
        this.gameInstance = gameInstance;
    }

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

        counter = 0;
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

    private void showAnstellenMenu() {
        System.out.println("ENTWICKLER AUF JOBSUCHE");
        List<Application> applications = gameDevStudio.getApplications();

        for (Application a : applications) {
            System.out.println("       Name: " + a.getDeveloper().getName().getName());
            System.out.println("          Anstellungsgebühr: " + a.getHireAgentFee().getValue().intValue() + "$");
            System.out.println("          Anstellungsbonus: " + a.getHireBonus().getValue().intValue() + "$");
        }

        System.out.println("\n - Anstellen(a) / Hauptmenü(b)");

        String input = "";
        // TODO auslagern: repeat until input code is valid
        //
        //
        // !!!!!!!!!!!!!!
        //
        do {
            input = scan.next();
        } while (!(input.equals("a") || input.equals("b")));
        //
        //
        // !!!!!!!!!!!
        //
        //

        if (input.equals("a")) {
            gameDevStudio.acceptApplication(applications.get(0), gameDevStudio.getOffices().get(0));
            System.out.printf("ENTWICKLER %s ANGESTELLT \n", applications.get(0).getDeveloper().getName().getName());
            incrementCounter();
        }
        showMainRoundMenu(true);
    }

    // TODO checken ob das in Ordnung ist:
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
            counter = 0;
            printDay(true);
            return true;
        } else {
            // repeat until input code is valid
            System.out.println((RUNDENZAHL - counter) + " Aktion(en) übrig");
            // auslagern
            //
            //
            // !!!!!!!!!!
            //
            do {
                input = scan.next();
            } while (!(allowedInputChars.contains(input)) || input.length() != 1);
            //
            //
            // !!!!!!!!!
            //
            //
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

    // prints menu items for user to navigate in
    private void showAuswertungenMenu() {
        String allowedInputChars = "abcdefg";
        boolean areNoActionsLeft;
        String input;
        do {
            System.out.println((RUNDENZAHL - counter) + " Aktion(en) übrig");
            System.out.println(
                    "AUSWERTUNGEN - Event Log(a) / Büroübersicht(b) / Entwickler(c) / Projekte(d) / Laufende Kosten(e) / Tage bis Bankrott(f) / Hauptmenü(g)");

            input = getInput(allowedInputChars);
            areNoActionsLeft = checkIfNoActionsAreLeft(input);

            chooseActionAuswertung(input);

        } while (!input.equals("g") && !areNoActionsLeft);
    }

    private void chooseActionAuswertung(String input) {
        switch (input) {
            case "a":
                // TODO auslagern: Ausgabe Event Log
                EventLog log = new EventLog();
                log.printEventLog(gameInstance);
                incrementCounter();
                break;
            case "b":
                // TODO auslagern: Ausgabe Büros
                List<Office> offices = gameDevStudio.getOffices();
                for (Office o : offices) {
                    System.out.println("       Name: " + o.getName().getName());
                    System.out.println("          Lease: " + o.getLease());
                }
                incrementCounter();
                break;
            case "c":
                /*
                 * TODO auslagern: Ausgabe angestellte Entwickler:innen gendern hier ja ist
                 * wichtig weil sonst
                 * sich die Programmierenden nicht angesprochen fühlen ja
                 */
                offices = gameDevStudio.getOffices();
                for (Office o : offices) {
                    for (Developer d : o.getDevelopers()) {
                        // TODO auslagern
                        System.out.println("       Name: " + d.getName().getName());
                        System.out.println("          Gehalt: " + d.getSalary().toString());
                        System.out.println("          Einstellungstag: " + d.getDayOfHire().getNumber());
                        System.out.println("          Coding Skills: " + d.getSkills().getCoding());
                        System.out.println("          Design Skills: " + d.getSkills().getDesign());
                        System.out.println("          Research Skills: " + d.getSkills().getResearch());
                        System.out.println("          Testing Skills: " + d.getSkills().getTesting());
                        try {
                            System.out
                                    .println("          Arbeitet an: " + d.getWorkingOn().getName().getName());
                        } catch (NullPointerException nullPointerException) {
                            System.out.println("          Zufriedenheit: " + d.getHappiness().get());
                        }
                    }
                }
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

    // Ausgabe laufende Projekte(d) ausgelagert
    private void printRunningProjects() {
        ProjectBoard board = gameDevStudio.getProjectBoard();
        List<Project> projects = board.get();
        checkForRunningProjects(projects);
    }

    // Überprüfung nach laufenden Projekten aus printProjectData ausgelagert
    private void checkForRunningProjects(List<Project> projects) {
        if (projects.size() == 0) {
            printNoRunningProjects();
        } else {
            printProjectInfo(projects);
        }
    }

    // Ausgabe bei keinen laufenden Projekten aus checkForRunningProjects
    // ausgelagert
    private void printNoRunningProjects() {
        System.out.println("       Keine laufenden Projekte \n");
    }

    // Ausgaben für jedes laufende Projekt aus checkForRunningProjects ausgelagert
    private void printProjectInfo(List<Project> projects) {
        for (Project p : projects) {
            printProjectName(p);
            printDaysLeftForProject(p);
        }
    }

    // Ausgabe Projektname aus printProjectInfo ausgalagert
    private void printProjectName(Project p) {
        System.out.println("       Projekt: " + p.getName().getName());
    }

    // Ausgabe Tage übrig für Projekt aus printProjectInfo ausgalagert
    private void printDaysLeftForProject(Project p) {
        String daysLeft = Integer
                .toString(p.getDeadline().getNumber() - gameInstance.getDay().getNumber());
        System.out.println("          Tage übrig: " + daysLeft);
    }

    // Ausgabe laufende Kosten(e) ausgelagert
    private void printRunningCosts() {
        System.out
                .println("       Laufende Kosten: " + calculateOfficeAndDeveloperCosts(gameDevStudio).toString()
                        + "\n");
    }

    // Ausgabe Tage bis Bankrott(f) ausgelagert
    private void printDaysToBankrupt() {
        double daysLeft = gameDevStudio.getCash().getValue().doubleValue()
                / calculateOfficeAndDeveloperCosts(gameDevStudio).getValue().doubleValue();
        String daysLeftString = Double.toString(Math.floor(daysLeft * 10) / 10);
        System.out.println("       Tage bis zum Bankrott: " + daysLeftString + "\n");
    }

    // counter++ ausgelagert
    private void incrementCounter() {
        counter++;
    }

    // Office Leasings und Developer Salaries ausgelagert
    private Money calculateOfficeAndDeveloperCosts(GameDevStudio studio) {
        Money total = new Money(new BigDecimal(0));
        addOfficeLeasesAndDeveloperSalaries(total, studio);
        return total;
    }

    private void addOfficeLeasesAndDeveloperSalaries(Money total, GameDevStudio studio) {
        for (Office o : studio.getOffices()) {
            total = total.add(o.getLease());
            addDeveloperSalaries(total, o);
        }
    }

    // for-Verzweigung aus addOfficeLeasesAndDeveloperSalaries geteilt
    private void addDeveloperSalaries(Money total, Office o) {
        for (Developer d : o.getDevelopers()) {
            total = total.add(d.getSalary());
        }
    }

}
