package ru.job4j.tracker.trackerformockito.action;

import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.trackerformockito.output.Output;
import ru.job4j.tracker.trackerformockito.store.Store;


public class DeleteAction implements UserAction {

    private final Output output;

    public DeleteAction(Output output) {
        this.output = output;
    }

    @Override
    public String name() {
        return "Delete Item";
    }

    @Override
    public boolean execute(Input input, Store tracker) {
        output.writeConsole("=== Delete Item ===");
        int id = input.askInt("Enter id: ");
        if (tracker.delete(id)) {
            output.writeConsole("Item was deleted.");
        } else {
            output.writeConsole(String.format("Item with id=%s not found.", id));
        }
        return true;
    }
}
