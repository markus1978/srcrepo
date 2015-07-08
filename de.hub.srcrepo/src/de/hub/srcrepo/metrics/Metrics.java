package de.hub.srcrepo.metrics;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Metrics {
	Metric[] value();
}