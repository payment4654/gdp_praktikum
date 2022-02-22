import java.util.List;

import gmbh.kdb.hsw.gdp.domain.Developer;
import gmbh.kdb.hsw.gdp.domain.GameDevStudio;
import gmbh.kdb.hsw.gdp.domain.Office;

public class DeveloperAdapter{

    

    private GameDevStudio gameDevStudio;

    //TODO in gameDevStudioAdapter auslagern
    public void printDevelopers() {
        List<Office> offices = gameDevStudio.getOffices();
        for (Office office : offices) {
            //TODO trennen
            for (Developer developer : office.getDevelopers()) {
                printDevelopersInfo(developer);
                tryHappiness(developer);
            }
        }
    }

    // Try Catch aus printDevelopers ausgelagert
    private void tryHappiness(Developer developer) {
        try {
            System.out
                    .println("          Arbeitet an: " + developer.getWorkingOn().getName().getName());
        } catch (NullPointerException nullPointerException) {
            System.out.println("          Zufriedenheit: " + developer.getHappiness().get());
        }
    }

    // Ausgabe Entwicklerinfos aus printDevelopers ausgelagert
    private void printDevelopersInfo(Developer developer) {
        System.out.println("       Name: " + developer.getName().getName());
        System.out.println("          Gehalt: " + developer.getSalary().toString());
        System.out.println("          Einstellungstag: " + developer.getDayOfHire().getNumber());
        System.out.println("          Coding Skills: " + developer.getSkills().getCoding());
        System.out.println("          Design Skills: " + developer.getSkills().getDesign());
        System.out.println("          Research Skills: " + developer.getSkills().getResearch());
        System.out.println("          Testing Skills: " + developer.getSkills().getTesting());
    }


}