package org.composer.api;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.composer.common.ServiceType;

import java.util.*;

public class ServiceRegistry {

    static Logger logger = LogManager.getLogger("API");

    private static final Map<ServiceType, List<Service>> SERVICE_REGISTRY = new HashMap<>();

    public static <T extends Service> void registerService(ServiceType type, T service) {
        SERVICE_REGISTRY.computeIfAbsent(type, t -> new ArrayList<>());
        if (!SERVICE_REGISTRY.get(type).contains(service)) {
            try {
                service.service();
            } catch (Exception e) {
                logger.log( Level.ALL, "", e);
            }
            SERVICE_REGISTRY.get(type).add(service);
        }
    }

    public static <T> List<T> findService(ServiceType serviceType) {
        return (List<T>) SERVICE_REGISTRY.get(serviceType);
    }


}
