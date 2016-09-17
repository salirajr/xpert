/** 
 * Modification History
 * Date			Time				Modified By             Comments
 * **************************************************************************************
 * Mar 12, 2015	4:37:19 PM			Jovi Rengga Salira		Initial Creation
 * 
 * 
 * Nov 17, 2015	4:37:19 PM			Abdul Azis Nur          Update :
 * 									-> remove StringUtil.cast in tinject.inject(StringUtil.cast(extractedValue),targetPath);
 * **************************************************************************************
 */
package com.xybase.ax.eai.archcomp.transformer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Objects;
import com.xybase.ax.eai.archcomp.common.util.StringUtil;
import com.xybase.ax.eai.archcomp.constant.InternalConstant;
import com.xybase.ax.eai.archcomp.control.bus.CrowbarBus;
import com.xybase.ax.eai.archcomp.control.bus.constant.CrowbarConstant;
import com.xybase.ax.eai.archcomp.control.bus.util.BusRspUtil;
import com.xybase.ax.eai.archcomp.message.XMessage;
import com.xybase.ax.eai.archcomp.transformer.extract.Extractor;
import com.xybase.ax.eai.archcomp.transformer.inject.Injector;
import com.xybase.ax.eai.archcomp.transformer.rule.TransformerRule;
import com.xybase.ax.eai.archcomp.transformer.rule.dao.TransformerRuleDao;
import com.xybase.ax.eai.archcomp.transformer.template.TransformerTemplate;
import com.xybase.ax.eai.archcomp.transformer.util.SpELProcessor;

/**
 * @param <E>
 * @note Transformation function, Message Handler Operational perusal.
 *
 */
public class TransformerImpl<T> implements Transformer<T> {

	private TransformerTemplate transform;
	private List<TransformerRule> transformRules;

	private Map<String, String> lsTargetParentPath;
	private Map<String, String> lsSourceParentPath;

	@SuppressWarnings("rawtypes")
	private Extractor extractor;
	@SuppressWarnings("rawtypes")
	private Injector injector;

	private Object context;
	private boolean payloadCycles;

	private TransformerRuleDao dao;

	private SpELProcessor processor;

	public TransformerImpl() {
		this.payloadCycles = false;
	}

	/**
	 * @param auditConfigIn
	 * @param auditConfigOut
	 * @param transform
	 * @param transformRules
	 * @param extractor
	 * @param injector
	 */
	@SuppressWarnings("rawtypes")
	public TransformerImpl(TransformerTemplate transform,
			List<TransformerRule> transformRules, Extractor extractor,
			Injector injector) {
		super();
		this.transform = transform;
		this.transformRules = transformRules;
		this.extractor = extractor;
		this.injector = injector;
		this.payloadCycles = false;
	}

	@SuppressWarnings("rawtypes")
	public Extractor getExtractor() {
		return extractor;
	}

	public void setExtractor(@SuppressWarnings("rawtypes") Extractor extractor) {
		this.extractor = extractor;
	}

	@SuppressWarnings("rawtypes")
	public Injector getInjector() {
		return injector;
	}

	@SuppressWarnings("rawtypes")
	public void setInjector(Injector injector) {
		this.injector = injector;
	}

	public TransformerTemplate getTransform() {
		return transform;
	}

	public void setTransform(TransformerTemplate transform) {
		this.transform = transform;
	}

	public List<TransformerRule> getTransformRules() {
		return transformRules;
	}

	public void setTransformRules(List<TransformerRule> transformRules) {
		this.transformRules = transformRules;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * Variables Logic, Add both, on Extractor and Injectors
	 * 
	 * @see
	 * com.xybase.ax.eai.archcomp.transform.ITransform#transform(java.lang.Object
	 * )
	 */

	public void setProcessor(SpELProcessor processor) {
		this.processor = processor;
	}
	
	public SpELProcessor gettProcessor() {
		return this.processor ; 
	}

	@Override
	@SuppressWarnings("unchecked")
	public void setPayload(T payload) {
		extractor.setContext(payload);
	}

	private int indexCounter(String path, List<String> indexIterator,
			Map<String, String> map) {
		int result = 1;
		for (String n : indexIterator) {
			if (path.contains(InternalConstant.XEAI_TRANSFORM_VARIABLES_IDENTIFIER
					+ n)) {
				result *= Integer.parseInt(map
						.get(InternalConstant.XEAI_TRANSFORM_INDEX_IDENTIFIER
								+ n));
			}

		}
		return result;
	}

