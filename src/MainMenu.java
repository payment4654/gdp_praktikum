import gmbh.kdb.hsw.gdp.Game;
import gmbh.kdb.hsw.gdp.domain.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class MainMenu {

    Scanner scan = new Scanner(System.in);
    GameDevStudio gameDevStudio;
    Game gameInstance;
    int counter = 0;

    public MainMenu()
    {

    }

    public void setStudio(GameDevStudio studio) {
        this.gameDevStudio = studio;
    }

    public void setGameInstance(Game gameInstance) {
        this.gameInstance = gameInstance;
    }

    public boolean nextRound()
    {
        printDay(false);
        return infoMenu(false);
    }

    //shows main round menu
    public boolean infoMenu(boolean fromInside) {
        System.out.println("AKTIONEN - Spiel beenden(a) / Nächste Runde(b) / Auswertungen anzeigen(c) / Entwickler anstellen(d)");
        String input = "";
        //allowed input chars
        String allowed = "abcd";
        //end round if 3 actions have been made, ask for input otherwise
        if(counter >= 3) {
            System.out.println("Keine Aktionen übrig");
            counter = 0;
            printDay(true);
            return true;
        }
        else {
            //repeat until input code is valid
            System.out.println((3 - counter) + " Aktion(en) übrig");
            do {
                input = scan.next();
            } while (!(allowed.contains(input)) || input.length() != 1);
        }

        if(input.equals("a")) {
            System.out.println("------- SPIEL BEENDET -------");
            return false;
        }
        if (input.equals("c")) {
            showAuswertungenMenu();
        }
        if(input.equals("d")) {
            showAnstellenMenu();
        }

        //is used to avoid multiple end day messages if method is called from another menu
        if(!fromInside)
            printDay(true);

        counter = 0;
        return true;
    }

    //prints start/end of current day
    void printDay(boolean end) {
        int spieltag = gameInstance.getDay().getNumber();
        //add "ENDE" to output if end is true, new line otherwise
        String umbruch = "\n\n";
        String endMessage = "";
        if(end) {
            endMessage = "ENDE";
            umbruch = "\n";
        }

        System.out.printf("%s---------- %s SPIELTAG %s ----------\n\n", umbruch, endMessage, spieltag);
    }

    //prints menu items for user to navigate in
    void showAuswertungenMenu() {
        String input = "";
        //repeat until 3 actions are made or user manually returns to main menu
        while(!input.equals("g") && counter < 3) {
            if(counter >= 3) {
                //System.out.println("Keine Aktionen übrig");
                //infoMenu(true);
            }
            else {
                System.out.println((3 - counter) + " Aktion(en) übrig");
                System.out.println("AUSWERTUNGEN - Event Log(a) / Büroübersicht(b) / Entwickler(c) / Projekte(d) / Laufende Kosten(e) / Tage bis Bankrott(f) / Hauptmenü(g)");

                //allowed input chars
                String allowed = "abcdefg";
                //repeat until input code is valid
                do {
                    input = scan.next();
                } while (!allowed.contains(input) || input.length() != 1);

                //output according information
                switch (input) {
                    case "a":
                        //Ausgabe Event Log
                        EventLog log = new EventLog();
                        log.printEventLog(gameInstance);
                        counter++;
                        break;
                    case "b":
                        //Ausgabe Büros
                        List<Office> offices = gameDevStudio.getOffices();
                        for (Office o : offices) {
                            System.out.println("       Name: " + o.getName().getName());
                            System.out.println("          Lease: " + o.getLease());
                        }
                        counter++;
                        break;
                    case "c":
                        //Ausgabe angestellte Entwickler:innen gendern hier ja ist wichtig weil sonst sich die Programmierenden nicht angesprochen fühlen ja
                        offices = gameDevStudio.getOffices();
                        for (Office o : offices) {
                            for (Developer d : o.getDevelopers()) {
                                System.out.println("       Name: " + d.getName().getName());
                                System.out.println("          Gehalt: " + d.getSalary().toString());
                                System.out.println("          Einstellungstag: " + d.getDayOfHire().getNumber());
                                System.out.println("          Coding Skills: " + d.getSkills().getCoding());
                                System.out.println("          Design Skills: " + d.getSkills().getDesign());
                                System.out.println("          Research Skills: " + d.getSkills().getResearch());
                                System.out.println("          Testing Skills: " + d.getSkills().getTesting());
                                try {
                                    System.out.println("          Arbeitet an: " + d.getWorkingOn().getName().getName());
                                } catch (NullPointerException nullPointerException) {
                                    System.out.println("          Zufriedenheit: " + d.getHappiness().get());
                                }
                            }
                        }
                        counter++;
                        break;
                    case "d":
                        //Ausgabe aktive Projekte und Tage bis Projektabschluss
                        ProjectBoard board = gameDevStudio.getProjectBoard();
                        List<Project> projects = board.get();
                        if(projects.size() == 0){
                            System.out.println("       Keine laufenden Projekte \n");
                        }
                        else {
                            for (Project p : projects) {
                                System.out.println("       Projekt: " + p.getName().getName());
                                String daysLeft = Integer.toString(p.getDeadline().getNumber() - gameInstance.getDay().getNumber());
                                System.out.println("          Tage übrig: " + daysLeft);
                            }
                        }
                        counter++;
                        break;
                    case "e":
                        //Ausgabe laufende Kosten
                        System.out.println("       Laufende Kosten: " + calculateCosts(gameDevStudio).toString() + "\n");
                        counter++;
                        break;
                    case "f":
                        //Ausgabe Tage bis Bankrott
                        double daysLeft = gameDevStudio.getCash().getValue().doubleValue() / calculateCosts(gameDevStudio).getValue().doubleValue();
                        String daysLeftString = Double.toString(Math.floor(daysLeft * 10) / 10);
                        System.out.println("       Tage bis zum Bankrott: " + daysLeftString + "\n");
                        counter++;
                        break;
                    case "g":
                        infoMenu(true);
                        break;
                }
            }
        }
    }

    void showAnstellenMenu() {
        System.out.println("ENTWICKLER AUF JOBSUCHE");
        List<Application> applications = gameDevStudio.getApplications();

        for(Application a: applications) {
            System.out.println("       Name: " + a.getDeveloper().getName().getName());
            System.out.println("          Anstellungsgebühr: " + a.getHireAgentFee().getValue().intValue() + "$");
            System.out.println("          Anstellungsbonus: " + a.getHireBonus().getValue().intValue() + "$");
        }

        System.out.println("\n - Anstellen(a) / Hauptmenü(b)");

        String input = "";
        //repeat until input code is valid
        do {
            input = scan.next();
        } while(!(input.equals("a") || input.equals("b")));

        if(input.equals("a")) {
            gameDevStudio.acceptApplication(applications.get(0), gameDevStudio.getOffices().get(0));
            System.out.printf("ENTWICKLER %s ANGESTELLT \n", applications.get(0).getDeveloper().getName().getName());
            counter++;
        }
        infoMenu(true);
    }

    //returns the sum of all office and developer costs
    private Money calculateCosts(GameDevStudio studio) {
        Money total = new Money(new BigDecimal(0));
        //add office leases
        for(Office o: studio.getOffices()) {
            total = total.add(o.getLease());
            //add developer salaries
            for(Developer d: o.getDevelopers()) {
                total = total.add(d.getSalary());
            }
        }
        return total;
    }
}
