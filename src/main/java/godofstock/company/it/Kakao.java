package godofstock.company.it;

import godofstock.company.MarketStatus;

public class Kakao extends ITCompany {
    @Override
    public double performance(MarketStatus marketStatus) {
        return (Math.round((Math.random() - 0.5) * 10)) / 10.0;
    }

    @Override
    public String getCompanyName() {
        return "카카오";
    }
}
