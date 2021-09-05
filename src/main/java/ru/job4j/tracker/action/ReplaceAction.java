package ru.job4j.tracker.action;

import ru.job4j.tracker.model.Item;
import ru.job4j.tracker.trackerformockito.output.Output;
import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.store.Store;

public class ReplaceAction implements UserAction {
    private Output out;

    public ReplaceAction(Output out) {
        this.out = out;
    }

    public ReplaceAction() {
    }

    @Override
    public String name() {
        return "Edit item";
    }

    @Override
    public boolean execute(Input input, Store tracker) {
        Integer id = input.askInt("Enter id: ");
        String name = input.askStr("Enter name: ");
        if (tracker.replace(id, new Item(name))) {
            System.out.println("Item is successfully replaced!");
        } else {
            System.out.println("Wrong id!");
        }
        return true;
    }
}
