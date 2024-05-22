package godofstock.investor.npc;

import godofstock.investor.Investor;

import java.util.Random;

import static godofstock.company.Company.NUMBER_OF_COMPANIES;

public abstract class NPC extends Investor {
    private int investAmount;

    public int getInvestAmount() {
        return investAmount;
    }

    @Override
    public int choiceCompany() {
        return new Random().nextInt(NUMBER_OF_COMPANIES) + 1;
    }

    int calculate(double percentage) {
        investAmount = (int) (getBudget() * percentage);
        return investAmount;
    }
}
