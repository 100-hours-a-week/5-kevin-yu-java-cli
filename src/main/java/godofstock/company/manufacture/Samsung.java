package godofstock.company.manufacture;

import godofstock.company.MarketStatus;

public class Samsung extends ManufactureCompany {
    @Override
    public double performance(MarketStatus marketStatus) {
        return (Math.round((Math.random() - 0.5) * 10)) / 10.0;
    }

    @Override
    public String getCompanyName() {
        return "삼성전자";
    }
}
