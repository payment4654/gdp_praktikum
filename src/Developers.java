import gmbh.kdb.hsw.gdp.domain.GameDevStudio;

import java.sql.SQLOutput;

public class Developers {
    public void developersAusgeben(GameDevStudio studio)
    {
        //prints out the stats of the developers in the terminal

        for(int i = 0; i < studio.getOffices().size();i++)
        {
            for(int j = 0; j < studio.getOffices().get(i).getDevelopers().size();j++) {
                System.out.println("       Name: "+ studio.getOffices().get(i).getDevelopers().get(j).getName().getName());
                System.out.println("          Gehalt: "+ studio.getOffices().get(i).getDevelopers().get(j).getSalary().getValue());
                System.out.println("          Einstellungstag: " +studio.getOffices().get(i).getDevelopers().get(j).getDayOfHire().getNumber());
                System.out.println("          Coding Skills: " + studio.getOffices().get(i).getDevelopers().get(j).getSkills().getCoding());
                System.out.println("          Design Skills: " + studio.getOffices().get(i).getDevelopers().get(j).getSkills().getDesign());
                System.out.println("          Research Skills: " + studio.getOffices().get(i).getDevelopers().get(j).getSkills().getResearch());
                System.out.println("          Testing Skills: " + studio.getOffices().get(i).getDevelopers().get(j).getSkills().getTesting());
                try {
                        System.out.println("          Arbeitet an: " + studio.getOffices().get(i).getDevelopers().get(j).getWorkingOn().getName().getName());
                }catch (NullPointerException nullPointerException) {
                    System.out.println("          Glücklichkeits: " + studio.getOffices().get(i).getDevelopers().get(j).getHappiness().get());
                }
            }
        }
    }
}