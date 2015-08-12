package com.kana.dev.springboot.contacts.appconfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

import com.kana.dev.springboot.contacts.persistence.PersistencePackageMarker;
import com.kana.dev.springboot.contacts.rest.RESTFulPackageMarker;
import com.kana.dev.springboot.contacts.service.ServicePackageMarker;
import com.kana.dev.springboot.contacts.web.WebPackageMarker;

@ComponentScan(basePackageClasses = { WebPackageMarker.class, RESTFulPackageMarker.class, ServicePackageMarker.class,
		PersistencePackageMarker.class })
@ConfigurationProperties(locations = "classpath:application.properties", ignoreUnknownFields = true)
@EnableAutoConfiguration
public class Application {
	public static void main(final String[] args) {
		SpringApplication.run(Application.class, args);
	}
}