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
        Investor[] investors = new Investor[NUMBER_OF_PLAYERS];

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("성함을 입력해주세요. >> ");
        String userName = br.readLine();

        investors[0] = new Player(userName);
        investors[1] = new Revenger();

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
}
