import structures.ACTreeWithJoker;
import java.util.Scanner;

public class Task2 {
    public static void main(String[] args) {
        char[] alphabet = new char[]{'A', 'C', 'G', 'T', 'N'};
        ACTreeWithJoker solver_tree = new ACTreeWithJoker(alphabet);
        String text, pattern;
        char joker;
        Scanner scanner = new Scanner(System.in);
        text = scanner.nextLine();
        pattern = scanner.nextLine();
        joker = scanner.nextLine().charAt(0);

        solver_tree.convertPatternToBor(pattern, joker);
        solver_tree.findAnswer(text, pattern);
    }
}
