package com.springbatch.bdremotepartitioner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class BdPartitionerRemoteJobApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(BdPartitionerRemoteJobApplication.class, args);
		context.close();
	}

}
