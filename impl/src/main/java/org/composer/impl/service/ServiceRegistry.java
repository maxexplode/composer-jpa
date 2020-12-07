package org.composer.impl.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceRegistry {
    public enum ServiceType {
        PHASER
    }

    private static final Map<ServiceType, List<Service>> SERVICE_REGISTRY = new HashMap<>();

    public static <T extends Service>  void registerService(ServiceType type, T service) {
        SERVICE_REGISTRY.computeIfAbsent(type, t -> new ArrayList<>());
        if (SERVICE_REGISTRY.get(type).contains(service)) {
            SERVICE_REGISTRY.get(type).add(service);
        }
    }



}
