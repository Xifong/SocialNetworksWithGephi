package graphgame.utilities;

import java.util.ArrayList;
import java.util.List;

public class ListSlice<T> {

    private List<T> list;
    private List<T> slice;
    private int start;
    private int end;

    public ListSlice(List<T> list, int start, int end){
        this.list = list;
        this.start = start;
        this.end = end;
    }

    public List<T> getSlice(){
        if(slice == null)
            actuallyGetSlice();
        return slice;
    }

    private void actuallyGetSlice(){
        slice = new ArrayList<>();
        for(int i = start; i < end; ++i){
            slice.add(list.get(i));
        }
    }
}
