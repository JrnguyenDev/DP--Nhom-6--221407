package domain.observer;

import java.util.LinkedList;
import java.util.List;

public class Publisher{

    private List<Subcriber> subcribers = new LinkedList<>();

    //method
    public void subcribe(Subcriber s) {
        subcribers.add(s);
    }

    public void unSubcribe(Subcriber s) {
        subcribers.remove(s);
    }

    public void notifySubcribers() 
    {
        for (Subcriber subcriber : subcribers) {
            subcriber.update();
        }
        
    }
}
