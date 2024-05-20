package godofstock.stockfirm;

import godofstock.company.Company;
import godofstock.company.MarketStatus;
import godofstock.company.construction.ConstructionCompany;
import godofstock.company.construction.Hyundai;
import godofstock.company.construction.Posco;
import godofstock.company.it.ITCompany;
import godofstock.company.it.Kakao;
import godofstock.company.it.Naver;
import godofstock.company.manufacture.LG;
import godofstock.company.manufacture.Samsung;
import godofstock.investor.Investor;

import java.util.HashMap;
import java.util.LinkedList;

import static godofstock.company.Company.*;


public class TradingSystem {
    private int day = 1;

    private final Company[] companies = new Company[NUMBER_OF_COMPANIES + 1];
    private final Investor[] investors;
    private final HashMap<String, LinkedList<Integer>> tradeLogs = new HashMap<>();

    public TradingSystem(Investor[] investors) {
        this.investors = investors;
        initCompanyList();
        initTradeLogs(investors);
    }

    private void initCompanyList() {
        companies[HYUNDAI] = new Hyundai();
        companies[POSCO] = new Posco();
        companies[KAKAO] = new Kakao();
        companies[NAVER] = new Naver();
        companies[LG] = new LG();
        companies[SAMSUNG] = new Samsung();
    }

    private void initTradeLogs(Investor[] investors) {
        for (Investor investor : investors) {
            tradeLogs.put(investor.getName(), new LinkedList<>());
        }
    }

    public int getDay() {
        return day;
    }

    public void trade() {
        MarketStatus[] marketStatuses = getMarketStatuses();
        double[] monthlyPerformance = getMonthlyPerformance(marketStatuses);

        for (Investor investor : investors) {
            // 투자자 고유 능력 사용 - Player는 선택, NPC는 무조건 사용
            investor.ability();
            // 투자할 회사 선택
            int companyId = investor.choiceCompany();
            // 투자할 금액 선택
            int investmentAmount = investor.investment();
            // 수익률 계산식: 회사 수익율 * 투자 금액
            // 회사 수익률: 1 + 회사 실적 + 시장 현황
            double performanceScore = 1 + monthlyPerformance[companyId];
            int investmentResult = (int) (performanceScore * investmentAmount);
            // 투자자 수익률 반영
            investor.balancingAccount(investmentResult, companyId);
            // 투자자 수익률 기록
            tradeLogs.get(investor.getName()).add(investmentResult - investmentAmount);
        }

        monthlyCompanyReport(marketStatuses, monthlyPerformance);
        monthlyInvestorReport();

        day++;
    }

    private void monthlyInvestorReport() {
        System.out.println("투자자별 월간 결산");
        for (String name : tradeLogs.keySet()) {
            int profit = tradeLogs.get(name).get(day - 1);
            System.out.println(name + ": " + String.format("%,d", profit));
        }
    }

    private void monthlyCompanyReport(MarketStatus[] marketStatuses, double[] monthlyPerformance) {
        System.out.println("기업별 월간 결산");
        for (int i = 0; i < NUMBER_OF_MARKETS; i++) {
            String result = switch (marketStatuses[i]) {
                case GREATE_BOOM -> "대호황";
                case BOOM -> "호황";
                case NORMAL -> "일반적인 시기";
                case DEPRESSION -> "불황";
                case GREAT_DEPRESSION -> "대불황";
            };

            switch (i) {
                case CONSTRUCTION -> System.out.println("건설 업계는 금월 " + result + "을 겪었습니다.");
                case IT -> System.out.println("IT 업계는 금월 " + result + "을 겪었습니다.");
                case MANUFACTURE -> System.out.println("제조 업계는 금월 " + result + "을 겪었습니다.");
            }
        }

        for (int i = 1; i <= NUMBER_OF_COMPANIES; i++) {
            String companyName = companies[i].getCompanyName();
            int profit = (int) (monthlyPerformance[i] * 100);
            System.out.println(companyName + ": " + profit + "%");
        }
    }

    private double[] getMonthlyPerformance(MarketStatus[] marketStatuses) {
        double[] monthlyPerformance = new double[NUMBER_OF_COMPANIES + 1];
        for (int i = 1; i <= NUMBER_OF_COMPANIES; i++) {
            Company company = companies[i];
            MarketStatus marketStatus;

            if (company instanceof ConstructionCompany)
                marketStatus = marketStatuses[CONSTRUCTION];
            else if (company instanceof ITCompany)
                marketStatus = marketStatuses[IT];
            else
                marketStatus = marketStatuses[MANUFACTURE];

            monthlyPerformance[i] = company.performance(marketStatus);
        }
        return monthlyPerformance;
    }

    private MarketStatus[] getMarketStatuses() {
        MarketStatus[] marketStatuses = new MarketStatus[NUMBER_OF_MARKETS];
        marketStatuses[CONSTRUCTION] = Company.marketStatus();
        marketStatuses[IT] = Company.marketStatus();
        marketStatuses[MANUFACTURE] = Company.marketStatus();
        return marketStatuses;
    }
}
