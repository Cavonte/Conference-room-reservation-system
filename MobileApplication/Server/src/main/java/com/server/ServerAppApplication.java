package com.server;

import TDG.ReservationTDG;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import java.sql.SQLException;

@SpringBootApplication
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class ServerAppApplication {

	public static void main(String[] args) throws ClassNotFoundException, SQLException
	{
		ReservationTDG.intitializeIdCounter();
		SpringApplication.run(ServerAppApplication.class, args);
	}
}
