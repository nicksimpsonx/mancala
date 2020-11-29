package com.simpsonn.mancala;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * This application was written for a time limited coding challenge, approximately 12 hours has been spent on it.
 *
 * The specification was to create a version of the classic strategy game Mancala using 6 stones per pit.
 * The game should be playable between two human opponents using a REST Api with a tool such as CURL or Postman.
 * There was no requirement for a graphical user interface, the goal was to get as far as possible in the development
 * of the game including as much documentation and tests as possible within the allowed time.
 *
 * rules for the game can be found on the wiki page:
 * https://en.wikipedia.org/wiki/Mancala
 *
 * @author Nick Simpson
 * @since 1.8
 * @version 0.0.1, 08/10/2020
 */

@SpringBootApplication
public class MancalaApplication {

	public static void main(String[] args) {

		SpringApplication.run(MancalaApplication.class, args);
	}
	
}
