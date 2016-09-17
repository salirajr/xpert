package com.xybase.ax.eai.waconsole.access.common;

import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.PollableChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.xybase.ax.eai.waconsole.appliance.Transform;
import com.xybase.ax.eai.waconsole.appliance.mock.model.Mock;

@Controller
public class Transformer {

	
	public String view() {
		// TODO Auto-generated method stub
		return "home_transform";
	}
	
	@RequestMapping(value = "/transform/home")
	public String view(Model model,
			@RequestParam(value = "transform-id", required = false) String transformId) {
		model.addAttribute("transformId", transformId);
		System.out.println(transformId + " is ined");
		return view();
	}
	
	@RequestMapping(value = "/transform/detail_transform_rule")
	public String view(Model model) {
		return "transform/" + "detail_transform_rule";
	}
	/*
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/transform/{typefrom}/{typeto}/context", method = RequestMethod.POST)
	public @ResponseBody String send(@PathVariable String typefrom,
			@PathVariable String typeto, @RequestBody String data) {
		Gson gson = new Gson();
		
		JsonObject response = new JsonObject();

		JsonObject jsonData = gson.fromJson(data, JsonObject.class);
		String context = jsonData.get("context").toString().replaceAll(StringUtil.RegX.whitespace, " ");

		context = context.toString().replaceAll("extract-parameter", typefrom.toLowerCase() + "Extractor");
		context = context.toString().replaceAll("inject-parameter", typeto.toLowerCase() + "Injector");
		context = context.toString().replaceAll("converter-parameter-in", typefrom.toLowerCase() + "Converter");
		context = context.toString().replaceAll("converter-parameter-out", typeto.toLowerCase() + "Converter");
		context = context.toString().replaceAll("\\\\r", "");
		context = context.toString().replaceAll("\\\\n", "");
		context = context.toString().replaceAll("\\\\t", "");
		context = context.toString().replaceAll("\\\\", "");
		context = context.toString().substring(1, context.toString().length()-1);
		
		System.out.println("context:\n"+context);

		try {
			GenericApplicationContext gAContext = new GenericApplicationContext();
			XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(
					gAContext);
			reader.loadBeanDefinitions(new ByteArrayResource(context.getBytes()));
			gAContext.refresh();

			MessageChannel channel = gAContext.getBean("channelIn",
					MessageChannel.class);
			channel.send(new GenericMessage<String>(data));

			PollableChannel channelOut = gAContext.getBean("channelOut",
					PollableChannel.class);
			Message responseIn = channelOut.receive();
			response.addProperty("payload", responseIn.getPayload().toString());

		} catch (Exception e) {
			response.addProperty("payload", e.getMessage());
			e.printStackTrace();
		}
		System.out.println("response => " + gson.toJson(response));
		return gson.toJson(response);
	}*/
	
	@RequestMapping(value = "/transform/{typefrom}/{typeto}/context", method = RequestMethod.POST, headers = { "Content-type=application/json" })
	public @ResponseBody String mockup(@PathVariable String typefrom,
			@PathVariable String typeto,@RequestBody Transform transform) {
		System.out.println(transform.toString());
		Gson gson = new Gson();
		JsonObject response = new JsonObject();
		
		String context = transform.getContext();
		context = context.toString().replaceAll("extract-parameter", typefrom.toLowerCase() + "Extractor");
		context = context.toString().replaceAll("inject-parameter", typeto.toLowerCase() + "Injector");
		context = context.toString().replaceAll("converter-parameter-in", typefrom.toLowerCase() + "Converter");
		context = context.toString().replaceAll("converter-parameter-out", typeto.toLowerCase() + "Converter");

		try {
			GenericApplicationContext gAContext = new GenericApplicationContext();
			XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(
					gAContext);
			reader.loadBeanDefinitions(new ByteArrayResource(context.getBytes()));
			gAContext.refresh();

			MessageChannel channel = gAContext.getBean("channelIn",
					MessageChannel.class);
			channel.send(new GenericMessage<Transform>(transform));

			PollableChannel channelOut = gAContext.getBean("channelOut",
					PollableChannel.class);
			@SuppressWarnings("rawtypes")
			Message responseIn = channelOut.receive();
			response.addProperty("payload", responseIn.getPayload().toString());

		} catch (Exception e) {
			response.addProperty("payload", e.getMessage());
			e.printStackTrace();
		}
		System.out.println("response => " + gson.toJson(response));
		return gson.toJson(response);
	}
	
