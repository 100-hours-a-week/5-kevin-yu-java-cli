package godofstock.investor;

import java.util.Random;

public class Revenger extends NPC {
    private int investAmount;
    private int targetId;
    private int maxLoss;

    public Revenger() {
        setName("존 윅");
    }

    @Override
    public int investment() {
        return switch(new Random().nextInt(10) + 1) {
            case 1, 2, 3, 4 -> calculate(0.3);
            case 5, 6, 7 -> calculate(0.2);
            case 8, 9 -> calculate(0.1);
            case 10 -> calculate(1.0);
            default -> 0;
        };
    }

    private int calculate(double percentage) {
        investAmount = (int) (getBudget() * percentage);
        return investAmount;
    }

    @Override
    public void balancingAccount(int money, int companyId) {
        int result = money - investAmount;
        if (result < maxLoss) {
            maxLoss = result;
            targetId = companyId;
        }
        setBudget(getBudget() + result);
    }

    @Override
    public void ability() {
        if (targetId == 0) return;

        double percentage = new Random().nextDouble();
        if (percentage < 0.1) {

        }
    }
}
