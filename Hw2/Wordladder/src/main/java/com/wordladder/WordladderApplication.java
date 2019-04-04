package com.wordladder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.*;

@SpringBootApplication
public class WordladderApplication {
	static public Set<String> dict;

	public static void main(String[] args) {
		SpringApplication.run(WordladderApplication.class, args);
	}

}
