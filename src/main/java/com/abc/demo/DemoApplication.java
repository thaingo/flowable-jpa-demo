package com.abc.demo;

import com.abc.demo.service.MyService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

  @Bean
  public CommandLineRunner init(final MyService myService) {
    return new CommandLineRunner() {
      @Override
      public void run(String... args) throws Exception {
          myService.createDemoUsers();
      }
    };
  }
}
