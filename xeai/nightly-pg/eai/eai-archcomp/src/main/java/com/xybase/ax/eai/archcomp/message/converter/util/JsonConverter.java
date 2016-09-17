/** 
 * Modification History
 * Date			Time				Modified By             Comments
 * **************************************************************************************
 * Mar 13, 2015	10:37:03 AM			Jovi Rengga Salira		Initial Creation
 * Mar 13, 2015	10:52:03 AM			Jovi Rengga Salira		Final!!
 * Mar 13, 2015	11:21:03 AM			Jovi Rengga Salira		Bug Fixed, Changes method
 * 															Operating as It use.
 * Mar 13, 2015	15:21:03 AM			Jovi Rengga Salira		Developed as Converter-model
 * Mar 13, 2015	15:21:03 AM			Jovi Rengga Salira		Add Configuration to return List as default!!!
 * **************************************************************************************
 */
package com.xybase.ax.eai.archcomp.message.converter.util;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import com.xybase.ax.eai.archcomp.common.util.StringUtil;
import com.xybase.ax.eai.archcomp.constant.InternalConstant;
import com.xybase.ax.eai.archcomp.exception.InternalErrorRuntimeException;

/**
 * @note This only simple of Implementation, using 1.2 Version, under Google
 *       license
 * 
 * @thanks jayway
 *
 */
public class JsonConverter implements Converter<DocumentContext, Object> {
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xybase.ax.eai.archcomp.message.converter.util.IConverter#toContext
	 * (java.lang.String)
	 */
	@Override
	public DocumentContext toContext(Object payload) {
		try {
			DocumentContext context = JsonPath.using(
					Configuration.builder().options(Option.ALWAYS_RETURN_LIST)
							.build()).parse(toDocumentContext(payload));
			return context;
		} catch (Exception e) {
			throw new InternalErrorRuntimeException(e);
		}
		
	}

	public Object toDocumentContext(Object payload) {
		return Configuration.defaultConfiguration().jsonProvider()
				.parse(StringUtil.cast(payload));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xybase.ax.eai.archcomp.message.converter.util.IConverter#toString
	 * (java.lang.Object)
	 */
	@Override
	public String toString(DocumentContext payload) {
		DocumentContext context = JsonPath
				.parse(InternalConstant.XEAI_JSON_ROOTS);
		context.set("$." + InternalConstant.XEAI_JSON_ROOT_PATH, payload.json());
		String result = context.read("$.*").toString();
		return result.substring(1, result.length() - 1);
	}

	@Override
	public Object express(Object context, String expression) {
		DocumentContext dc = (DocumentContext) context;
		return dc.read(expression);
	}

}
