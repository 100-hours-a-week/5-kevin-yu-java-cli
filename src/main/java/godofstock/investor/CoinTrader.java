package godofstock.investor;

import java.util.Random;

public class CoinTrader extends NPC {
    public CoinTrader() {
        setName("멜론 머스크");
    }

    @Override
    public int investment() {
        return switch(new Random().nextInt(10) + 1) {
            case 1, 2, 3, 4 -> calculate(0.7);
            case 5, 6, 7 -> calculate(0.5);
            case 8, 9 -> calculate(0.4);
            case 10 -> calculate(1.0);
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
        double bitcoin = 0.3 + (2.0 - 0.3) * new Random().nextDouble();
        bitcoin = Math.round(bitcoin * 100.0) / 100.0;
        System.out.printf("""
                ╔══════════════════════════════════════════════════════════════════════════╗
                     비트코인 가격이 변동으로 인해 멜론 머스크의 재산에 변동이 생겼습니다. (%,d %%)
                ╚══════════════════════════════════════════════════════════════════════════╝
                """, (int) (bitcoin * 100));
        setBudget((int) (getBudget() * bitcoin));
    }
}
