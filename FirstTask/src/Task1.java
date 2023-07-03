import structures.*;
import java.util.Scanner;

public class Task1 {
    public static void main(String[] args) {
        char[] alphabet = new char[]{'A', 'C', 'G', 'T', 'N'};
        AhoCorasickTree solver_tree = new AhoCorasickTree(alphabet);
        Scanner scanner = new Scanner(System.in);
        String text = scanner.next();
        int pattern_num = scanner.nextInt();
        MyArrayList<String> patterns = new MyArrayList<>();
        for (int i = 0; i < pattern_num; i++) {
            String temp_line = scanner.next();
            solver_tree.addStringtoTree(temp_line);
            patterns.add(temp_line);
        }

        solver_tree.find_samples(text, patterns);
        solver_tree.printRes();
    }
}
