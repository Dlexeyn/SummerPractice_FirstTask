package structures;

public class AnswerPair {

    public AnswerPair(int textPosition, int listNumber)
    {
        this.textPosition = textPosition;
        this.listNumber = listNumber;
    }

    public int getTextPosition() {
        return textPosition;
    }

    public int getListNumber() {
        return listNumber;
    }

    private final int textPosition;
    private final int listNumber;

    @Override
    public String toString()
    {

        return String.valueOf(textPosition + " " + listNumber);
    }
    
}
