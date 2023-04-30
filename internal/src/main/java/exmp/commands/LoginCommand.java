package exmp.commands;

import java.util.ArrayList;
import java.util.List;

public class LoginCommand implements exmp.commands.Command {
    @Override
    public String getName() {
        return "login";
    }

    @Override
    public List<exmp.commands.ArgDescriptor> getArguments() {
        List<exmp.commands.ArgDescriptor> args = new ArrayList<>();
        args.add(new exmp.commands.ArgDescriptor("login", String.class));
        args.add(new exmp.commands.ArgDescriptor("password", String.class));
        return args;
    }

    @Override
    public String getDescription() {
        return "Войти в аккаунт";
    }

    @Override
    public exmp.commands.CommandResult execute(exmp.App app, Object... args) {
        return new exmp.commands.CommandResult(0, null, null);
    }
}
