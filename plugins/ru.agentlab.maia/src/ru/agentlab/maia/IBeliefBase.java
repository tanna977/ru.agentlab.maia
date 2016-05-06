/*******************************************************************************
 * Copyright (c) 2016 AgentLab.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package ru.agentlab.maia;

import org.semanticweb.owlapi.model.IRI;

public interface IBeliefBase {

	void addClassAssertion(IRI object, IRI subject);

	void addClass(String name);

	void addObjectPropertyAssertion(IRI object, IRI predicate, IRI subject);

}
