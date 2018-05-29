package models;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.Map;
import java.util.TreeMap;

public class Tickets
{
    private Map<Integer, Ticket> tickets;
    public Tickets()
    {
        tickets = new TreeMap<>();

        Ticket one =  new Ticket(1, 1,
                3,3,"2018-01-01","John","Jones",
                "you@me.com",5015551234L,3,2,
                "wxda",210210210L,
                "Broken PC","PC doesnt work",2,3);
        Ticket two =  new Ticket(1, 1,
                3,3,"2018-01-01","John","Jones",
                "you@me.com",5015551234L,3,2,
                "wxda",210210210L,
                "Broken PC","PC doesnt work",2,3);
        Ticket three =  new Ticket(1, 1,
                3,3,"2018-01-01","John","Jones",
                "you@me.com",5015551234L,3,2,
                "wxda",210210210L,
                "Broken PC","PC doesnt work",2,3);
       tickets.put(one.getTicketsId(),one);
        tickets.put(two.getTicketsId(),two);
        tickets.put(three.getTicketsId(),three);
    }

    public Map<Integer, Ticket> getTickets()
    {
        return tickets;
    }
}
