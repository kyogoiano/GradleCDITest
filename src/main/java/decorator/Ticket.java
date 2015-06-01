package decorator;

/**
 * Created by andrade on 13/04/15.
 */
public class Ticket {
    private String name;
    public Ticket(String name) {
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}
