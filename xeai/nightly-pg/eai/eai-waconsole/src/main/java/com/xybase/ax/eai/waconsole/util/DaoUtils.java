/** 
 * Modification History
 * Date			Time				Modified By             Comments
 * **************************************************************************************
 * Feb 15, 2016	11:00:36 am			Abdul Azis Nur			add method "execute()" to save DML on DB
 * 
 * **************************************************************************************
 * 
 */

package com.xybase.ax.eai.waconsole.util;

import java.util.Arrays;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xybase.ax.eai.archcomp.common.util.StringUtil;
import com.xybase.ax.eai.waconsole.appliance.qdao.QDao;
import com.xybase.ax.eai.waconsole.appliance.qmap.dao.QMapDaoImpl;

@Controller
public class DaoUtils {

	private final String prefixMap = "/dautl";

	private final static Logger log = LogManager.getLogger(DaoUtils.class);

	@Autowired
	public QDao commonQDao;

	@Autowired
	private QMapDaoImpl commonQMapDao;

	@Autowired
	private QMapDaoImpl commonDmlMapDao;

	@RequestMapping(value = prefixMap + "/retrieve/json/{queryname}", method = RequestMethod.POST, headers = { "Content-type=application/json" })
	public @ResponseBody String retrieve(@PathVariable String queryname,
			@RequestBody String[] parameter) {
		return retrieveAsJSon(queryname, parameter);
	}

	private String retrieveAsJSon(String queryname, String[] parameter) {
		if (!commonQDao.hasKey(queryname)) {
			log.warn(queryname + " doesn't exist!");
			return "{\"payload\" : [] }";
		} else {
			log.info(queryname + " with parameters "
					+ Arrays.toString(parameter));
			String result = commonQDao.get(queryname, parameter);
			if (StringUtil.isNullOrBlank(result))
				return "{\"payload\" : [] }";
			else
				return "{\"payload\" : " + result + " }";
		}
	}

	@RequestMapping(value = prefixMap + "/extract/{type}/{queryname}", method = RequestMethod.POST, headers = { "Content-type=application/json" })
	public @ResponseBody String extract(@PathVariable String type,
			@PathVariable String queryname, @RequestBody String[] parameter) {
		return extractAsJSon(type, queryname, parameter);
	}

	private String extractAsJSon(String type, String queryname,
			String[] parameter) {
		if (!commonQMapDao.hasKey(queryname)) {
			log.warn(queryname + " doesn't exist!");
			return "{\"payload\" : [] }";
		} else {
			log.info(queryname + " of " + type + " with parameters "
					+ Arrays.toString(parameter));
			String result = commonQMapDao.retrieve(type, queryname, parameter);
			if (StringUtil.isNullOrBlank(result))
				return "{\"payload\" : [] }";
			else if (type.toLowerCase().equals("rows")) {
				String count = commonQMapDao.count(queryname, parameter);
				return "{\"payload\" : " + result + ", \"nRow\" : " + count
						+ " }";
			} else {
				return "{\"payload\" : " + result + " }";
			}
		}
	}

	public @ResponseBody String submit(@RequestBody Map<String, String> request) {
		return null;
	}

	@RequestMapping(value = "/dml/execute/{type}/{queryname}", method = RequestMethod.POST, headers = { "Content-type=application/json" })
	public @ResponseBody String execute(@PathVariable String type,
			@PathVariable String queryname, @RequestBody String[] parameters) {
		String result = "";
		int status = 0;
		System.out.println("parameters:" + Arrays.toString(parameters));
		System.out.println("type:" + type);
		if (commonDmlMapDao.update(queryname, parameters)) {
			result = "OPERATION " + type.toUpperCase() + " SUCCEED";
			status = 1;
		} else {
			result = "OPERATION " + type.toUpperCase() + " FAILED";
		}
		return "{\"message\" : \"" + result + "\", \"operation\" : \""
				+ type.toLowerCase() + "\", \"code\" : " + status + " }";
	}

}
