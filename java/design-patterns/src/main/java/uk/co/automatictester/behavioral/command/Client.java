package uk.co.automatictester.behavioral.command;

import java.util.LinkedList;
import java.util.List;

public class Client {

    public static void main (String[] args) {
        Command minLogin = new MinTimeCommand("Login");
        Command maxLogin = new MaxTimeCommand("Login");
        Command maxLogout = new MaxTimeCommand("Logout");

        List<Command> commands = new LinkedList<>();
        commands.add(minLogin);
        commands.add(maxLogin);
        commands.add(maxLogout);

        StatsCollector statsCollector = new StatsCollector();
        statsCollector.run(commands);
    }
}
