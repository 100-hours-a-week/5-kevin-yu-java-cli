package godofstock.investor.npc;

import java.util.Random;

public class Revenger extends NPC {
    private int targetId;
    private int maxLoss;

    public Revenger() {
        setName("존 윅");
    }

    @Override
    public int investment() {
        return switch(new Random().nextInt(10) + 1) {
            case 1, 2, 3, 4 -> calculate(0.4);
            case 5, 6, 7 -> calculate(0.6);
            case 8, 9 -> calculate(0.2);
            case 10 -> calculate(1.0);
            default -> 0;
        };
    }

    @Override
    public void balancingAccount(int money, int companyId) {
        int result = money - getInvestAmount();
        if (result < maxLoss) {
            maxLoss = result;
            targetId = companyId;
        }
        setBudget(getBudget() + result);
    }

    @Override
    public void ability(double[] performances) {
        if (targetId == 0) return;

        double percentage = new Random().nextDouble();
        if (percentage < 0.1) {
            performances[targetId] = -0.8;
            System.out.println("""
                ╔══════════════════════════════════════════════════════════════╗
                       존 윅이 한 회사의 시설을 공격해 주가를 크게 떨어뜨렸습니다.
                ╚══════════════════════════════════════════════════════════════╝
                """);
        }
    }
}
