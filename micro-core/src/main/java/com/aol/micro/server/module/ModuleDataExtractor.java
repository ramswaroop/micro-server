package com.aol.micro.server.module;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.Filter;
import javax.servlet.Servlet;

import lombok.AllArgsConstructor;

import org.pcollections.ConsPStack;
import org.pcollections.PStack;
import org.springframework.context.ApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;

import com.aol.micro.server.servers.model.FilterData;
import com.aol.micro.server.servers.model.ServerData;
import com.aol.micro.server.servers.model.ServletData;

@AllArgsConstructor
public class ModuleDataExtractor {

	private final Module module;
	
	public  PStack getRestResources( ApplicationContext rootContext){
		
			List resources = new ArrayList<>();
			module.getRestResourceClasses().forEach(it -> resources.addAll(rootContext.getBeansOfType(it).values()));
			module.getRestAnnotationClasses().forEach(it -> resources.addAll(rootContext.getBeansWithAnnotation(it).values()));
			return ConsPStack.from(resources);
		
	}
	
	public List<FilterData> createFilteredDataList(ServerData data) {
		Map<String,Filter> filterMap = module.getFilters(data);
		return filterMap.entrySet().stream().map( e -> { 
			return new FilterData(e.getKey(), e.getValue().getClass().getName(), new DelegatingFilterProxy(e.getValue()));
		}).collect(Collectors.toList());
	}
	
	public List<ServletData> createServletDataList(ServerData data) {
		Map<String,Servlet> servletMap = module.getServlets(data);
		return servletMap.entrySet().stream().map( e -> { 
			return new ServletData( e.getValue().getClass().getName(), e.getValue().getClass(), e.getKey());
		}).collect(Collectors.toList());
	}
	
}