	private Map<String, String> update(List<String> head, String path,
			int number, Map<String, String> map) {

		int nmod = 1, index, ni = 1;
		for (int i = head.size() - 1; i >= 0; i--) {
			if (path.contains(InternalConstant.XEAI_TRANSFORM_VARIABLES_IDENTIFIER
					+ head.get(i))) {

				ni = Integer.parseInt(map
						.get(InternalConstant.XEAI_TRANSFORM_INDEX_IDENTIFIER
								+ head.get(i)));

				index = (number / nmod) % ni;
				map.put(head.get(i), index + StringUtil.emptyChar);
				nmod *= ni;

			}

		}

		return map;
	}

	private String driedPath(String path, String parentPath) {
		if (!StringUtil.isNullOrBlank(path)
				&& path.substring(0, 2).equals(
						InternalConstant.XEAI_TRANSFORM_CHILD_IDENTIFIER)) {
			path = parentPath
					+ path.replace(
							InternalConstant.XEAI_TRANSFORM_CHILD_IDENTIFIER,
							StringUtil.emptyChar);
		}
		return path;
	}

	private boolean isChild(String path) {
		return !StringUtil.isNullOrBlank(path)
				&& path.substring(0, 2).equals(
						InternalConstant.XEAI_TRANSFORM_CHILD_IDENTIFIER);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xybase.ax.eai.archcomp.transform.ITransform#transform()
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public XMessage<T> transform() {

		Injector tinject = injector.clones();
		Extractor textract = extractor.clones();
		textract.setContext(context);
		// TODO Auto-generated method stub
		lsTargetParentPath = new HashMap<String, String>();
		lsSourceParentPath = new HashMap<String, String>();

		if (payloadCycles)
			tinject.setTemplate(tinject.getConverter().toString(
					extractor.getContext()));
		else
			tinject.setTemplate(this.transform.getTemplate());

		String targetPath, sourceParentPath = "", sourcePath, targetParentPath = "", matrix, key;
		int indexCount;

		List<String> indexIterator = new ArrayList<String>();

		Map<String, String> variables = textract.getVariables();

		for (TransformerRule tr : transformRules) {

			sourcePath = tr.getTransformRuleSource();
			targetPath = tr.getTransformRuleTarget();
			matrix = tr.getTransformRuleMatrix();
			indexCount = 0;
			
			if (tr.getTransformRuleConfig() == 2
					&& !tr.getTransformRuleSource().equals(
							InternalConstant.XEAI_TRANSFORM_NO_EXTRACTION)) {
				processor.setVariables(variables);
				sourcePath = StringUtil.cast(processor.express(sourcePath));
			}

			/*
			 * logic #00, TransformRuleMatrix eq #START_INDEX#; Force: 1. Assign
			 * Index Identifier to variables; 2. Initiate value to variable; 3.
			 * Skip logic #01;
			 */
			if (!StringUtil.isNullOrBlank(matrix)
					&& matrix.contains(Transformer.IndexIdentifier.STARTINDEX)) {

				sourcePath = driedPath(sourcePath, sourceParentPath);
				targetPath = driedPath(targetPath, targetParentPath);

				key = targetPath.replaceFirst(
						InternalConstant.XEAI_TRANSFORM_VARIABLES_IDENTIFIER,
						StringUtil.emptyChar);
				indexIterator.add(key);

				variables.put(key, "0");

				variables.put(InternalConstant.XEAI_TRANSFORM_INDEX_IDENTIFIER
						+ key, textract.count(sourcePath)
						+ StringUtil.emptyChar);

			} else if (!StringUtil.isNullOrBlank(matrix)
					&& matrix.contains(Transformer.IndexIdentifier.ENDPOINTER)) {

				if (StringUtil.isNullOrBlank(sourcePath)) {
					sourceParentPath = StringUtil.emptyChar;
				} else {
					if (sourcePath
							.equals(InternalConstant.XEAI_TRANSFORM_CHILD_IDENTIFIER)) {
						sourcePath = StringUtil.emptyChar;
					} else if (lsSourceParentPath.containsKey(sourcePath)) {
						sourceParentPath = lsSourceParentPath.get(sourcePath);
						sourcePath = StringUtil.emptyChar;
					}
				}
				if (StringUtil.isNullOrBlank(targetPath)) {
					targetParentPath = StringUtil.emptyChar;
				} else {
					if (targetPath
							.equals(InternalConstant.XEAI_TRANSFORM_CHILD_IDENTIFIER)) {
						targetPath = StringUtil.emptyChar;
					} else if (lsTargetParentPath.containsKey(targetPath)) {
						targetParentPath = lsSourceParentPath.get(targetPath);
						targetPath = StringUtil.emptyChar;
					}
				}

			} else if (!StringUtil.isNullOrBlank(matrix)
					&& matrix.contains(Transformer.IndexIdentifier.PARENT)) {

				if (isChild(sourcePath)) {

					sourceParentPath = driedPath(sourcePath, sourceParentPath);
				} else {
					sourceParentPath = sourcePath;
				}

				if (isChild(targetPath)) {
					targetParentPath = driedPath(targetPath, targetParentPath);
				} else {
					targetParentPath = targetPath;
				}

				lsSourceParentPath.put(tr.getTransformRuleId()
						+ StringUtil.emptyChar, sourceParentPath);
				lsTargetParentPath.put(tr.getTransformRuleId()
						+ StringUtil.emptyChar, targetParentPath);

				sourcePath = StringUtil.emptyChar;
				targetPath = StringUtil.emptyChar;

			} else {
				if (isChild(sourcePath)) {
					sourcePath = driedPath(sourcePath, sourceParentPath);
				}

				if (isChild(targetPath)) {
					targetPath = driedPath(targetPath, targetParentPath);

				}
				indexCount = indexCounter(targetPath, indexIterator, variables);
				for (int index = 1; index <= indexCount; index++) {

					textract.setVariables(variables);
					tinject.setVariables(variables);

					Object extractedValue;
					if (sourcePath
							.equals(InternalConstant.XEAI_TRANSFORM_NO_EXTRACTION))
						extractedValue = sourcePath;
					else
						extractedValue = textract.extract(sourcePath);
//					System.out.println("extractedValue =>"+extractedValue.getClass());
					if (!Objects.equal(
							InternalConstant.XEAI_TRANSFORM_NO_INJECTION,
							extractedValue)) {
						tinject.inject((extractedValue),
								targetPath);
					}

					variables = tinject.getVariables();
					variables = update(indexIterator, targetPath, index,
							variables);

				}

			}

		}

		XMessage result = new XMessage(tinject.finalized());
		result.setVariables(tinject.getVariables());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xybase.ax.eai.archcomp.transform.ITransform#setVariables(java.util
	 * .Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void setVariables(Map<String, String> variables) {
		injector.setVariables(variables);
		extractor.setVariables(variables);
	}

	public void setTransformRuleDao(TransformerRuleDao dao) {
		this.dao = dao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xybase.ax.eai.archcomp.transform.ITransform#setContext(java.lang.
	 * Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void setContext(Object context) {
		// TODO Auto-generated method stub
		this.context = context;
		extractor.setContext(this.context);
		processor.setRootObject(this.context);
	}

	@Override
	public boolean isinitialized() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String reinitialized() {
		// TODO Auto-generated method stub
		int transformId = transformRules.get(0).getTransformId();
		this.transformRules = dao.gets(transformId);
		this.transform.reinitialized();
		return BusRspUtil.asResponse(
				CrowbarConstant.code.CREINITIALIZATIONPSUCCEED,
				CrowbarConstant.state.CREINITIALIZATIONPSUCCEED,
				"Transformation is refreshed!");
	}

	@Override
	public String reinitialized(String operand) {
		// TODO Auto-generated method stub
		String response = "";
		switch (operand) {
		case "rules":
			int transformId = transformRules.get(0).getTransformId();
			this.transformRules = dao.gets(transformId);
			response = BusRspUtil.asResponse(
					CrowbarConstant.code.CREINITIALIZATIONPSUCCEED,
					CrowbarConstant.state.CREINITIALIZATIONPSUCCEED,
					"Rules variables is refreshed!");
			break;
		case "template":
			this.transform.reinitialized();
			response = BusRspUtil.asResponse(
					CrowbarConstant.code.CREINITIALIZATIONPSUCCEED,
					CrowbarConstant.state.CREINITIALIZATIONPSUCCEED,
					"Template variables is refreshed!");
			break;
		default:
			response = BusRspUtil.asResponse(CrowbarConstant.code.DESCRIBEINFO,
					CrowbarConstant.state.CREINITIALIZATIONP,
					StringUtil.constraints("rules",
							StringUtil.RegX.doubleQuotes), StringUtil
							.constraints("template",
									StringUtil.RegX.doubleQuotes));
			break;

		}
		return response;
	}

	@Override
	public String destroy() {
		// TODO Auto-generated method stub
		return BusRspUtil.asResponse(CrowbarConstant.code.CDESTROYOFF,
				CrowbarConstant.state.CDESTROYOFF,
				CrowbarConstant.message.CDESTROYOFF);
	}

	@Override
	public String destroy(String operand) {
		// TODO Auto-generated method stub
		return destroy();
	}

	@Override
	public String info() {
		// TODO Auto-generated method stub
		return BusRspUtil.asInfo(this.getClass().getName(), "",
				CrowbarBus.class.getName());
	}

	public void setPayloadCycles(boolean payloadCycles) {
		this.payloadCycles = payloadCycles;
	}
	
	// AD BY AZIS 20160223
	private Object payloadData;
	

	/**
	 * @return the payloadData
	 */
	public Object getPayloadData() {
		return payloadData;
	}

	/**
	 * @param payloadData the payloadData to set
	 */
	public void setPayloadData(Object payloadData) {
		this.payloadData = payloadData;
	}


}
