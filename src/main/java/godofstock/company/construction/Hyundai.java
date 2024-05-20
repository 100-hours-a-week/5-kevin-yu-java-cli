package godofstock.company.construction;

import godofstock.company.MarketStatus;

public class Hyundai extends ConstructionCompany {
    @Override
    public double performance(MarketStatus marketStatus) {
        return (Math.round((Math.random() - 0.5) * 10)) / 10.0;
    }

    @Override
    public String getCompanyName() {
        return "현대건설";
    }
}
