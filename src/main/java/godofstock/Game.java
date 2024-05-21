package godofstock;

import godofstock.investor.Investor;
import godofstock.investor.Player;
import godofstock.investor.Revenger;
import godofstock.stockfirm.TradingSystem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;

public class Game {
    public static final int NUMBER_OF_PLAYERS = 5;
    public static final int LAST_DAY = 7;

    public static void main(String[] args) throws IOException {
        printTitle();

        boolean loop = true;
        while (loop) {
            System.out.println("""
                    ╔════════════════════════════════╗
                                  메인 메뉴
                    ╚════════════════════════════════╝
                    """);
            System.out.println("1. 게임 시작");
            System.out.println("2. 게임 기록 확인 (추가 예정)");
            System.out.println("3. 게임 종료");

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("숫자만 입력해주세요. >> ");
            String userInput = br.readLine();

            switch (userInput) {
                case "1" -> startGame();
                case "2" -> {}
                case "3" -> loop = false;
                default -> {
                    System.out.println(MessageConst.CAUTION_SELECT);
                }
            }
        }

        System.out.println("""
                
                  ####     ####     ####    #####             #####    ##  ##   ###### \s
                 ######   ######   ######   ######            ##  ##   ##  ##   ###### \s
                 ##       ##  ##   ##  ##   ##  ##            ##  ##   ######   ##     \s
                 ## ###   ##  ##   ##  ##   ##  ##            #####     ####    ####   \s
                 ##  ##   ##  ##   ##  ##   ##  ##            ##  ##     ##     ##     \s
                 ######   ######   ######   ######            ##  ##     ##     ###### \s
                  ####     ####     ####    #####             #####      ##     ###### \s
                """);
    }

    private static void startGame() throws IOException {
        Investor[] investors = initInvestors();
        TradingSystem tradingSystem = new TradingSystem(investors);

        while (tradingSystem.getDay() <= LAST_DAY) {
            System.out.printf("""
                    
                    ╔════════════════════════════════╗
                                 🌤 DAY %d
                    ╚════════════════════════════════╝
                    
                    """, tradingSystem.getDay());
            tradingSystem.trade();
        }

        Arrays.sort(investors, Comparator.comparingInt(Investor::getBudget).reversed());
        System.out.println("""
                ╔════════════════════════════════╗
                         🏆   최종 순위   🏆
                ╚════════════════════════════════╝
                """);

        for (int i = 0; i < NUMBER_OF_PLAYERS; i++) {
            String rank = switch (i) {
                case 0 -> "🥇";
                case 1 -> "🥈";
                case 2 -> "🥉";
                default -> "";
            };

            System.out.printf("""
                    ══════════════════
                    %s %d위 - %s
                    최종 자산: %d
                    """, rank, i + 1, investors[i].getName(), investors[i].getBudget());
        }

        System.out.println("══════════════════\n");
    }

    private static Investor[] initInvestors() throws IOException {
        Investor[] investors = new Investor[NUMBER_OF_PLAYERS];

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("게임에서 사용할 닉네임을 입력해주세요. >> ");
        String userName = br.readLine();

        investors[0] = new Player(userName);
        investors[1] = new Revenger();
        investors[2] = new Revenger();
        investors[3] = new Revenger();
        investors[4] = new Revenger();

        return investors;
    }

    private static void printTitle() {
        System.out.println("""
                \s
                                                                                                                                                                                                                      \s
                      ,o888888o.        ,o888888o.     8 888888888o.                    ,o888888o.     8 8888888888                d888888o.   8888888 8888888888     ,o888888o.         ,o888888o.    8 8888     ,88'\s
                     8888     `88.   . 8888     `88.   8 8888    `^888.              . 8888     `88.   8 8888                    .`8888:' `88.       8 8888        . 8888     `88.      8888     `88.  8 8888    ,88' \s
                  ,8 8888       `8. ,8 8888       `8b  8 8888        `88.           ,8 8888       `8b  8 8888                    8.`8888.   Y8       8 8888       ,8 8888       `8b  ,8 8888       `8. 8 8888   ,88'  \s
                  88 8888           88 8888        `8b 8 8888         `88           88 8888        `8b 8 8888                    `8.`8888.           8 8888       88 8888        `8b 88 8888           8 8888  ,88'   \s
                  88 8888           88 8888         88 8 8888          88           88 8888         88 8 888888888888             `8.`8888.          8 8888       88 8888         88 88 8888           8 8888 ,88'    \s
                  88 8888           88 8888         88 8 8888          88           88 8888         88 8 8888                      `8.`8888.         8 8888       88 8888         88 88 8888           8 8888 88'     \s
                  88 8888   8888888 88 8888        ,8P 8 8888         ,88           88 8888        ,8P 8 8888                       `8.`8888.        8 8888       88 8888        ,8P 88 8888           8 888888<      \s
                  `8 8888       .8' `8 8888       ,8P  8 8888        ,88'           `8 8888       ,8P  8 8888                   8b   `8.`8888.       8 8888       `8 8888       ,8P  `8 8888       .8' 8 8888 `Y8.    \s
                     8888     ,88'   ` 8888     ,88'   8 8888    ,o88P'              ` 8888     ,88'   8 8888                   `8b.  ;8.`8888       8 8888        ` 8888     ,88'      8888     ,88'  8 8888   `Y8.  \s
                      `8888888P'        `8888888P'     8 888888888P'                    `8888888P'     8 8888                    `Y8888P ,88P'       8 8888           `8888888P'         `8888888P'    8 8888     `Y8.\s
                               \s
                               \s
                               \s
                \s""");

        System.out.println("""
                ╔══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗
                   이 게임은 4명의 NPC와 주식 투자 대결을 벌이는 게임입니다. 각 회사의 주가는 실제 주식과 무관하며 랜덤하게 배정됩니다. 플레이어와 각 NPC는 개인 능력을 가지고 시작합니다. 당신의 운과 실력을 시험해보세요!
                ╚══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╝
                """);
    }
}
