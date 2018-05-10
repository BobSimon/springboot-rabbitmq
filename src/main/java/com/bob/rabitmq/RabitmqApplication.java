package com.bob.rabitmq;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RabitmqApplication  implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(RabitmqApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
       System.out.println("欢迎来到贵地");
	}



}
