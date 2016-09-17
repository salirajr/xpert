package com.xybase.ax.eai.waconsole.appliance.control;

import org.springframework.beans.FatalBeanException;

import com.xybase.ax.eai.archcomp.exception.ApplicationRuntimeException;
import com.xybase.ax.eai.archcomp.matrix.Matrix;

public class ControlInterpreter {

	private Matrix module;

	/*
	 * Version 1.0 {'owner':'MODULE', 'container':'CONTAINER_NAME',
	 * 'referral':'LAYER/BASE/BINDERKEY', 'name':'INSTANCE_NAME',
	 * 'command':'CIPHER_RULE','parameters':'PARAMETERS'}
	 */

	/*
	 * Version 1.1
	 * {MODULE/*CONTAINER/*CONTAINER_ACTION/*REFERRAL/*INSTANCE_NAME/
	 * *COMMAND/*PARAMETERS}
	 */

	public ControlInterpreter(Matrix module) {
		// TODO Auto-generated constructor stub
		this.module = module;
	}

	private String parameterized(String cmd, String param) {
		String[] parameters = param.split(";");
		String[] command = cmd.split("\\?");
		String result = "";
		int i;
		for (i = 0; i < command.length - 1; i++) {
			result += command[i];
			result += parameters[i].contains("=") ? parameters[i].split("=")[1]
					: "";
		}
		result += command[i];
		return result;
	}

	public String intepret(String ctrl) {
		String control[] = ctrl.split("/\\*");
		if (control.length == 8) {
			if (module.containsKey(control[0])) {
				if (control[1].equals("LAYER")) {
					return "@" + control[5] + "."
							+ parameterized(control[6], control[7]);
				} else if (control[1].equals("BASE") || control[1].equals("BINDERKEY")) {
					return "@"
							+ control[2]
							+ "."
							+ parameterized(
									parameterized(control[3], control[4]),
									"?="
											+ "@"
											+ control[5]
											+ "."
											+ parameterized(control[6],
													control[7]));
				}
			}
		}
		throw new ApplicationRuntimeException("Unidentified message!",
				new FatalBeanException("CONTACT YOUR ADMINISTRATOR!"));

	}

}
