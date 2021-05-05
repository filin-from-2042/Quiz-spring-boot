package com.quiz.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@SpringBootApplication
@PropertySource("classpath:application.yml")
public class BootApplication {

	@Bean
	MessageSource messageSource()
	{
		ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
		ms.setBasename("bundle");
		ms.setDefaultEncoding("UTF-8");
		return ms;
	}

	public static void main(String[] args) {

		ApplicationContext context = SpringApplication.run(BootApplication.class, args);
		Quiz quiz = context.getBean(Quiz.class);
		quiz.start();
	}

}
