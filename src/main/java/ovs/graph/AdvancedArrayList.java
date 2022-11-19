package ovs.graph;

import java.util.ArrayList;

public class AdvancedArrayList<E> {

    private ArrayList<E> list = new ArrayList<>();

    private ArrayList<ListChangedListener> changedListener = new ArrayList<>();

    public void add(E e){
        list.add(e);
        for (int i = 0; i < changedListener.size(); i++) {
            changedListener.get(i).onChanged();
        }
    }

    public void remove(E e){
        list.remove(e);
        for (int i = 0; i < changedListener.size(); i++) {
            changedListener.get(i).onChanged();
        }
    }

    public void addListChangedListener(ListChangedListener listChangedListener){
        this.changedListener.add(listChangedListener);
    }

    public int size(){
        return list.size();
    }

    public E get(int index){
        return list.get(index);
    }
}
