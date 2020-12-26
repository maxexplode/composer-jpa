package org.composer.api;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.composer.common.ServiceType;
import org.composer.common.ServiceWrapper;

import java.util.*;

public class ServiceRegistry {

    static Logger logger = LogManager.getLogger("API");

    private static final Map<ServiceType, List<Service>> SERVICE_REGISTRY = new HashMap<>();
    private static final Map<ServiceType, Map<Class<?>, Object>> SERVICE_OBJECTS = new HashMap<>();

    public static <T extends Service> void registerService(ServiceType type, T service) {
        SERVICE_REGISTRY.computeIfAbsent(type, t -> new ArrayList<>());
        if (!SERVICE_REGISTRY.get(type).contains(service)) {
            try {
                Object object = service.service();
                if (object != null) {
                    SERVICE_OBJECTS.computeIfAbsent(type, t -> new HashMap<>());
                    SERVICE_OBJECTS.get(type).put(object.getClass(), object);
                }
            } catch (Exception e) {
                logger.log(Level.ALL, "", e);
            }
            SERVICE_REGISTRY.get(type).add(service);
        }
    }

    public static <T> ServiceWrapper<T> findService(ServiceType serviceType) {
        return new ServiceWrapper<T>((List<T>) SERVICE_REGISTRY.get(serviceType), SERVICE_OBJECTS.get(serviceType));
    }

}
