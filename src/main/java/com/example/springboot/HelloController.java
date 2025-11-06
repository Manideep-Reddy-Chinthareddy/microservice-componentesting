package com.example.springboot;

import com.example.springboot.service.ExampleServiceClient;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(HelloController.class);
	private final ExampleServiceClient client;

	public HelloController(ExampleServiceClient client) {
        this.client = client;
    }

	@GetMapping("/index-info")
	public String index() {
		String response=client.get(String.class);
		logger.info("Response from example service: {}", response);
		return response;
	}

}
