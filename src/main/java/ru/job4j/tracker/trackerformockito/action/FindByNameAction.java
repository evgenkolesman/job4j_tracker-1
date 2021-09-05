package ru.job4j.tracker.trackerformockito.action;

import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.model.Item;
import ru.job4j.tracker.trackerformockito.output.Output;
import ru.job4j.tracker.trackerformockito.store.Store;

import java.util.List;

public class FindByNameAction implements UserAction{
    private Output output;

    public FindByNameAction(Output output) {
        this.output = output;
    }

    @Override
    public String name() {
        return "Find item By Name";
    }

    @Override
    public boolean execute(Input input, Store tracker) {
        output.writeConsole("=== Find item By Name ===");
        String name = input.askStr("Enter name: ");
        List<Item> items = tracker.findByName(name);
        if (items.size() > 0) {
            for (int i = 0; i < items.size(); i++) {
                output.writeConsole(items.get(i).toString());
            }
        } else {
            output.writeConsole(String.format("Item with name=%s not found.", name));
        }
        return true;
    }
}
