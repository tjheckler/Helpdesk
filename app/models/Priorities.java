package models;

import java.util.Map;
import java.util.TreeMap;

public class Priorities
{
    private Map<Integer, Priority> priorities;
    public Priorities()
    {
        priorities = new TreeMap<>();

        Priority one =  new Priority(1, "High");
        Priority two =  new Priority(2, "Medium");
        Priority three =  new Priority(3, "Low");
        priorities.put(one.getPriorityId(),one);
        priorities.put(two.getPriorityId(),two);
        priorities.put(three.getPriorityId(),three);
    }

    public Map<Integer, Priority> getPriorities()
    {
        return priorities;
    }
}
