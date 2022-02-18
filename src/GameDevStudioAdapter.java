import java.math.BigDecimal;

import gmbh.kdb.hsw.gdp.domain.Developer;
import gmbh.kdb.hsw.gdp.domain.GameDevStudio;
import gmbh.kdb.hsw.gdp.domain.Money;
import gmbh.kdb.hsw.gdp.domain.Office;

public class GameDevStudioAdapter {

    private GameDevStudio gameDevStudio;

    public GameDevStudioAdapter(GameDevStudio gameDevStudio) {
        this.gameDevStudio = gameDevStudio;
    }

    public double calculateDaysLeft() {
        double daysLeft = gameDevStudio.getCash().getValue().doubleValue()
                / calculateOfficeAndDeveloperCosts(gameDevStudio).getValue().doubleValue();
        daysLeft = Math.floor(daysLeft * 10) / 10;
        return daysLeft;
    }

    public Money calculateOfficeAndDeveloperCosts(GameDevStudio studio) {
        Money total = new Money(new BigDecimal(0));
        total.add(addOfficeLeasesAndDeveloperSalaries(studio));
        return total;
    }

    private Money addOfficeLeasesAndDeveloperSalaries(GameDevStudio studio) {
        Money officeCosts = new Money(new BigDecimal(0));
        for (Office o : studio.getOffices()) {
            officeCosts = o.getLease();
            officeCosts.add(addDeveloperSalaries(o));
        }
        return officeCosts;
    }

    private Money addDeveloperSalaries(Office o) {
        Money developerCosts = new Money(new BigDecimal(0));
        for (Developer d : o.getDevelopers()) {
            developerCosts.add(d.getSalary());
        }
        return developerCosts;
    }

}
