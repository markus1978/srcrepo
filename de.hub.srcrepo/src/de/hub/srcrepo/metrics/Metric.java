package de.hub.srcrepo.metrics;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Metrics.class)
public @interface Metric {
	String name();
	int value() default 0;
}
