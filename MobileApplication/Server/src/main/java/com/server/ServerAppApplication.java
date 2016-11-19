package com.server;

import TDG.ReservationTDG;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class ServerAppApplication {

	public static void main(String[] args) throws ClassNotFoundException, SQLException
	{
		ReservationTDG.intitializeIdCounter();
		scheduleReset();
		SpringApplication.run(ServerAppApplication.class, args);
	}

	private static void scheduleReset()
	{
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 0);

		Timer time = new Timer();

		// Start running the task on Saturday at 11:59:59 pm. It will run every week at that time
		time.schedule(new ResetTask(), calendar.getTime(), TimeUnit.DAYS.toMillis(7));
	}
}
