package ru.job4j.tracker.trackerformockito.action;

import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.model.Item;

import ru.job4j.tracker.trackerformockito.output.Output;
import ru.job4j.tracker.trackerformockito.store.Store;
import ru.job4j.tracker.trackerformockito.store.Tracker;

public class ReplaceAction implements UserAction {
    private final Output output;

    public ReplaceAction(Output output) {
        this.output = output;
    }

    @Override
    public String name() {
        return "Edit Item";
    }

    @Override
    public boolean execute(Input input, Store tracker) {
        output.writeConsole("=== Edit item ===");
        int id = input.askInt("Enter id: ");
        String name = input.askStr("Enter new name for Item: ");
        Item item = new Item(name);
        if (tracker.replace(id, item)) {
            output.writeConsole("Edit item is done.");
        } else {
            output.writeConsole(String.format("Item with id=%s not found.", id));
        }
        return true;
    }
}
