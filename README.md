#Revision
This branch is a refactor of the original assignment submitted in the master branch.
This program implements a factory design pattern that is central to item management.
I have also spent time reviewing area's i could improve code quality, specifically 
in the <b> Cooking Skill Component.</b>

- Nested Else If logic has been removed in many areas
- The use of OOL concepts such as inheritance and polymorphism has been implemented
- All Skills have been refactored to disallow any hard coded constructors assigning values
- All "Item" objects inherit the createInstanceOf method which allows items to reference 
themselves or other Item objects to create a new instance of the selected object dynamically
without knowing its class before runtime.


# Developer l1ghtsword
Welcome to the GamesLabs developer assignment. The goal of this assignment is to test your ability to use :
- git
- maven
- component and event based programming
- java programming
- code quality
- follow vague descriptions

## Requirements
Before starting this assignment, you will need to have :
- java 8+
- maven 3+
- git

# The assignment
You will need to do different tasks. You may need to change the original code in order to complete these tasks.


# Component based project
A component is a piece of code handling one feature, and only one feature.
It can interact with other components through events.
A component must extends the `net.gameslabs.api.Component` class.

## Component cycle
When a component is loaded, the `onLoad` is called. You can register events in this method by doing so:
```java
@Override
public void onLoad() {
    registerEvent(GiveXpEvent.class, this::onGiveXPToPlayer);
}

private void onGiveXPToPlayer(GiveXpEvent event) {
    // Do something
}
```
The `onUnload` method is called when the component unloads.

## Events
You may send an event by using the `Component.send` method:
```java
private void onGiveXPToPlayer(GiveXpEvent event) {
    int previousLevel = ...
    int nextLevel = ...
    if (previousLevel != nextLevel) {
        send(new PlayerGainLevelEvent(event.getPlayer(), event.getSkill(), nextLevel));
    }
}
```
Events go through components in the order they're loaded in (i.e. priority). If an event is cancelled, it will not go through lower priority components.

## Main structure (Provided by GamesLabs)
Here is the list of the main packages of this project:
- **net.gameslabs.api** This package contains mostly back-end code
- **net.gameslabs.components** Main packages which register and listen to events
- **net.gameslabs.events**  A package containing the different events.
- **net.gameslabs.exception** This package contains the special exception thrown when Assignment runchecks() fails to meet criteria
- **net.gameslabs.implem** This package contains the class which implements the Player interface, used when making new player objects
- **net.gameslabs.model** A package containing all the objects that will be instanced or enum classes referee to by other classes

## Assignment.class
- **net.gameslabs.model.objects.assignment** This is where most of your code will be made. assignment.Main is the main class to execute

## L1ghtsword code structure
- **ca.braelor.l1ghtsword.assignment.components** - Package containing component classes registered by main. They register and listen to events depending on their functions and are the only way to interact with the back-end code from the front end. Events must be sent to cause desired operations to happen.
- **ca.braelor.l1ghtsword.assignment.events** - Package containing the events to be sent to component listeners. Events can be called by Assignment.class or by other components when something needs to be checked or an operation needs to be performed.
- **ca.braelor.l1ghtsword.assignment.exception** - A package containing my many different runtime exceptions that happen as a result of an unalloyed or undesirable operation. Often used in combination with catch statements to make logical decisions based on the exception being thrown.
- **ca.braelor.l1ghtsword.assignment.model** - This package contains all my model objects to be instanced or enum lists to be referred by other Classes 
- **ca.braelor.l1ghtsword.assignment.utils** - This package contains my util class. It is a toolbox of sorts for common operations that are not component specific, such as converting a Item and Quantity into a ItemData object, or checking if an item is stackable.

