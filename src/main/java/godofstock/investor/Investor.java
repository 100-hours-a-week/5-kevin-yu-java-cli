package godofstock.investor;

public abstract class Investor {
    private String name;
    private int budget = 50_000; // 초기 자금

    protected void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    protected void setBudget(int budget) {
        this.budget = budget;
    }

    public int getBudget() {
        return budget;
    }

    public abstract int choiceCompany();

    public abstract int investment();

    public abstract void balancingAccount(int money, int companyId);

    public abstract void ability(Object obj);
}
