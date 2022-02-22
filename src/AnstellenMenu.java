import java.util.List;

import gmbh.kdb.hsw.gdp.Game;
import gmbh.kdb.hsw.gdp.domain.Application;
import gmbh.kdb.hsw.gdp.domain.GameDevStudio;

public class AnstellenMenu extends MenuAdapter {

    private GameDevStudio gameDevStudio;
    private MainMenu mainMenu;

    public AnstellenMenu(Game gameInstance, GameDevStudio gameDevStudio) {
        this.gameDevStudio = gameDevStudio;
    }

    public void showAnstellenMenu(int counter) {
        System.out.println("ENTWICKLER AUF JOBSUCHE");
        List<Application> applications = gameDevStudio.getApplications();
        String allowedInputChars = "ab";

        printDeveloperMarket(applications);

        System.out.println("\n - Anstellen(a) / Hauptmenü(b)");

        checkInputAnstellen(applications, getInput(allowedInputChars), counter);
        mainMenu.showMainRoundMenu(true);
    }

    private void printDeveloperMarket(List<Application> applications) {
        for (Application a : applications) {
            System.out.println("       Name: " + a.getDeveloper().getName().getName());
            System.out.println("          Anstellungsgebühr: " + a.getHireAgentFee().getValue().intValue() + "$");
            System.out.println("          Anstellungsbonus: " + a.getHireBonus().getValue().intValue() + "$");
        }
    }

    private void checkInputAnstellen(List<Application> applications, String input, int counter) {
        if (input.equals("a")) {
            gameDevStudio.acceptApplication(applications.get(0), gameDevStudio.getOffices().get(0));
            System.out.printf("ENTWICKLER %s ANGESTELLT \n", applications.get(0).getDeveloper().getName().getName());
            incrementCounter(counter);
        }
    }

}
