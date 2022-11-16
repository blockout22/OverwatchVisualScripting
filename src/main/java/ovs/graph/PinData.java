package ovs.graph;

public class PinData<E> {

    public E value;

    public void setValue(E value){
        this.value = value;
    }

    public E getValue(){
        return value;
    }
}
