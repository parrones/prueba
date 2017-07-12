package com.mytest.springboot.infrastructure.repository.jpa;

import java.util.Properties;

import javax.persistence.SharedCacheMode;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.orm.jpa.AbstractEntityManagerFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories("com.mytest.springboot.infrastructure.repository.jpa")
@EnableTransactionManagement
public class JPAContext {

	@Bean
	public JpaUserRepository getJPARepository() {
		return new JpaUserRepository();
	}

	@Bean
	@Autowired
	public JpaTransactionManager transactionManager(AbstractEntityManagerFactoryBean entityManagerFactory) {
		
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory.getObject());
		return transactionManager;
	}

	@Bean(name = "entityManagerFactory")
	@Profile({ "dev", "default" })
	@Autowired
	public LocalContainerEntityManagerFactoryBean entityEmbeddedManagerfactory(DataSource dataSource) {
		
		LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();

		entityManagerFactory.setDataSource(dataSource);
		entityManagerFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		entityManagerFactory.setPackagesToScan("com.mytest.springboot.infrastructure.repository.jpa");
		entityManagerFactory.setSharedCacheMode(SharedCacheMode.ENABLE_SELECTIVE);

		Properties jpaProperties = new Properties();
		jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
		jpaProperties.put("hibernate.generate_statistics", true);
		jpaProperties.put("hibernate.show_sql", true);

		entityManagerFactory.setJpaProperties(jpaProperties);

		return entityManagerFactory;
	}

	@Bean(name = "entityManagerFactory")
	@Profile({"local"})
	@Autowired
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
		
		LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();

		entityManagerFactory.setDataSource(dataSource);
		entityManagerFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		entityManagerFactory.setPackagesToScan("com.mytest.springboot.infrastructure.repository.jpa");
		entityManagerFactory.setSharedCacheMode(SharedCacheMode.ENABLE_SELECTIVE);

		Properties jpaProperties = new Properties();
		jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		jpaProperties.put("hibernate.generate_statistics", true);
		jpaProperties.put("hibernate.show_sql", true);
		entityManagerFactory.setJpaProperties(jpaProperties);

		return entityManagerFactory;
	}

	@Bean
	@Profile({"local"})
	@ConfigurationProperties(prefix="datasource.mysql")
	public DataSource dataSource(Environment env) {
		
		return DataSourceBuilder.create().build();
	}

	@Bean
	@Profile({ "dev", "default" })
	public EmbeddedDatabase embeddedDatabase() {
		return new EmbeddedDatabaseBuilder().addScript("classpath:schema.sql").build();
	}
}
