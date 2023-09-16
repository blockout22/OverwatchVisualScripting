package ovs.graph;

import java.util.ArrayList;
import java.util.HashMap;

public class AdvancedMap<K, V> extends HashMap<K, V> {

    private ArrayList<ListChangedListener> listChangedListener = new ArrayList<>();

    public void addChangeListener(ListChangedListener listChangedListener){
        this.listChangedListener.add(listChangedListener);
    }

    public void triggerOnChanged(){
        for (int i = 0; i < listChangedListener.size(); i++) {
            listChangedListener.get(i).onChanged();
        }
    }

    @Override
    public V put(K key, V value) {
        V v = super.put(key, value);
        for (int i = 0; i < listChangedListener.size(); i++) {
            listChangedListener.get(i).onChanged();
        }
        return v;
    }

    @Override
    public boolean remove(Object key, Object value) {
        boolean val = super.remove(key, value);
        for (int i = 0; i < listChangedListener.size(); i++) {
            listChangedListener.get(i).onChanged();
        }
        return val;
    }

    @Override
    public V remove(Object key) {
        V v = super.remove(key);
        for (int i = 0; i < listChangedListener.size(); i++) {
            listChangedListener.get(i).onChanged();
        }
        return v;
    }
}
