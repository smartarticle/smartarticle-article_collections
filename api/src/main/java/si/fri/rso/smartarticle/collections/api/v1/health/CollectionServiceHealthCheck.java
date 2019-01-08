package si.fri.rso.smartarticle.collections.api.v1.health;

import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import si.fri.rso.smartarticle.collections.services.configuration.AppProperties;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@Health
@ApplicationScoped
public class CollectionServiceHealthCheck implements HealthCheck{
    @Inject
    private AppProperties appProperties;

    public HealthCheckResponse call() {
        if (appProperties.isHealthy()) {
            return  HealthCheckResponse.named(CollectionServiceHealthCheck.class.getSimpleName()).up().build();
        } else {
            return  HealthCheckResponse.named(CollectionServiceHealthCheck.class.getSimpleName()).down().build();
        }
    }
}
