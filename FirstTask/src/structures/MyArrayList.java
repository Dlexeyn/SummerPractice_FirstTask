package structures;

public class MyArrayList<T> {
    private final int INITIAL_CAPACITY = 10;
    private final int CUT_CAPACITY = 4;
    private int current_size = 0;
    private Object[] array;

    public MyArrayList() {
        array = new Object[INITIAL_CAPACITY];
    }

    public MyArrayList(int size)
    {
        array = new Object[size];
    }

    public void add(T item)
    {
        if(current_size == array.length - 1)
            resize(array.length * 2);
        array[current_size++] = item;
    }

    public void add(int index, T item)
    {
        if(current_size == array.length - 1)
            resize(array.length * 2);
        for(int i = current_size - 1; i >= index; i--)
            array[i + 1] = array[i];
        array[index] = item;
        current_size++;
    }

    public void fill(int start, int end, T item)
    {
        for (int i = start; i < end; i++)
        {
            add(i, item);
        }
    }

    public int size()
    {
        return current_size;
    }

    public Object set(int index, Object item)
    {
        if(index < array.length && index >= 0)
        {
            Object o = array[index];
            array[index] = item;
            return o;
        }
        return null;
    }

    public T get(int index)
    {
        return (T) array[index];
    }

    public void remove(int index)
    {
        if(current_size == 0)
            return;
        for (int i = index; i < current_size; i++)
            array[i] = array[i + 1];
        array[current_size--] = null;

        if(current_size < array.length / CUT_CAPACITY && array.length > INITIAL_CAPACITY)
            resize(array.length / 2);
    }

    int find(T item)
    {
        for (int i = 0; i < current_size; i++)
        {
            if(item.equals((T) array[i]))
                return i;
        }
        return -1;
    }

    private void resize(int newLength) {
        Object[] newArray = new Object[newLength];
        System.arraycopy(array, 0, newArray, 0, current_size);
        array = newArray;
    }

    @Override
    public String toString()
    {
        if (array.length == 0)
            return null;
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < current_size; i++)
        {
            builder.append((T) array[i].toString());
            builder.append('\n');
        }
        return builder.toString();
    }
}
