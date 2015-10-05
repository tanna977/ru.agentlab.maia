package ru.agentlab.maia.execution

import java.util.concurrent.atomic.AtomicReference

abstract class TaskPrimitive extends Task {

	var protected implementation = new AtomicReference<Object>

	override protected internalExecute() {
		try {
			doInject()
			doRun()
			doUninject()

			setStateSuccess
		} catch (Exception e) {
			setStateFailed
		}
	}

	def protected void doInject()

	def protected void doUninject()

	def protected Object doRun()

}