import java.util.List;

import gmbh.kdb.hsw.gdp.domain.GameDevStudio;
import gmbh.kdb.hsw.gdp.domain.Office;

public class OfficeAdapter {




    public void printOffices(GameDevStudio gameDevStudio) {
        List<Office> offices = gameDevStudio.getOffices();
        for (Office office : offices) {
            System.out.println("       Name: " + office.getName().getName());
            System.out.println("          Lease: " + office.getLease());
        }
    }
    
}