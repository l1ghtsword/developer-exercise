package net.gameslabs.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Collections;


/**
 * An abstract component that can listen and register events
 */
public abstract class Component {

    //Declare registeredEvents in memory implementing the Map interface
    private Map<Class, List<EventMethod>> registeredEvents;
    //Declare ComponentRegistry, Registry obj in memory
    private ComponentRegistry registry;
    //Initialize registeredEvents as a HashMap Obj
    public Component() {
        registeredEvents = new HashMap<>();
    }

    //registers event by adding it to the registeredEvents HashMap Obj
    public final <T extends Event> void registerEvent(Class<T> eventType, EventMethod<T> method) {
        //hashmap computeIfAbsent to check for null or pre-existing key (no duplicate events to be registered)
        //If no duplicate event found, then add event to ArrayList
        registeredEvents.computeIfAbsent(eventType, e -> new ArrayList<>()).add(method);
    }

    //get registered events
    public final List<EventMethod> getEvents(Event event) {
        //never returns null, will wither return only valid Map as if no events are registered, a default empty map is sent instead
        return registeredEvents.getOrDefault(event.getClass(), Collections.emptyList());
    }

    /**
     * Send an event
     * @param event
     */
    protected final void send(Event event) {
        registry.sendEvent(event);
    }

    public final void initialize(ComponentRegistry registry) {
        this.registry = registry;
    }

    /**
     * Called when the component loads
     */
    public abstract void onLoad();

    /**
     * Called when the component unloads
     */
    public abstract void onUnload();
}