## CookingComponent (Unique Feature)
If you have read through the runChecks or looked at some of the functions in this project, there are many unique features that were not asked for such as:
- Items can be stackable
- Some items can be used and do something when used
- Many small checks or changes associated with fatures implemented in addition to requested criteria
<br><br>
The "Unique feature" you /are/ actually requesting will be the cooking SKill.
The CookingComponent will manage a map if items are are cookable to objects which contain information about that food item
such as burn chance, what you get when item is burned or cooked, what level you must be to cook this food, etc.
<br><br>
The skill is pretty straight forward in logic.<br><br>
- An event to cook an item will be received by the CookingComponent event listener.
- The listener will first check the play has the item being cooked in the players inventory.
- If there is one, it will then check if that item is coockable by testing it in the util.class
- If util returns true, players level will then be checked against that items cooking level requirements.
- If player has a high enough level, the cookItem method will be called with that food's burn chance
- A random number between 1 and 100 will be generated and matched against the burn chance, if successful item will be cooked
- When cooked, the cookedItem is referenced from the Food object and put in the players inventory AFTER the raw food is removed. 
XP will also be rewarded, referenced by the Food object.
- If cook failed, the Food object burntItem is referenced instead and given to the player AFTER removing the raw food first. No XP will be rewarded.
- The event will now be cancelled.
<br><br>
Of course, if any of the initial criteria fails, the cook event will be cancelled and no cooking will be attempted.



## run() method in assignments
This is a virtual example of "runtime" where a player would be able to interact with game elements like getting an item, mining a rock, cooking a chicken, etc.
There are several lines executed one after another and are somewhat self explanatory using log() to send friendly menages to console as operations are being preformed.

## runChecks() method in assignments
In the Assignments class, there is a method called runChecks(), at the end of all the run() body code.
If the criteria is not met there will be an AssignmentFailed exception thrown which will crash the program as it is uncaught.
The checks and their explanations are as follows:

- **if (getLevel(mainPlayer, Skill.EXPLORATION) != 1)**<br>
This will confirm the mainPlayer has level 1 Exploration by sending a GetPlayerLevelEvent to check.<br><br>

- **if (getLevel(mainPlayer, Skill.CONSTRUCTION) != 2)**<br>
This will confirm the mainPlayer has level 2 Exploration by sending a GetPlayerLevelEvent to check.<br><br>

- **if (getItem(someOtherPlayer, Item.COINS).getQuantity() != 1000)**<br>
This will send a GetItemEvent to confirm mainPlayer has 1000 coins somewhere in their inventory<br><br>

- **if (getLevel(mainPlayer, Skill.MINING) != 6)**<br>
This will confirm the mainPlayer has level 6 Mining by sending a GetPlayerLevelEvent to check.<br><br>

- **hasItem(mainPlayer, Item.BURNT_SHRIMP) )**<br>
This check CAN fail, as it depends on a 10% burn chance of shrimp. It will tell you to run the application again if you are unlucky... <br><br>


## The main class
The Main class: - **ca.braelor.l1ghtsword.assignment.Main.class**
```java
public static void main(String[] args) {
    new Assignment(
        new MyXPBoosterComponent(),
        new ChartComponent(),
        new InventoryComponent(),
        new MiningComponent(),
        new CookingComponent()
    ).run();
```
The assignment class takes components in its constructor. Components are loaded in order.

# The assignment
Here is the list of tasks you need to achieve:
- Edit the MyXPBoosterComponent to enable DXP in the construction skill
- Add an inventory component in charge of giving, checking if a player has a given item and removing a given item.
    In order to complete this task, you may need to edit pre-existing code.
    Add new checks in Assignment.java to run checks on the features you have added.
- Add a mining skill and component with different ores. A player should only be able to mine coal at level 5 (meaning a, event needs to be cancelled according to his mining level). A player should receive xp from mining an ore.
    Add new checks in Assignment.java to run checks on the features you have added.
- Add a unique feature and add this feature to the README with a description of what it is supposed to do and checks in Assignment.

## Publishing
Fork this repository and add scipio3000 to the repository. PM scipio3000 with the link to your repository once you are done with the assignment
