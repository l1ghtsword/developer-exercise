package net.gameslabs.api;

public class Event {
    private boolean cancelled;

    public Event() {
        //Empty Constructor
    }

    //used to check if event instance is canceled
    public boolean isCancelled() {
        return cancelled;
    }

    //used to cancel event instance
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
