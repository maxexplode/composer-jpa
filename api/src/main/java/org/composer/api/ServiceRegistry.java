package org.composer.api;

import java.util.*;

public class ServiceRegistry {
    public enum ServiceType {
        PHASER,
        CONFIG
    }

    private static final Map<ServiceType, List<Service>> SERVICE_REGISTRY = new HashMap<>();

    public static <T extends Service> void registerService(ServiceType type, T service) {
        SERVICE_REGISTRY.computeIfAbsent(type, t -> new ArrayList<>());
        if (!SERVICE_REGISTRY.get(type).contains(service)) {
            SERVICE_REGISTRY.get(type).add(service);
        }
    }

    public static <T> List<T> findService(ServiceType serviceType) {
        return (List<T>) SERVICE_REGISTRY.get(serviceType);
    }


}
