package godofstock;

import godofstock.company.MarketStatus;

public class MessageTemplate {
    private static final String GREAT = "기록적인 수익을 냈습니다.";
    private static final String GOOD = "좋은 수익율을 기록했습니다.";
    private static final String COMMON = "무난한 한 달을 보냈습니다.";
    private static final String BAD = "적자를 기록했습니다.";
    private static final String WORSE = "엄청난 손해를 입었습니다.";

    public static String CompanyReportTemplate(String market, MarketStatus marketStatus, String companyName, double performance) {
        StringBuilder sb = new StringBuilder();

        sb.append(companyName).append("은(는) ").append(market);

        switch (marketStatus) {
            case GREATE_BOOM, BOOM -> {
                if (performance >= 50.0) {
                    sb.append("의 호황에 힙입어, ").append(GREAT);
                } else if (performance >= 20.0) {
                    sb.append("의 호황에 힙입어, ").append(GOOD);
                } else if (performance >= 0.0) {
                    sb.append("의 호황 덕분에, ").append(COMMON);
                } else if (performance >= -20.0) {
                    sb.append("의 호황에도 불구하고, ").append(BAD);
                } else  {
                    sb.append("의 호황에도 불구하고, ").append(WORSE);
                }
            }
            case NORMAL -> {
                if (performance >= 50.0) {
                    sb.append("가 평이한 와중에, ").append(GREAT);
                } else if (performance >= 20.0) {
                    sb.append("가 평이한 와중에, ").append(GOOD);
                } else if (performance >= 0.0) {
                    sb.append("가 무난한 시기를 보냄에 따라, ").append(COMMON);
                } else if (performance >= -20.0) {
                    sb.append("가 평이했음에도 불구하고, ").append(BAD);
                } else {
                    sb.append("가 평이했음에도 불구하고, ").append(WORSE);
                }
            }
            case GREAT_DEPRESSION, DEPRESSION -> {
                if (performance >= 50.0) {
                    sb.append("의 불황에도 불구하고, ").append(GREAT);
                } else if (performance >= 20.0) {
                    sb.append("의 불황에도 불구하고, ").append(GOOD);
                } else if (performance >= 0.0) {
                    sb.append("의 불황에도, 다행히 ").append(COMMON);
                } else if (performance >= -20.0) {
                    sb.append("의 불황으로 인해, ").append(BAD);
                } else {
                    sb.append("의 불황으로 인해, ").append(WORSE);
                }
            }
        }

        return sb.toString();
    }
}
