
package br.com.cvm.rest;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		// TODO Stub de método gerado automaticamente
		 SpringApplication.run(Application.class,args);
	
		System.out.println("ola");
		 
	}
	  /*@Bean
	  public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
	    return args -> {

	      System.out.println("Let's inspect the beans provided by Spring Boot:");

	      String[] beanNames = ctx.getBeanDefinitionNames();
	      Arrays.sort(beanNames);
	      for (String beanName : beanNames) {
	        System.out.println(beanName);
	      }

	    };
	  }*/

}
