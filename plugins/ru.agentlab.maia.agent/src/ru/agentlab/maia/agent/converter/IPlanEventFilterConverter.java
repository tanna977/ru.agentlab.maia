package ru.agentlab.maia.agent.converter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;

import ru.agentlab.maia.agent.filter.IPlanEventFilter;

public interface IPlanEventFilterConverter {

	IPlanEventFilter<?> getMatcher(Object role, Method method, Annotation annotation, Map<String, Object> customData);

}