package exmp.commands;

import static java.lang.System.exit;

public class ExitCommand implements exmp.commands.Command {
    @Override
    public String getName() {
        return "exit";
    }

    @Override
    public String getDescription() {
        return "завершить программу";
    }

    @Override
    public void execute(exmp.App app, String[] args) {
        app.switchOff();
    }
}
