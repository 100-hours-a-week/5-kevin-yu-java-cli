package godofstock.company.it;

import godofstock.company.Company;
import godofstock.company.MarketStatus;

public abstract class ITCompany implements Company {
    @Override
    public double performance(MarketStatus marketStatus) {
        double marketPerformance = switch (marketStatus) {
            case GREAT_BOOM -> 0.2;
            case BOOM -> 0.1;
            case DEPRESSION -> -0.1;
            case GREAT_DEPRESSION -> -0.2;
            default -> 0.0;
        };
        double companyPerformance = (Math.round((Math.random() - 0.5) * 10)) / 10.0;

        return marketPerformance + companyPerformance;
    }
}
