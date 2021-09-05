package ru.job4j.tracker.trackerformockito.action;

import org.junit.After;
import org.junit.Test;
import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.model.Item;
import ru.job4j.tracker.trackerformockito.output.Output;
import ru.job4j.tracker.trackerformockito.output.StubOutput;
import ru.job4j.tracker.trackerformockito.store.Tracker;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FindByIdActionTest {
    @After
    public void tearDown() {
        Tracker.getInstance().clear();
    }

    @Test
    public void execute() {
        Output out = new StubOutput();
        Tracker tracker = Tracker.getInstance();
        Item item = tracker.add(new Item("First item"));
        tracker.add(new Item("Second item"));

        Input input = mock(Input.class);
        FindByIdAction idAction = new FindByIdAction(out);

        when(input.askInt(any(String.class))).thenReturn(item.getId());

        idAction.execute(input, tracker);

        String ln = System.lineSeparator();
        assertThat(out.toString(), is("=== Find Item By ID ===" + ln + item + ln));
    }
}