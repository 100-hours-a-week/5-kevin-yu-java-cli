package godofstock.investor;

import java.util.Random;

import static godofstock.company.Company.SAMSUNG;

public class CEO extends NPC {
    public CEO() {
        setName("재드래곤");
        setBudget(75_000);
    }

    @Override
    public int choiceCompany() {
        return SAMSUNG;
    }

    @Override
    public int investment() {
        return switch (new Random().nextInt(10) + 1) {
            case 1, 2, 3, 4 -> calculate(0.4);
            case 5, 6, 7 -> calculate(0.3);
            case 8, 9 -> calculate(0.5);
            case 10 -> calculate(0.8);
            default -> 0;
        };
    }

    @Override
    public void balancingAccount(int money, int companyId) {
        int result = money - getInvestAmount();
        setBudget(getBudget() + result);
    }

    @Override
    public void ability(double[] performances) {
        if (performances[SAMSUNG] < 0.0 && new Random().nextDouble() < 0.75) {
            performances[SAMSUNG] /= 2;
            System.out.println("""
                ╔══════════════════════════════════════════════════════════════╗
                       재드래곤이 SNS에서 사람들의 호감을 사 손실이 줄어들었습니다.
                ╚══════════════════════════════════════════════════════════════╝
                """);
        }
    }
}
