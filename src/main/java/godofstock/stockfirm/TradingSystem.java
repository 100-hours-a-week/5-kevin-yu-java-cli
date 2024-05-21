package godofstock.stockfirm;

import godofstock.MessageConst;
import godofstock.MessageTemplate;
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
import godofstock.investor.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import static godofstock.Game.LAST_DAY;
import static godofstock.company.Company.*;


public class TradingSystem {
    private int day = 1;

    private final Company[] companies = new Company[NUMBER_OF_COMPANIES + 1];
    private final Investor[] investors;
    private final HashMap<String, int[]> tradeLogs = new HashMap<>();

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
            tradeLogs.put(investor.getName(), new int[LAST_DAY]);
        }
    }

    public int getDay() {
        return day;
    }

    public void trade() {
        MarketStatus[] marketStatuses = getMarketStatuses();
        double[] monthlyPerformance = getMonthlyPerformance(marketStatuses);

        for (Investor investor : investors) {
            while (true) {
                boolean isPlayer = investor instanceof Player;
                String userInput = null;
                if (isPlayer) {
                    try {
                        System.out.println("═════════════ 취할 행동을 선택해주세요. ═════════════");
                        System.out.println("  1. 개인 능력 사용");
                        System.out.println("  2. 투자할 회사 선택");
                        System.out.println("  3. 넘어가기");
                        System.out.println("═════════════════════════════════════════════════");
                        System.out.print("숫자만 입력해주세요. >> ");

                        userInput = new BufferedReader(new InputStreamReader(System.in)).readLine();
                        if (!userInput.matches("[123]")) {
                            System.out.println(MessageConst.CAUTION_SELECT);
                            continue;
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

                // 투자자 고유 능력 사용 - Player는 선택, NPC는 무조건 사용
                if (!isPlayer || "1".equals(userInput)) {
                    manageAbility(investor, monthlyPerformance);

                    if (isPlayer) continue;
                }

                if (!isPlayer || "2".equals(userInput)) {
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
                    tradeLogs.get(investor.getName())[day - 1] = investmentResult - investmentAmount;
                }
                break;
            }
        }

        monthlyCompanyReport(marketStatuses, monthlyPerformance);
        monthlyInvestorReport();

        day++;
    }

    private void monthlyInvestorReport() {
        System.out.println("""
                
                ╔════════════════════════════════╗
                          투자자 월간 결산
                ╚════════════════════════════════╝
                """);

        for (Investor investor : investors) {
            String name = investor.getName();

            System.out.println("═════════════════════════════════════════════════════════");
            int profit = tradeLogs.get(name)[day - 1];
            System.out.printf("%s: %,d $\n", name, profit);
            System.out.printf("현재 보유 자산: %,d $\n", investor.getBudget());
        }
        System.out.println("═════════════════════════════════════════════════════════");
    }

    private void monthlyCompanyReport(MarketStatus[] marketStatuses, double[] monthlyPerformance) {
        System.out.println("""
                
                ╔════════════════════════════════╗
                            월간 시장 동향
                ╚════════════════════════════════╝
                """);

        System.out.println("═════════════════════════════════════════════════════════════════");
        for (int i = 0; i < NUMBER_OF_MARKETS; i++) {
            String result = switch (marketStatuses[i]) {
                case GREATE_BOOM -> "대호황";
                case BOOM -> "호황";
                case NORMAL -> "일반적인 시기";
                case DEPRESSION -> "불황";
                case GREAT_DEPRESSION -> "대불황";
            };

            switch (i) {
                case CONSTRUCTION -> System.out.println("건설 업계는 금월 " + result + "입니다.");
                case IT -> System.out.println("IT 업계는 금월 " + result + "입니다.");
                case MANUFACTURE -> System.out.println("제조 업계는 금월 " + result + "입니다.");
            }
        }
        System.out.println("══════════════════════════════════════════════════════════════\n");

        for (int i = 1; i <= NUMBER_OF_COMPANIES; i++) {
            String companyName = companies[i].getCompanyName();
            int profit = (int) (monthlyPerformance[i] * 100);
            System.out.println("═════════════════════════════════════════════════════════════════");
            System.out.println(companyName + ": " + profit + "%");
            System.out.println(makeEachCompaniesReport(marketStatuses, i, companyName, profit));
        }
        System.out.println("═════════════════════════════════════════════════════════════════");
    }

    private String makeEachCompaniesReport(MarketStatus[] marketStatuses, int i, String companyName, int profit) {
        String market;
        MarketStatus marketStatus;

        if (companies[i] instanceof ConstructionCompany) {
            market = "건설 업계";
            marketStatus = marketStatuses[CONSTRUCTION];
        } else if (companies[i] instanceof ITCompany) {
            market = "IT 업계";
            marketStatus = marketStatuses[IT];
        } else {
            market = "제조 업계";
            marketStatus = marketStatuses[MANUFACTURE];
        }

        return MessageTemplate.CompanyReportTemplate(market, marketStatus, companyName, profit);
    }

    private void manageAbility(Investor investor, double[] monthlyPerformance) {
        if (investor instanceof Player) {
            investor.ability(monthlyPerformance);
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
