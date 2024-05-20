package godofstock.investor;

import java.util.Random;

import static godofstock.company.Company.NUMBER_OF_COMPANIES;

public abstract class NPC extends Investor {
    @Override
    public int choiceCompany() {
        return new Random().nextInt(NUMBER_OF_COMPANIES) + 1;
    }
}
