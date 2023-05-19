package exmp.commands;

import java.io.Serializable;

public record ArgDescriptor(String description, Class<?> type) implements Serializable { }
