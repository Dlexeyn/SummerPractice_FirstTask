import java.util.*;
import structures.*;

public class Task1 {
    public static void main(String[] args) {
        List<Character> alphabet = Arrays.asList('A', 'C', 'G', 'T', 'N');
        Aho_Corasick_tree solver_tree = new Aho_Corasick_tree(alphabet);
        Scanner scanner = new Scanner(System.in);
        String text = scanner.next();
        int pattern_num = scanner.nextInt();
        Map<String, Integer> patterns = new HashMap<>();
        for (int i = 0; i < pattern_num; i++) {
            String temp_line = scanner.next();
            solver_tree.addStringtoTree(temp_line);
            patterns.put(temp_line, i + 1);
        }

        solver_tree.find_samples(text, patterns);
        solver_tree.printRes();
    }
}
