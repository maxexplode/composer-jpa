package org.composer.common;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ServiceWrapper<T> {
    private List<T> services;
    private Map<Class<?>, Object> objects;

    public ServiceWrapper(List<T> services, Map<Class<?>, Object> objects) {
        this.services = services;
        this.objects = objects;
    }

    public Object object(Class<?> aClass) {
        if (objects != null && !objects.isEmpty()) {
            return objects.get(aClass);
        }
        return null;
    }

    public List<T> services() {
        return services;
    }

    public Optional<T> service(Class<T> aClass) {
        return services.stream().filter(t -> t.getClass().isAssignableFrom(aClass)).findAny();
    }
}
