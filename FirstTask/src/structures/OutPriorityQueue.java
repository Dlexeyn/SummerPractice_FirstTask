package structures;

class OutPriorityQueue {
    // Function to heapify the tree
    void heapify(MyArrayList<AnswerPair> hT, int i) {
        int size = hT.size();
        // Find the largest among root, left child and right child
        int largest_index = i;
        int leftIndex = 2 * i + 1;
        int rightIndex = 2 * i + 2;
        if (leftIndex < size && hT.get(leftIndex) != null && compare(hT.get(leftIndex), hT.get(largest_index)))
            largest_index = leftIndex;
        if (rightIndex < size && hT.get(rightIndex) != null &&compare(hT.get(rightIndex), hT.get(largest_index)))
            largest_index = rightIndex;

        // Swap and continue heapifying if root is not largest
        if (largest_index != i) {
            AnswerPair temp = hT.get(largest_index);
            hT.set(largest_index, hT.get(i));
            hT.set(i, temp);

            heapify(hT, largest_index);
        }
    }

    // Function to insert an element into the tree
    void insert(MyArrayList<AnswerPair> hT, AnswerPair newPair) {
        int size = hT.size();
        if (size == 0) {
            hT.add(newPair);
        } else {
            hT.add(newPair);
            int averageIndex = (size > 1) ? size / 2 - 1 : 0;
            for (int i = averageIndex; i >= 0; i--) {
                heapify(hT, i);
            }
        }
    }

    // Function to delete an element from the tree
    void deleteNode(MyArrayList<AnswerPair> hT, AnswerPair pair) {
        int size = hT.size();
        int i;
        for (i = 0; i < size; i++) {
            if (pair == hT.get(i))
                break;
        }

        AnswerPair temp = hT.get(i);
        hT.set(i, hT.get(size - 1));
        hT.set(size - 1, temp);

        hT.remove(size - 1);
        for (int j = size / 2 - 1; j >= 0; j--) {
            heapify(hT, j);
        }
    }

    AnswerPair extractMin(MyArrayList<AnswerPair> hT)
    {
        if(hT.size() == 0)
            return null;
        AnswerPair minPair = hT.get(0);
        if(hT.size() > 1)
        {
            AnswerPair lastPair = hT.get(hT.size() - 1);
            hT.set(0, lastPair);
            hT.remove(hT.size() - 1);
            heapify(hT, 0);
        }
        return minPair;
    }

    private boolean compare(AnswerPair p1, AnswerPair p2)
    {
        if(p1.getTextPosition() != p2.getTextPosition())
            return p1.getTextPosition() < p2.getTextPosition();
        else
            return p1.getListNumber() <= p2.getListNumber();
    }

}
