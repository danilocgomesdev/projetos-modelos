package fieg.core.producer;

import jakarta.enterprise.context.ApplicationScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.enterprise.inject.Produces;
import jakarta.enterprise.inject.spi.InjectionPoint;

@ApplicationScoped
class LoggerProducer {

	@Produces
	public Logger crieLogger(InjectionPoint point) {
		return LoggerFactory.getLogger(point.getMember().getDeclaringClass());
	}
}
