package com.xybase.ax.eai.archcomp.control.bus.constant;

public class BinderConstant {
	public class state {
		public static final String BINDED = "BINDED";
		public static final String ALTERED = "ALTERED";
		public static final String ERR_ADDED = "ERR_ADDED";
		public static final String REFRESHED = "REFRESHED";
		public static final String DESTROYED = "DESTROYED";
		public static final String ERR_START = "ERR_START";
		public static final String ERR_REFRESHED = "ERR_REFRESHED";

	}

	public class code {
		public static final String BINDED_SUCCESS = "801";
		public static final String ALTERED_SUCCESS = "803";
		public static final String ADDED_SUCCESS = "804";
		public static final String ERR_ADDED = "111";
		public static final String ERR_START = "112";
		public static final String ERR_REFRESHED = "113";
	}

	public class message {
		public static final String BINDED_SUCCESS = "SUCCESS BINDING!!";
		public static final String ALTERED_SUCCESS = "SUCCESS ALTERING!!";
		public static final String ADD_SUCCESS = "SUCCESS ADD-BINDED SERVICE!!";
		public static final String DESTROYED = "DESTROYED SUCCESS";
		public static final String DISABLED = "FEATURE DISABLED";
		public static final String CONTEXT_UP = "CONTEXT IS RUNNING";
		public static final String REFRESHED = "CONTEXT IS REFRESHED";
		public static final String CONTEXT_DESTROYED = "CONTEXT IS DESTROYED";
	}

}
