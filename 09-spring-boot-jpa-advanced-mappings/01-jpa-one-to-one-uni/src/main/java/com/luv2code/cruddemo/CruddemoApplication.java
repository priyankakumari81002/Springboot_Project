package com.luv2code.cruddemo;

import com.luv2code.cruddemo.dao.AppDAO;
import com.luv2code.cruddemo.entity.Instructor;
import com.luv2code.cruddemo.entity.InstructorDetail;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.swing.*;

@SpringBootApplication
public class CruddemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CruddemoApplication.class, args);}

	@Bean
	public CommandLineRunner commandLineRunner(AppDAO appDAO){
		return runner -> {
			// createInstructor(appDAO);
			// findInstructor(appDAO);
			deleteInstructor(appDAO);
		};
	}

	private void deleteInstructor(AppDAO appDAO) {
		int theId = 1;
		System.out.println("Deleting instructor id:" + theId);
		appDAO.deleteInstructorById(theId);
		System.out.println("Done!");
	}

	private void findInstructor(AppDAO appDAO) {
	 int theId = 2;
	 System.out.println("Finding instructor id:" + theId);
	 Instructor tempInstructor = appDAO.findInstructorById(theId);
	 System.out.println("tempInstructor:" + tempInstructor);
	 System.out.println("the associated instructorDetail only:" + tempInstructor.getInstructorDetail());
	}

	private void createInstructor(AppDAO appDAO) {
		/*
		// create the instructor
		Instructor tempInstructor = new Instructor("Chad","Darby", "darby@luv2code.com");

		//create the instructor detail
		InstructorDetail tempInstructorDetail = new InstructorDetail("http://www.luv2code.com/youtube","Luv 2 code!!!");
         */
		// create the instructor
		Instructor tempInstructor = new Instructor("Priyanka","Kumari", "priyankamahto81001@gmail.com");

		//create the instructor detail
		InstructorDetail tempInstructorDetail = new InstructorDetail("http://www.luv2code.com/youtube","Sleeping");
		// associate the objects
		tempInstructor.setInstructorDetail(tempInstructorDetail);
		// save the Instructor
		//NOTE: this will Also save the details objects because of CascadeType.ALL
		System.out.println("Saving instructors:" + tempInstructor);
		appDAO.save(tempInstructor);
		System.out.println("Done!");
	}
}


