package com.aol.micro.server.spring;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import javax.servlet.ServletContextListener;
import javax.sql.DataSource;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import com.aol.micro.server.Plugin;
import com.aol.micro.server.config.Config;
import com.aol.micro.server.servers.model.ServerData;
import com.aol.micro.server.spring.datasource.DataSourceBuilder;
import com.aol.micro.server.spring.datasource.JdbcConfig;
import com.aol.micro.server.spring.datasource.hibernate.HibernateConfig;
import com.aol.micro.server.spring.datasource.hibernate.SpringDataConfig;
import com.aol.micro.server.spring.datasource.jdbc.SQL;

/**
 * 
 * Collections of Spring configuration classes (Classes annotated with @Configuration)
 * that configure various useful pieces of functionality - such as property file loading,
 * datasources, scheduling etc
 * 
 * @author johnmcclean
 *
 */
public class DataPlugin implements Plugin{
	
	@Override
	public Optional<SpringDBConfig> springDbConfigurer(){
		return Optional.of(new SpringConfigurer());
	}
	@Override
	public Set<Class> springClasses() {
		return new HashSet<>(Arrays.asList(JdbcConfig.class,  
						DataSourceBuilder.class, SQL.class, 
						SpringDataConfig.class,
						HibernateConfig.class));
	}

	@Override
	public Set<Function<ServerData, ServletContextListener>> servletContextListeners() {
		return null;
	}

	@Override
	public Set<Class> jaxRsResources() {
	
		return null;
	}

	@Override
	public Set<String> jaxRsPackages() {
	
		return null;
	}

}
