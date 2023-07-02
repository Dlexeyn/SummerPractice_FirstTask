package structures;

import java.util.*;

class Node {
    boolean is_terminal = false;
    String terminal_line;
    Node parent_node = null;
    Node suffix_pointer = null;
    Node terminal_suffix_pointer = null;
    char parent_symb;
    MyArrayList<Node> sons_nodes;
    MyArrayList<Node> transitions_array;
}
public class Aho_Corasick_tree {
    List<Character> alphabet;
    OutPriorityQueue outPriorityQueue;

    MyArrayList<AnswerPair> myArrayList;
    Node root;

    public Aho_Corasick_tree(List<Character> alphabet) {
        this.alphabet = alphabet;
        outPriorityQueue = new OutPriorityQueue();
        myArrayList = new MyArrayList<>();
        root = new Node();
        root.transitions_array = new MyArrayList<>();
        root.sons_nodes = new MyArrayList<>();
    }

    private int getAlphabetIndex(char sym) {
        for (int index = 0; index < alphabet.size(); index++) {
            if (alphabet.get(index) == sym)
                return index;
        }
        return -1;
    }

    private Node getSuffixLink(Node cur_node) {
        if (cur_node.suffix_pointer == null) {
            if (cur_node == root || cur_node.parent_node == root)
                cur_node.suffix_pointer = root;
            else
                cur_node.suffix_pointer = transit_across_sym(getSuffixLink(cur_node.parent_node), cur_node.parent_symb);
        }

        return cur_node.suffix_pointer;
    }

    private Node getTerminalSuffixLink(Node cur_node) {
        if (cur_node.terminal_suffix_pointer == null) {
            Node cur_suffix = getSuffixLink(cur_node);
            if (cur_suffix == root)
                cur_node.terminal_suffix_pointer = root;
            else
                cur_node.terminal_suffix_pointer = (cur_suffix.is_terminal) ? cur_suffix : getTerminalSuffixLink(cur_suffix);
        }
        return cur_node.terminal_suffix_pointer;
    }

    private Node transit_across_sym(Node cur_node, char sym) {
        MyArrayList<Node> cur_transitions = cur_node.transitions_array;
        int sym_index = getAlphabetIndex(sym);
        if (cur_transitions.get(sym_index) == null) {
            if (cur_node.sons_nodes.get(sym_index) != null)
                cur_transitions.set(cur_node.sons_nodes.get(sym_index), sym_index);
            else if (cur_node == root)
                cur_transitions.set(root, sym_index);
            else
                cur_transitions.set(transit_across_sym(getSuffixLink(cur_node), sym), sym_index);
        }
        return cur_transitions.get(sym_index);
    }

    public void addStringtoTree(String line) {
        Node cur_node = root;
        for (char symb : line.toCharArray()) {
            int symb_index = getAlphabetIndex(symb);
            MyArrayList<Node> cur_sons = cur_node.sons_nodes;
            if (cur_sons.get(symb_index) == null) {
                cur_sons.set(new Node(), symb_index);
                Node new_node = cur_sons.get(symb_index);
                new_node.parent_node = cur_node;
                new_node.parent_symb = symb;
                new_node.transitions_array = new MyArrayList<>();
                new_node.sons_nodes = new MyArrayList<>();
            }
            cur_node = cur_sons.get(symb_index);
        }
        cur_node.is_terminal = true;
        cur_node.terminal_line = line;
    }

    public void find_samples(String text, Map<String, Integer> patterns) {
        Node cur_node = root;
        for (int index = 0; index < text.length(); index++) {
            cur_node = transit_across_sym(cur_node, text.charAt(index));
            Node i_node = cur_node;
            while (i_node != root) {
                if (i_node.is_terminal)
                    outPriorityQueue.insert(myArrayList, new AnswerPair(index + 2 - i_node.terminal_line.length(),
                            patterns.get(i_node.terminal_line)));
                i_node = getTerminalSuffixLink(i_node);
            }
        }
    }

    public void printRes() {
        int fixedSize = myArrayList.size();
        for(int i = 0; i < fixedSize; i++)
        {
            System.out.println(outPriorityQueue.extractMin(myArrayList).toString());
        }
    }
}
