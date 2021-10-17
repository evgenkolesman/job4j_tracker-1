package ru.job4j.tracker.trackerformockito.action;

import org.junit.After;
import org.junit.Test;
import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.model.Item;
import ru.job4j.tracker.trackerformockito.output.Output;
import ru.job4j.tracker.trackerformockito.output.StubOutput;
import ru.job4j.tracker.trackerformockito.store.Tracker;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DeleteActionTest {

    @After
    public void tearDown() {
        Tracker.getInstance().clear();
    }

    @Test
    public void execute() {
        Output out = new StubOutput();
        Tracker tracker = Tracker.getInstance();
        Item item = tracker.add(new Item("New item name"));

        Input input = mock(Input.class);
        DeleteAction delete = new DeleteAction(out);

        when(input.askInt(any(String.class))).thenReturn(item.getId());

        delete.execute(input, tracker);

        String ln = System.lineSeparator();
        assertThat(out.toString(), is("=== Delete Item ===" + ln + "Item was deleted." + ln));
        assertThat(tracker.findById(item.getId()), is(nullValue()));
    }
}