package uk.co.automatictester.behavioral.command;

import java.util.List;

public class StatsCollector {

    public void run(List<Command> commands) {
        commands.forEach(Command::execute);
    }
}
