import gmbh.kdb.hsw.gdp.Game;

public class EventLog {
    //outputs the event log on the console
    public void printEventLog(Game gameInstance) {
        System.out.println("EVENTS - ");
        for(String s: gameInstance.getEventLog()) {
            System.out.println("       " + s);
        }
    }
}
