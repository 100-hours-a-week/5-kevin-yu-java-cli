package godofstock.investor;


import godofstock.MessageConst;

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
                        ╚═════════════════════╝
                        """);
                System.out.print(MessageConst.INPUT_COMPANY);

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
            System.out.print(MessageConst.INPUT_AMOUNT);
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
    public void ability() {

    }
}
