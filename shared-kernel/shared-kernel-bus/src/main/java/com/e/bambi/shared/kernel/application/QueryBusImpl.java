package com.e.bambi.shared.kernel.application;

import com.e.bambi.shared.kernel.application.bus.Query;
import com.e.bambi.shared.kernel.application.bus.QueryHandler;
import com.e.bambi.shared.kernel.application.port.inbound.bus.QueryBus;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Primary
@Component
public class QueryBusImpl implements QueryBus {

    private final Map<Class<?>, QueryHandler> handlers;

    public QueryBusImpl(List<QueryHandler> queryHandlerImplementations) {
        this.handlers = new HashMap<>();
        queryHandlerImplementations.forEach(queryHandler -> {
            Class<?> queryClass = getQueryClass(queryHandler);
            handlers.put(queryClass, queryHandler);
        });
    }

    public <R> R dispatch(Query<R> query) {
        if (!handlers.containsKey(query.getClass())) {
            Mono.error(new Exception(String.format("No handler for %s", query.getClass().getName())));
        }
        return (R) handlers.get(query.getClass()).handle(query);
    }

    private Class<?> getQueryClass(QueryHandler handler) {
        Type commandInterface = ((ParameterizedType) handler.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[1];
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
