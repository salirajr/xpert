/** 
 * Modification History
 * Date         Modified By             Comments
 * **************************************************************************************
 * 12102014		Jovi Rengga Salira		Initial Creation
 * 										Based Fact constructor in Airport operation-logic!
 * 23102014		Jovi Rengga Salira		Add Logging simulates source of Code
 * 05112014		Jovi Rengga Salira		Add Concept of Backward Chain, Utilized Stack, 
 * 										ref: reapPhase(use as Phasing), reapContainer(use as Main Container)
 * 										Simple Mechanism, cyclic phase with stack-mechanism!
 * 12112014		Jovi Rengga Salira		Add OUT-SOURCE-ARC Query, Possibility to Shift Query
 * **************************************************************************************
 */

package com.concept.ruleengine.model;

import java.util.Stack;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EphemeralMantle {

	private static Logger logger = LogManager.getLogger(EphemeralMantle.class);

	protected String operationName;
	protected Stack<String> phase;
	protected int round;
	protected String status;

	public EphemeralFact facts;

	public EphemeralMantle() {
		this.phase = new Stack<String>();
		this.round = 0;

	}

	public EphemeralMantle(String name) {
		// TODO Auto-generated constructor stub
		this.operationName = name;
		this.phase = new Stack<String>();
		this.round = 0;
	}

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	public String getPhase() {
		return phase.peek();
	}

	public void setPhase(String phase) {
		if (!this.phase.isEmpty())
			this.phase.pop();
		this.phase.push(phase);
		this.round = 0;
	}

	public void nextPhase(String phase) {
		this.phase.push(phase);
	}

	public String popPhase() {
		return phase.pop();
	}

	public int getRound() {
		return round;
	}

	public void setRound(int round) {
		this.round = round;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public EphemeralFact getFacts() {
		return facts;
	}

	public void setFacts(EphemeralFact facts) {
		this.facts = facts;
	}

	public void logsCurrent() {
		logger.info(this.toString());
	}

	public void logs(Object info) {
		logger.info(info);
	}

	public static Logger getLogger() {
		return logger;
	}

	@Override
	public String toString() {
		return "EphemeralMantle [operationName=" + operationName + ", phase=" + phase + ", round=" + round
				+ ", status=" + status + ", facts=" + facts + "]";
	}
	
	

}
