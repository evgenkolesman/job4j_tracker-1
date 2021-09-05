package ru.job4j.tracker.trackerformockito.output;

import ru.job4j.tracker.trackerformockito.output.Output;

public class StubOutput implements Output {
    private final StringBuilder buffer = new StringBuilder();

    @Override
    public void writeConsole(String string) {
        if (string != null) {
            buffer.append(string);
        } else {
            buffer.append("null");
        }
        buffer.append(System.lineSeparator());
    }

    @Override
    public String toString() {
        return buffer.toString();
    }
}
