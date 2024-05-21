package godofstock.investor;


import godofstock.MessageConst;
import godofstock.company.Company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static godofstock.company.Company.NUMBER_OF_COMPANIES;


public class Player extends Investor {
    private int investAmount;

    public Player(String name) {
        setName(name);
    }

    @Override
    public int choiceCompany() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            while (true) {
                System.out.println("""
                        
                        ╔═════════════════════╗
                                회사 목록
                        ╠═════════════════════╣
                              1. 현대건설
                              2. 포스코건설
                              3. 카카오
                              4. 네이버
                              5. LG전자
                              6. 삼성전자
                        ╚═════════════════════╝""");
                System.out.print("투자할 회사의 번호를 선택해주세요. >> ");

                String userInput = br.readLine();
                if (!userInput.matches("[0-9]*")) {
                    System.out.println(MessageConst.CAUTION_NUMBER);
                    continue;
                }

                int companyId = Integer.parseInt(userInput);
                if (companyId < 0 || NUMBER_OF_COMPANIES < companyId) {
                    System.out.println(MessageConst.CAUTION_SELECT);
                    continue;
                }

                return companyId;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int investment() {
        while (true) {
            System.out.printf("현재 잔고: %,d $\n", getBudget());
            System.out.print("투자할 금액을 입력해주세요. >> ");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            try {
                String userInput = br.readLine();
                if (!userInput.matches("[0-9]*")) {
                    System.out.println(MessageConst.CAUTION_NUMBER);
                    continue;
                }

                int amount = Integer.parseInt(userInput);
                if (amount > getBudget()) {
                    System.out.println(MessageConst.CAUTION_BUDGET);
                    continue;
                }

                investAmount = amount;
                return amount;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void balancingAccount(int money, int companyId) {
        setBudget(getBudget() + (money - investAmount));
    }

    @Override
    public void ability(double[] performances) {
        try {
            while (true) {
                System.out.println("""
                        
                        ╔════════════════════════════════╗
                                     정보 구매
                        ╚════════════════════════════════╝
                        """);

                System.out.println("1. 고급 정보상: 항상 올바른 정보만 알려줍니다. (비용: 3,000 $)");
                System.out.println("2. 중급 정보상: 대부분 올바른 정보를 알려줍니다. 가끔 틀린 정보를 알려줍니다. (비용: 1,500 $)");
                System.out.println("3. 초급 정보상: 보통 올바른 정보를 알려줍니다. 하지만 틀린 정보인 경우도 꽤 있습니다. (비용: 500 $)");
                System.out.println("4. 나가기");
                System.out.printf("현재 잔고: %,d $\n", getBudget());
                System.out.print("원하는 정보상의 번호를 입력해주세요. >> ");

                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                String userInput = br.readLine();

                if (!userInput.matches("[1234]")) {
                    System.out.println(MessageConst.CAUTION_SELECT);
                    continue;
                }

                if (userInput.equals("4")) break;

                switch (userInput) {
                    case "1" -> setBudget(getBudget() - 3000);
                    case "2" -> setBudget(getBudget() - 1500);
                    case "3" -> setBudget(getBudget() - 500);
                }
                Informant.getInformation(performances, userInput);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