	@RequestMapping(value = "/transform/get/context", method = RequestMethod.POST, headers = { "Content-type=application/json" })
	public @ResponseBody String mockup(@RequestBody Mock mock) {
		Gson gson = new Gson();
		String xmlContext ="<?xml version='1.0' encoding='UTF-8'?> <br/> "+
				"<beans xmlns='http://www.springframework.org/schema/beans' <br/> "+ 
				" 	xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'  <br/> "+
				" 	xsi:schemaLocation='http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-4.2.xsd  <br/> "+
				" 		http://www.springframework.org/schema/integration http://www.springframework.org/schema/integration/spring-integration-4.2.xsd  <br/> "+
				" 		http://www.springframework.org/schema/integration/http http://www.springframework.org/schema/integration/http/spring-integration-http-4.2.xsd'  <br/> "+
				" 	xmlns:int='http://www.springframework.org/schema/integration'  <br/> "+
				" 	xmlns:int-http='http://www.springframework.org/schema/integration/http'>  <br/> "+
				"   <br/> "+
				" 	<bean id='xeaiDataSource'  <br/> "+
				" 		class='org.springframework.jdbc.datasource.DriverManagerDataSource'>  <br/> "+
				" 		<property name='driverClassName' value='org.postgresql.Driver' />  <br/> "+
				" 		<property name='url'  <br/> "+
				" 			value='jdbc:postgresql://192.168.88.75:5432/xeai-nightly' />  <br/> "+
				" 		<property name='username' value='postgres' />  <br/> "+
				" 		<property name='password' value='postgres' />  <br/> "+
				" 	</bean>  <br/> "+
				"  <br/> "+ 
				" 	<!-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ -->  <br/> "+
				"   <br/> "+
				" 	<bean id='transformDao'  <br/> "+
				" 		class='com.xybase.ax.eai.archcomp.transformer.template.dao.TemplateTemplateDaoImpl'>  <br/> "+
				" 		<property name='dataSource' ref='xeaiDataSource' />  <br/> "+
				" 		<property name='query'  <br/> "+
				" 			value='select transform_template, transform_type from xeai_transform where transform_id = ?'></property>  <br/> "+
				" 	</bean>  <br/> "+
				"  <br/> "+ 
				" 	<bean id='transformRuleDao'  <br/> "+
				" 		class='com.xybase.ax.eai.archcomp.transformer.rule.dao.TransformerRuleDaoImpl'>  <br/> "+
				" 		<property name='dataSource' ref='xeaiDataSource' />  <br/> "+
				" 		<property name='query'  <br/> "+
				" 			value='select transform_rule_id, transform_rule_source, transform_rule_target, transform_rule_config, transform_rule_matrix from xeai_transform_rule where transform_rule_config != 0 and transform_id = ? order by transform_rule_id asc'></property>  <br/> "+
				" 	</bean>  <br/> "+
				"  <br/> "+ 
				" 	<bean id='auditLogConfigDao'  <br/> "+
				" 		class='com.xybase.ax.eai.archcomp.common.audit.config.dao.AuditLogConfigDaoImpl'>  <br/> "+
				" 		<property name='dataSource' ref='xeaiDataSource' />  <br/> "+
				" 		<property name='query'  <br/> "+
				" 			value='select audit_param, audit_correlation, audit_config, audit_type from xeai_audit_log_config where  audit_event = ? and audit_key = ? and audit_level = ?'></property>  <br/> "+
				" 	</bean>  <br/> "+
				"  <br/> "+ 
				"  <br/> "+ 
				" 	<bean id='xeaiErrorSequence' class='com.xybase.ax.eai.archcomp.common.dao.SoleRetrieval'>  <br/> "+
				" 		<property name='dataSource' ref='xeaiDataSource' />  <br/> "+
				" 		<constructor-arg value='select xeai_error_id_sequence() as nextval' />  <br/> "+
				" 		<constructor-arg value='nextval' />  <br/> "+
				" 	</bean>  <br/> "+
				"  <br/> "+ 
				" 	<bean id='dataLookupDao' class='com.xybase.ax.eai.archcomp.lookup.dao.LookupDaoImpl'>  <br/> "+
				" 		<property name='dataSource' ref='xeaiDataSource' />  <br/> "+
				" 		<property name='query'  <br/> "+
				" 			value='select key, value from xeai_lookup_data where name = ?'></property>  <br/> "+
				" 	</bean>  <br/> "+
				"  <br/> "+ 
				" 	<!-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ -->  <br/> "+
				"  <br/> "+ 
				" 	<bean id='transfromerFactory'  <br/> "+
				" 		class='com.xybase.ax.eai.archcomp.transformer.factory.TransfromerFactory'>  <br/> "+
				" 		<property name='transformDao' ref='transformDao' />  <br/> "+
				" 		<property name='transformRuleDao' ref='transformRuleDao' />  <br/> "+
				" 	</bean>  <br/> "+
				"   <br/> "+
				" 	<bean id='objectExtractor'  <br/> "+
				" 		class='com.xybase.ax.eai.archcomp.transformer.extract.ObjectExtractor' />  <br/> "+
				"   <br/> "+
				" 	<bean id='jsonExtractor'  <br/> "+
				" 		class='com.xybase.ax.eai.archcomp.transformer.extract.JsonExtractor' />  <br/> "+ 
				"   <br/> "+
				" 	<bean id='jsonInjector'  <br/> "+
				" 		class='com.xybase.ax.eai.archcomp.transformer.inject.JsonInjector' />  <br/> "+
				"   <br/> "+
				" 	<bean id='selfAssignmentObjectInjector'  <br/> "+
				" 		class='com.xybase.ax.eai.archcomp.transformer.inject.ObjectInjector'>  <br/> "+
				" 		<property name='selfAssignment' value='true' />  <br/> "+
				" 	</bean>  <br/> "+
				"  <br/> "+ 
				" 	<bean id='objectInjector'  <br/> "+
				" 		class='com.xybase.ax.eai.archcomp.transformer.inject.ObjectInjector'>  <br/> "+
				" 	</bean>  <br/> "+
				"   <br/> "+
				" 	<bean id='objectConverter'  <br/> "+
				" 		class='com.xybase.ax.eai.archcomp.message.converter.util.ObjectConverter' />  <br/> "+
				"   <br/> "+
				" 	<bean id='jsonConverter'  <br/> "+
				" 		class='com.xybase.ax.eai.archcomp.message.converter.util.JsonConverter' />  <br/> "+
				"  <br/> "+ 
				" 	<bean id='stringUtil' class='com.xybase.ax.eai.archcomp.common.util.StringUtil' />  <br/> "+
				" 	<bean id='dateUtil' class='com.xybase.ax.eai.archcomp.common.util.DateUtil' />  <br/> "+
				" 	<bean id='transformUtil' class='com.xybase.ax.eai.archcomp.common.util.TransformUtil' />  <br/> "+
				"   <br/> "+
				" 	<bean id='transformationSpELProcessor'  <br/> "+
				" 		class='com.xybase.ax.eai.archcomp.transformer.util.SpELProcessor'>  <br/> "+
				" 		<property name='utils'>  <br/> "+
				" 			<array>  <br/> "+
				" 				<ref bean='stringUtil' />  <br/> "+
				" 				<ref bean='dateUtil' />  <br/> "+
				" 				<ref bean='transformUtil' />  <br/> "+
				" 			</array>  <br/> "+
				" 		</property>  <br/> "+
				" 	</bean>  <br/> "+
				"  <br/> "+ 
				" 	<!-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ -->  <br/> "+
				"  <br/> "+ 
				" 	<int:channel id='channelIn' />  <br/> "+
				"  <br/> "+ 
				"  <br/> "+ 
				" 	<int:chain input-channel='channelIn' output-channel='channelOut'>  <br/> "+
				" 		<int:service-activator ref='postgresNotifyHandler'  <br/> "+
				" 			method='handle'></int:service-activator>  <br/> "+
				" 		<int:transformer expression='payload.asAcdmTemplate()' />  <br/> "+
				" 		<int:transformer ref='jsonConverter' method='toContext' />  <br/> "+
				" 		<int:service-activator ref='transformHandler'  <br/> "+
				" 			method='handle'></int:service-activator>  <br/> "+
				" 		<int:transformer ref='jsonConverter' method='toString' />  <br/> "+
				" 	</int:chain>  <br/> "+
				"   <br/> "+
				" 	<bean id='postgresNotifyHandler'  <br/> "+
				" 		class='com.xybase.ax.eai.archcomp.xyscrib.handler.PostgresNotifyHandler'>  <br/> "+
				" 		<property name='payloadKey'>  <br/> "+
				" 			<bean class='com.xybase.ax.eai.archcomp.lookup.Lookup'  <br/> "+
				" 				factory-bean='dataLookupDao' factory-method='get'>  <br/> "+
				" 				<constructor-arg value='POSTGRESNOTIFICATION.PAYLOAD.KEY' />  <br/> "+
				" 			</bean>  <br/> "+
				" 		</property>  <br/> "+
				" 	</bean>  <br/> "+
				"  <br/> "+ 
				" 	<bean id='transformHandler' class='com.xybase.ax.eai.waconsole.archcomp.handler.TransformHandler'>  <br/> "+
				" 		<property name='payloadCycles' value='true' />  <br/> "+
				" 		<property name='transformer'>  <br/> "+
				" 			<bean class='com.xybase.ax.eai.archcomp.transformer.TransformerImpl'  <br/> "+
				" 				>  <br/> "+
				" 				<property name='extractor' ref='jsonExtractor' />  <br/> "+
				" 				<property name='injector' ref='jsonInjector' />  <br/> "+
				" 				<property name='processor' ref='transformationSpELProcessor' />  <br/> "+
				" 			</bean>  <br/> "+
				" 		</property>  <br/> "+
				" 		<property name='auditLogConfigIn'>  <br/> "+
				" 			<bean class='com.xybase.ax.eai.archcomp.common.audit.config.AuditLogConfig'  <br/> "+
				" 				factory-bean='auditLogConfigDao' factory-method='get'>  <br/> "+
				" 				<constructor-arg value='AXPgDataXCHG' index='0' />  <br/> "+
				" 				<constructor-arg value='5000' index='1' />  <br/> "+
				" 				<constructor-arg value='0' index='2' />  <br/> "+
				" 			</bean>  <br/> "+
				" 		</property>  <br/> "+
				" 		<property name='auditLogConfigOut'>  <br/> "+
				" 			<bean class='com.xybase.ax.eai.archcomp.common.audit.config.AuditLogConfig'  <br/> "+
				" 				factory-bean='auditLogConfigDao' factory-method='get'>  <br/> "+
				" 				<constructor-arg value='AXPgDataXCHG' index='0' />  <br/> "+
				" 				<constructor-arg value='5000' index='1' />  <br/> "+
				" 				<constructor-arg value='1' index='2' />  <br/> "+
				" 			</bean>  <br/> "+
				" 		</property>  <br/> "+
				" 	</bean>  <br/> "+
				"  <br/> "+ 
				" 	<int:channel id='channelOut'>  <br/> "+
				" 		<int:queue />  <br/> "+
				" 	</int:channel>  <br/> "+
				"  <br/> "+ 
				" </beans>";
		JsonObject response = new JsonObject();
		response.addProperty("payload",xmlContext);
		return gson.toJson(response);
	}
	
}
