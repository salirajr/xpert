package com.xybase.ax.eai.archcomp.control.bus.constant;

public class CrowbarConstant {

	public class state {
		public static final String INITIALIZED = "INITIALIZED";
		public static final String DESTROYED = "ZEROED_INITIALIZED";

		public static final String INITIALIZATION_SUCCEED = "INITIALIZATION_SUCCEED";
		public static final String INITIALIZATION_FAILED = "INITIALIZATION_FAILED";
		public static final String CDESTROYOFF = "CROWBAR:DESTROY_OFF";
		public static final String CREINITIALIZATION = "CROWBAR:INITIALIZATION_OFF";
		public static final String CREINITIALIZATIONP = "CROWBAR:PARAMETERIZED_INITIALIZATION_OFF";
		public static final String CREDESTROYINGP = "CROWBAR:PARAMETERIZED_DESTROYING_OFF";
		public static final String CREINITIALIZATIONPSUCCEED = "CROWBAR:PARAMETERIZED_INITIALIZATION_SUCCEED";

		public static final String CREINITIALIZATIONPINFO = "CROWBAR:PARAMETERIZED_INITIALIZATION_INFO";


	}

	public class code {
		public static final String INITIALIZATION_SUCCEED = "011";
		public static final String DESCRIBEINFO = "000";
		public static final String NOINFO = "100";
		public static final String CDESTROYOFF = "101";
		public static final String CREINITIALIZATIONP = "102";
		public static final String CREINITIALIZATIONPSUCCEED = "112";
		public static final String CREDESTROYINGP = "103";
		public static final String INITIALIZATION_FAILED = "104";
		public static final String CREINITIALIZATION = "105";
	}

	public class message {
		
		public static final String DESTROYED = "DESTROYED SUCCEED"; 

		public static final String NOINFO = "No info available!";

		public static final String CDESTROYOFF = "Destroy crowbar operation is not permitted on this instance!";

		public static final String CREINITIALIZATIONP = "Reinitialization operation with parameter is not avaialable on this instance!";

		public static final String CREINITIALIZATION = "Reinitialization operation is not avaialable on this instance!";

		public static final String CREDESTROYINGP = "Destroying operation with parameter is not avaialable on this instance!";

		public static final String INITIALIZATION_FAILED = "Invalid Lookup Definition!!, check your CDI, Make sure the system is UP and READY!!";
	}

}
