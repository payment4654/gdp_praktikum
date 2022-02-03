import gmbh.kdb.hsw.gdp.Game;
import gmbh.kdb.hsw.gdp.IGameHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        //acts like an interface for the application with the most important stats for the current game
            getStreamExkurs();
            
            
            MainMenu menu = new MainMenu();
            Game gameInstance = Game.create(setGameDevStudioConfig(menu));
            menu.setGameInstance(gameInstance);
            gameInstance.start();
        
    }

    private static IGameHandler setGameDevStudioConfig(MainMenu menu) {
        return gameDevStudio -> {
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
            

            return menu.startRound();
        };
    }

    private static void getStreamExkurs(){
        Integer[] zahlen = {1, 2, 3, 4};

        for (int element : zahlen) {
            System.out.print(element);
        }
        System.out.println();
        for (int aktuelleZahl : zahlen) {
            System.out.print(aktuelleZahl);
        }
        System.out.println();

        List<Integer> zahlen2 = new ArrayList<>(Arrays.asList(zahlen));


        zahlen2.forEach(System.out::println);


        List<Integer> neueListVonZahlen = 
        zahlen2.stream()
            .map(zahl -> zahl*2)
            .filter(zahl -> zahl > 3)
            .collect(Collectors.toList());
        System.out.println(neueListVonZahlen);
    }
}

