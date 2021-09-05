package ru.job4j.tracker.trackerformockito.action;

import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.model.Item;
import ru.job4j.tracker.trackerformockito.output.Output;
import ru.job4j.tracker.trackerformockito.store.Store;

public class FindByIdAction implements UserAction {

    private final Output output;

    public FindByIdAction(Output output) {
        this.output = output;
    }

    @Override
    public String name() {
        return "Find Item By ID";
    }

    @Override
    public boolean execute(Input input, Store tracker) {
        output.writeConsole("=== Find Item By ID ===");
        int id = input.askInt("Enter id: ");
        Item item = tracker.findById(id);
        if (item != null) {
            output.writeConsole(item.toString());
        } else {
            output.writeConsole(String.format("Item with id=%s not found.", id));
        }
        return true;
    }
}