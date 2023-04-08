package exmp.commands;

public class ArgDescriptor {
    private final String description;
    private final Class<?> type;

    public ArgDescriptor(String description, Class<?> type) {
        this.description = description;
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public Class<?> getType() {
        return type;
    }
}
