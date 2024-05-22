package godofstock.company;

import java.util.Random;

import static godofstock.company.MarketStatus.*;

public interface Company {
    int NUMBER_OF_MARKETS = 3;
    int NUMBER_OF_COMPANIES = 6; // 회사 개수

    int CONSTRUCTION = 0;
    int IT = 1;
    int MANUFACTURE = 2;

    int HYUNDAI = 1;
    int POSCO = 2;
    int KAKAO = 3;
    int NAVER = 4;
    int LG = 5;
    int SAMSUNG = 6;

    double performance(MarketStatus marketStatus);

    String getCompanyName();

    static MarketStatus marketStatus() {
        return switch (new Random().nextInt(10) + 1) {
            case 1 -> GREAT_BOOM;
            case 2, 3 -> BOOM;
            case 4, 5, 6, 7 -> NORMAL;
            case 8, 9 -> DEPRESSION;
            case 10 -> GREAT_DEPRESSION;
            default -> null;
        };
    }
}
