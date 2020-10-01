package ru.marchenko.regionsdirectory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author Created by Vladislav Marchenko on 30.09.2020
 */
@SpringBootApplication
@EnableCaching
public class RegionsDirectoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(RegionsDirectoryApplication.class, args);
	}

}
