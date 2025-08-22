package fieg.core.producer;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.enterprise.inject.spi.InjectionPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
class LoggerProducer {

	@Produces
	public Logger crieLogger(InjectionPoint point) {
		return LoggerFactory.getLogger(point.getMember().getDeclaringClass());
	}
}
