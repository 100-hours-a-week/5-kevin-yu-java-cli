package godofstock.investor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class Informant {
    public static void getInformation(double[] monthlyPerformance, String rank) {
        System.out.println("══════════════════════════════════════════════════════════════\n");
        switch (rank) {
            case "1" -> System.out.println("안녕하세요. 고급 정보상인 버번입니다. ");
            case "2" -> System.out.println("안녕하세요. 중급 정보상인 아르고입니다. ");
            case "3" -> System.out.println("안녕하세요. 초급 정보상인 프랭클린입니다. ");
        }

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
        System.out.print("어떤 회사의 정보를 알고 싶으신가요? 번호로 알려주세요. >> ");

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            String userInput = br.readLine();

            if (userInput.matches("[123456]")) {
                double result = monthlyPerformance[Integer.parseInt(userInput)];
                double random = new Random().nextDouble();

                // 같은 코드를 수행하지만 확률이 따로 돌아야 하기 때문에 if-else if로 처리
                if (rank.equals("2") && random < 0.2) {
                    result += Math.random() - 0.5;
                } else if (rank.equals("3") && random < 0.4) {
                    result += Math.random() - 0.5;
                }

                reportReuslt(result);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void reportReuslt(double result) {
        if (result >= 0.3) {
            System.out.println("꼭 구매하시길 추천드립니다.");
        } else if (result >= 0.0) {
            System.out.println("나쁘지 않은 선택인 것 같습니다.");
        } else if (result >= -0.3) {
            System.out.println("조금 위험한 선택인 것 같습니다.");
        } else {
            System.out.println("절대 구매하지 않는 걸 추천드립니다.");
        }
        System.out.println("══════════════════════════════════════════════════════════════\n");
    }
}
