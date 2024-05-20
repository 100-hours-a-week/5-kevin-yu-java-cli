package godofstock.company.it;

import godofstock.company.MarketStatus;

public class Naver extends ITCompany {
    @Override
    public double performance(MarketStatus marketStatus) {
        return (Math.round((Math.random() - 0.5) * 10)) / 10.0;
    }

    @Override
    public String getCompanyName() {
        return "네이버";
    }
}
