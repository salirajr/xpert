package com.ptxti.concept.ruleengine.mapper.util;


/** 
 * 
 * Modification History
 * Date         Modified By             Comments
 * **************************************************************************************
 * 11092014		Jovi Rengga Salira		Copy Source
 * **************************************************************************************
 */

import org.apache.commons.jxpath.AbstractFactory;
import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.jxpath.Pointer;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/*
 * Copyright 1999-2004 The Apache Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/***
 * Test AbstractFactory.
 * 
 * @author Dmitri Plotnikov
 * @version $Revision: 1.6 $ $Date: 2004/06/29 22:58:17 $
 */
public class DOMFactory extends AbstractFactory {

	/***
	 * Returns <b>false</b> if this factory cannot create the requested object.
	 */
	public boolean createObject(JXPathContext context, Pointer pointer,
			Object parent, String name, int index) {
		// System.out.println("Index = " + index);
		addDOMElement((Node) parent, index, name, null);
		return true;
	}

	private void addDOMElement(Node parent, int index, String tag,
			String namespaceURI) {
		Node child = parent.getFirstChild();
		int count = 0;
		while (child != null) {
			if (child.getNodeName().equals(tag)) {
				count++;
			}
			child = child.getNextSibling();
		}

		// Keep inserting new elements until we have index + 1 of them
		while (count <= index) {
			Document doc = parent.getOwnerDocument();
			Node newElement;
			if (namespaceURI == null) {
				newElement = doc.createElement(tag);
			} else {
				newElement = doc.createElementNS(namespaceURI, tag);
			}

			parent.appendChild(newElement);
			count++;
		}
	}

	public boolean declareVariable(JXPathContext context, String name) {
		return false;
	}
}
