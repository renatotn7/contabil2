package br.com.cvm.rest;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		// TODO Stub de método gerado automaticamente
		 SpringApplication app=	new  SpringApplication(Application.class);
		 /*app.setDefaultProperties(Collections
		          .singletonMap("server.port", "8081"));*/
		
		        app.run(args);
	}

}
