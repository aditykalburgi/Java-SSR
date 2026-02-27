import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
class CustomList<T> implements Iterable<T>{

    private List<T> list = new ArrayList<>();

    public void add(T item) {
        list.add(item);
    }

    @Override
    public Iterator<T> iterator(){
        return new SkipIterator();
    }

    public class SkipIterator implements Iterator<T> {

        private int index =0;

        @Override
                public boolean hasNext(){
            return index < list.size();
        }

        @Override
                public T next(){
            if(!hasNext()){
                throw new NoSuchElementException();
            }
            T value = list.get(index);
            index +=2;
            return value;
        }


    }

}



public class CustomForLoopDemo {

    public static void main(String [] args){

        CustomList<String> list = new CustomList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.add("e");
        list.add("f");

        System.out.println("every second element in the list:");

        for(String s : list) {
            System.out.println(s);
        }
    }

}
