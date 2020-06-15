package net.gameslabs.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ComponentRegistry {

    //Declare list object to store component objects in order of priority
    private List<Component> componentList;

    //Constructor method to initialize "componentList" ArrayList on class init
    public ComponentRegistry() {
        componentList = new ArrayList<>();
    }

    //Method which receives passed Component objects and add's them to the list of Component obj's to be iterated through
    public void registerComponent(Component component) {
        componentList.add(component);
    }


    public void sendEvent(Event event) {
        //The coolest line in this entire project, i learned something new today...
        //in short, use of the stream() method to chain together a sequence of functions to collect and sort events into a list from the ComponentList Obj
        //really neat
        List<EventMethod> methods = componentList.stream().map(component -> component.getEvents(event)).flatMap(Collection::stream).collect(Collectors.toList());

        //Iterate through the list object of events -I added a try/catch
        for (EventMethod eventMethod : methods) {

            // "Would require proper handling in a production environment"
            //Agreed, just set a generic try/catch but is rather vague and potentially not very useful
            //Added to prevent hard crash
            try {
                eventMethod.onExecute(event);
                if (event.isCancelled()) { break; }
            } catch (Exception e) { e.printStackTrace(); }
        }
    }

    /**
     * Loads all components
     */
    public void load() {
        componentList.forEach(component -> component.initialize(this));
        componentList.forEach(Component::onLoad);
    }

    /**
     * Unloads all components
     */
    public void unload() {
        componentList.forEach(Component::onUnload);
    }
}
