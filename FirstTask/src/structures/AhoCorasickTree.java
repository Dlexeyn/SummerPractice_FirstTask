package structures;

import java.util.*;

class borNode {
    boolean is_terminal = false;
    String terminal_line;
    borNode parent_bor_node = null;
    borNode suffix_pointer = null;
    borNode terminal_suffix_pointer = null;
    char parent_symb;
    MyArrayList<borNode> sons_nodes = new MyArrayList<>();
    MyArrayList<borNode> transitions_array = new MyArrayList<>();
}
public class AhoCorasickTree {
    char[] alphabet;
    OutPriorityQueue outPriorityQueue;
    MyArrayList<AnswerPair> answerList;
    borNode root;

    public AhoCorasickTree(char[] alphabet) {
        this.alphabet = alphabet;
        outPriorityQueue = new OutPriorityQueue();
        answerList = new MyArrayList<>();
        root = new borNode();
        root.transitions_array = new MyArrayList<>();
        root.sons_nodes = new MyArrayList<>();
    }

    private int getAlphabetIndex(char sym) {
        for (int index = 0; index < alphabet.length; index++) {
            if (alphabet[index] == sym)
                return index;
        }
        return -1;
    }

    private borNode getSuffixLink(borNode cur_bor_node) {
        if (cur_bor_node.suffix_pointer == null) {
            if (cur_bor_node == root || cur_bor_node.parent_bor_node == root)
                cur_bor_node.suffix_pointer = root;
            else
                cur_bor_node.suffix_pointer = transit_across_sym(getSuffixLink(cur_bor_node.parent_bor_node), cur_bor_node.parent_symb);
        }

        return cur_bor_node.suffix_pointer;
    }

    private borNode getTerminalSuffixLink(borNode cur_bor_node) {
        if (cur_bor_node.terminal_suffix_pointer == null) {
            borNode cur_suffix = getSuffixLink(cur_bor_node);
            if (cur_suffix == root)
                cur_bor_node.terminal_suffix_pointer = root;
            else
                cur_bor_node.terminal_suffix_pointer = (cur_suffix.is_terminal) ? cur_suffix : getTerminalSuffixLink(cur_suffix);
        }
        return cur_bor_node.terminal_suffix_pointer;
    }

    private borNode transit_across_sym(borNode cur_bor_node, char sym) {
        MyArrayList<borNode> cur_transitions = cur_bor_node.transitions_array;
        int sym_index = getAlphabetIndex(sym);
        if (cur_transitions.get(sym_index) == null) {
            if (cur_bor_node.sons_nodes.get(sym_index) != null)
                cur_transitions.set(sym_index, cur_bor_node.sons_nodes.get(sym_index));
            else if (cur_bor_node == root)
                cur_transitions.set(sym_index, root);
            else
                cur_transitions.set(sym_index, transit_across_sym(getSuffixLink(cur_bor_node), sym));
        }
        return cur_transitions.get(sym_index);
    }

    public void addStringtoTree(String line) {
        borNode cur_bor_node = root;
        for (char symb : line.toCharArray()) {
            int symb_index = getAlphabetIndex(symb);
            MyArrayList<borNode> cur_sons = cur_bor_node.sons_nodes;
            if (cur_sons.get(symb_index) == null) {
                cur_sons.set(symb_index, new borNode());
                borNode new_bor_node = cur_sons.get(symb_index);
                new_bor_node.parent_bor_node = cur_bor_node;
                new_bor_node.parent_symb = symb;
                new_bor_node.transitions_array = new MyArrayList<>();
                new_bor_node.sons_nodes = new MyArrayList<>();
            }
            cur_bor_node = cur_sons.get(symb_index);
        }
        cur_bor_node.is_terminal = true;
        cur_bor_node.terminal_line = line;
    }

    public void find_samples(String text, MyArrayList<String> patterns) {
        borNode cur_bor_node = root;
        for (int index = 0; index < text.length(); index++) {
            cur_bor_node = transit_across_sym(cur_bor_node, text.charAt(index));
            borNode i_bor_node = cur_bor_node;
            while (i_bor_node != root) {
                if (i_bor_node.is_terminal)
                    outPriorityQueue.insert(answerList, new AnswerPair(index + 2 - i_bor_node.terminal_line.length(),
                            patterns.find(i_bor_node.terminal_line) + 1));
                i_bor_node = getTerminalSuffixLink(i_bor_node);
            }
        }
    }

    public void printRes() {
        int fixedSize = answerList.size();
        for(int i = 0; i < fixedSize; i++)
        {
            System.out.println(outPriorityQueue.extractMin(answerList).toString());
        }
    }
}
