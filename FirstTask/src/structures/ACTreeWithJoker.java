package structures;
class Bor_Node {
    boolean is_terminal = false;
    String terminal_line;
    Bor_Node parent_node = null;
    Bor_Node suffix_pointer = null;
    Bor_Node terminal_suffix_pointer = null;
    char symbol;
    MyArrayList<Integer> text_positions = new MyArrayList<>();
    MyArrayList<Bor_Node> sons_nodes;
    MyArrayList<Bor_Node> transitions_array;
}

public class ACTreeWithJoker {
    char[] alphabet;
    MyArrayList<Pair<Bor_Node, Integer>> bor_substrings;
    MyArrayList<Integer> search_array;
    Bor_Node root;

    public ACTreeWithJoker(char[] alphabet) {
        this.alphabet = alphabet;
        root = new Bor_Node();
        root.transitions_array = new MyArrayList<>(alphabet.length);
        root.sons_nodes = new MyArrayList<>(alphabet.length);
        bor_substrings = new MyArrayList<>();
    }

    int getAlphabetIndex(char sym) {
        for (int index = 0; index < alphabet.length; index++) {
            if (alphabet[index] == sym)
                return index;
        }
        return -1;
    }

    Bor_Node getSuffixLink(Bor_Node cur_node) {
        if (cur_node.suffix_pointer == null) {
            if (cur_node == root || cur_node.parent_node == root)
                cur_node.suffix_pointer = root;
            else
                cur_node.suffix_pointer = transit_across_sym(getSuffixLink(cur_node.parent_node),
                        cur_node.symbol);
        }

        return cur_node.suffix_pointer;
    }

    Bor_Node getTerminalSuffixLink(Bor_Node cur_node) {
        if (cur_node.terminal_suffix_pointer == null) {
            Bor_Node cur_suffix = getSuffixLink(cur_node);
            if (cur_suffix == root)
                cur_node.terminal_suffix_pointer = root;
            else
                cur_node.terminal_suffix_pointer = (cur_suffix.is_terminal)
                        ? cur_suffix : getTerminalSuffixLink(cur_suffix);
        }
        return cur_node.terminal_suffix_pointer;
    }

    Bor_Node transit_across_sym(Bor_Node cur_node, char sym) {
        MyArrayList<Bor_Node> cur_transitions = cur_node.transitions_array;
        int sym_index = getAlphabetIndex(sym);
        if (cur_transitions.get(sym_index) == null) {
            if (cur_node.sons_nodes.get(sym_index) != null)
                cur_transitions.set(sym_index, cur_node.sons_nodes.get(sym_index));
            else if (cur_node == root)
                cur_transitions.set(sym_index, root);
            else
                cur_transitions.set(sym_index, transit_across_sym(getSuffixLink(cur_node), sym));
        }
        return cur_transitions.get(sym_index);
    }

    public void convertPatternToBor(String pattern, char wildcard) {
        String substring = "";
        int index;
        for (index = 0; index < pattern.length(); index++) {
            if (pattern.charAt(index) != wildcard)
                substring += pattern.charAt(index);
            else if (!substring.isEmpty()) {
                addStringToTree(substring, index - substring.length() + 1);
                substring = "";
            }
        }
        if (!substring.isEmpty())
            addStringToTree(substring, index - substring.length() + 1);
    }

    void addStringToTree(String line, int pattern_position) {
        Bor_Node cur_node = root;
        for (int index = 0; index < line.length(); index++) {
            int symb_index = getAlphabetIndex(line.charAt(index));
            MyArrayList<Bor_Node> cur_sons = cur_node.sons_nodes;
            if (cur_sons.get(symb_index) == null) {
                cur_sons.set(symb_index, new Bor_Node());
                cur_sons.get(symb_index).parent_node = cur_node;
                cur_sons.get(symb_index).symbol = line.charAt(index);
                cur_sons.get(symb_index).transitions_array = new MyArrayList<>(alphabet.length);
                cur_sons.get(symb_index).sons_nodes = new MyArrayList<>(alphabet.length);
            }
            cur_node = cur_sons.get(symb_index);
        }
        cur_node.is_terminal = true;
        cur_node.terminal_line = line;
        bor_substrings.add(new Pair<>(cur_node, pattern_position));
    }

    void find_samples(String text) {
        Bor_Node cur_node = root;
        int finded_index;
        for (int index = 0; index < text.length(); index++) {
            cur_node = transit_across_sym(cur_node, text.charAt(index));
            Bor_Node i_node = cur_node;
            while (i_node != root) {
                if (i_node.is_terminal) {
                    finded_index = index - i_node.terminal_line.length() + 2;
                    i_node.text_positions.add(finded_index);
                }
                i_node = getTerminalSuffixLink(i_node);
            }
        }
    }

    public void findAnswer(String text, String pattern) {
        find_samples(text);
        search_array = new MyArrayList<>(text.length());
        int substrings_count = bor_substrings.size();
        search_array.fill(0, text.length(), 0);
        for (int i = 0; i < bor_substrings.size(); i++) {
            Pair<Bor_Node, Integer> cur_subsring_node = bor_substrings.get(i);
            for(int j = 0; j < cur_subsring_node.first.text_positions.size(); j++) {
                int index = cur_subsring_node.first.text_positions.get(j) - cur_subsring_node.second + 1;
                if (index >= 0 && text.length() >= pattern.length() + index - 1)
                    search_array.set(index, search_array.get(index) + 1);
            }
        }

        for (int index = 0; index < search_array.size(); index++) {
            if (search_array.get(index) == substrings_count)
                System.out.println(index);
        }
    }
}