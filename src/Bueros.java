import gmbh.kdb.hsw.gdp.domain.GameDevStudio;

public class Bueros {
    public void buerosAusgeben(GameDevStudio studio)
    {
        //prints out the name and monthly rate of lease of the created offices

        for(int i = 0; i < studio.getOffices().size();i++)
        {
            System.out.println("       Name: " + studio.getOffices().get(i).getName().getName());
            System.out.println("          Lease: " + studio.getOffices().get(i).getLease());
        }
    }
}
