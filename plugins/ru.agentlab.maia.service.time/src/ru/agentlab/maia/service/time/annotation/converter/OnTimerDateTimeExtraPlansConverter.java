package ru.agentlab.maia.service.time.annotation.converter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import ru.agentlab.maia.IPlan;
import ru.agentlab.maia.agent.Plan;
import ru.agentlab.maia.agent.converter.IPlanExtraConverter;
import ru.agentlab.maia.agent.event.AgentStartedEvent;
import ru.agentlab.maia.agent.event.AgentStoppedEvent;
import ru.agentlab.maia.service.time.TimerEvent;
import ru.agentlab.maia.service.time.annotation.OnTimerDateTime;

public class OnTimerDateTimeExtraPlansConverter implements IPlanExtraConverter {

	@Inject
	ScheduledExecutorService scheduler;

	@Inject
	Queue<Object> eventQueue;

	@Override
	public List<IPlan<?>> getPlans(Object role, Method method, Annotation annotation, Map<String, Object> customData) {
		OnTimerDateTime onTimerDelay = (OnTimerDateTime) annotation;

		final UUID eventKey = (UUID) customData.get(annotation.getClass().getName());
		final ScheduledFuture<?>[] futures = new ScheduledFuture[1];
		LocalDateTime dateTime = LocalDateTime
			.parse(onTimerDelay.value(), DateTimeFormatter.ofPattern(onTimerDelay.pattern()));

		Runnable onStartPlan = () -> {
			LocalDateTime now = LocalDateTime.now();
			long delay = ChronoUnit.MILLIS.between(now, dateTime);
			System.out.println("Register schedule in " + delay + " ms");
			futures[0] = scheduler
				.schedule(() -> eventQueue.offer(new TimerEvent(eventKey)), delay, TimeUnit.MILLISECONDS);
		};

		Runnable onStopPlan = () -> {
			System.out.println("Unregister schedule");
			futures[0].cancel(true);
		};

		List<IPlan<?>> result = new ArrayList<>();
		result.add(new Plan<AgentStartedEvent>(AgentStartedEvent.class, onStartPlan)); // AgentStartedEvent.class,
		result.add(new Plan<AgentStoppedEvent>(AgentStoppedEvent.class, onStopPlan)); // AgentStoppedEvent.class,
		return result;
	}

}