package ovs.graph;

import java.util.ArrayList;
import java.util.Collections;

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

    public void remove(int index){
        list.remove(index);
        for (int i = 0; i < changedListener.size(); i++) {
            changedListener.get(i).onChanged();
        }
    }

    public ArrayList<E> getList()
    {
        return list;
    }

    public void setList(ArrayList<E> list){
        this.list = list;
    }

    public void triggerOnChanged(){
        for (int i = 0; i < changedListener.size(); i++) {
            changedListener.get(i).onChanged();
        }
    }

    public void swap(int i, int j){
        Collections.swap(getList(), i, j);
        triggerOnChanged();
    }

    public boolean swap(E a, E b){
        int _a = -1;
        int _b = -1;

        for (int i = 0; i < list.size(); i++) {

            if(list.get(i) == a){
                _a = i;
            }

            if(list.get(i) == b){
                _b = i;
            }

            if(_a != -1 && _b != -1){
                break;
            }
        }

        if(_a != -1 && _b != -1){
            swap(_a, _b);
            return true;
        }

        return false;
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

    public void set(int index, E value){
        list.set(index, value);
    }
}
