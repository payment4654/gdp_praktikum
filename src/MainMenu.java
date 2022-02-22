import gmbh.kdb.hsw.gdp.Game;
import gmbh.kdb.hsw.gdp.domain.*;

public class MainMenu extends MenuAdapter {

    AuswertungenMenu auswertungenMenu;
    AnstellenMenu anstellenMenu;
    GameDevStudioAdapter gameDevStudioAdapter;
    DeveloperAdapter developerAdapter = new DeveloperAdapter();
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

    public boolean startRound() {
        printDay(false, gameInstance);
        return showMainRoundMenu(false);
    }

    public boolean showMainRoundMenu(boolean fromInside) {
        System.out.println(
                "AKTIONEN - Spiel beenden(a) / Nächste Runde(b) / Auswertungen anzeigen(c) / Entwickler anstellen(d)");
        String allowedInputChars = "abcd";
        String input = getInput(allowedInputChars);
        boolean areNoActionsLeft = checkIfNoActionsAreLeft(input, counter, gameInstance);
        if (areNoActionsLeft) {
            return true;
        } else {
            chooseActionHauptmenu(input);
        }
        // is used to avoid multiple end day messages if method is called from another
        // menu
        if (!fromInside)
            printDay(true, gameInstance);

        resetCounter(counter);
        return true;
    }

    private boolean chooseActionHauptmenu(String input) {

        switch (input) {
            case "a":
                System.out.println("------- SPIEL BEENDET -------");
                return false;

            case "c":
                auswertungenMenu = new AuswertungenMenu(gameInstance, gameDevStudio);
                auswertungenMenu.showAuswertungenMenu(counter);
                return true;

            case "d":
                anstellenMenu = new AnstellenMenu(gameInstance, gameDevStudio);
                anstellenMenu.showAnstellenMenu(counter);
                return true;

            default:
                return true;
        }
    }

}
