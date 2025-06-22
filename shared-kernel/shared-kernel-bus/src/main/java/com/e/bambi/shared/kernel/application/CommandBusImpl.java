package com.e.bambi.shared.kernel.application;

import com.e.bambi.shared.kernel.application.bus.Command;
import com.e.bambi.shared.kernel.application.bus.CommandHandler;
import com.e.bambi.shared.kernel.application.port.inbound.bus.CommandBus;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Primary
public class CommandBusImpl implements CommandBus {

    private final Map<Class<?>, CommandHandler> handlers;

    public CommandBusImpl(List<CommandHandler> commandHandlerImplementations) {
        this.handlers = new HashMap<>();
        commandHandlerImplementations.forEach(commandHandler -> {
            Class<?> commandClass = getCommandClass(commandHandler);
            handlers.put(commandClass, commandHandler);
        });
    }

    @Override
    public <R> R dispatch(Command<R> command) {
        if (!handlers.containsKey(command.getClass())) {
            Mono.error(new Exception(String.format("No handler for %s", command.getClass().getName())));
        }
        return (R) handlers.get(command.getClass()).handle(command);
    }

    private Class<?> getCommandClass(CommandHandler handler) {
        Type commandInterface =
                ((ParameterizedType) AopUtils.getTargetClass(handler).getGenericInterfaces()[0]).getActualTypeArguments()[1];
        return getClass(commandInterface.getTypeName());
    }

    private Class<?> getClass(String name) {
        try {
            return Class.forName(name);
        } catch (Exception e) {
            return null;
        }
    }
}
