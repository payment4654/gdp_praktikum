import gmbh.kdb.hsw.gdp.Game;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        //acts like an interface for the application with the most important stats for the current game

        new Scanner(System.in);

        MainMenu menu = new MainMenu();
        Game gameInstance = Game.create(gameDevStudio -> {
            menu.setStudio(gameDevStudio);
            System.out.println(gameDevStudio.getCash());
            int happy = 0;
            int happyCount = 0;
            for(int i = 0; i < gameDevStudio.getOffices().size();i++)
            {
                for(int j = 0; j < gameDevStudio.getOffices().get(i).getDevelopers().size();j++) {
                    happy += gameDevStudio.getOffices().get(i).getDevelopers().get(j).getHappiness().get();
                    happyCount++;
                }
            }
            System.out.println("Durchschnittliche Zufriedenheit: " + (double)happy/(double)happyCount);

            return menu.nextRound();
        });

        menu.setGameInstance(gameInstance);
        gameInstance.start();
    }
}

