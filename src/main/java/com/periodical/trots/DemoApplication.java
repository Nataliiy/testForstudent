package com.periodical.trots;

import com.periodical.trots.entities.*;
import com.periodical.trots.services.*;
import com.periodical.trots.services.impl.UserServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;

@SpringBootApplication
@EnableScheduling
public class DemoApplication {

	private final PeriodicalService periodicalService;
	private final PublisherService publisherService;
	private final SubjectService subjectService;
	private final PeriodicalHasSubjectService periodicalHasSubjectService;
	private final UserServiceImpl userService;
	private final StatusService statusService;
	private final ReceiptService receiptService;
	private final PeriodicalHasReceiptService periodicalHasReceiptService;

	public DemoApplication(PeriodicalService periodicalService, PublisherService publisherService, SubjectService subjectService, PeriodicalHasSubjectService periodicalHasSubjectService, UserServiceImpl userService, StatusService statusService, ReceiptService receiptService, PeriodicalHasReceiptService periodicalHasReceiptService) {
		this.periodicalService = periodicalService;
		this.publisherService = publisherService;
		this.subjectService = subjectService;
		this.periodicalHasSubjectService = periodicalHasSubjectService;
		this.userService = userService;
		this.statusService = statusService;
		this.receiptService = receiptService;
		this.periodicalHasReceiptService = periodicalHasReceiptService;
	}

	@PostConstruct
	public void init(){
		if (userService.getAll().isEmpty()){
			subjectService.save(new SubjectEntity(1, "History"));
			subjectService.save(new SubjectEntity(2, "Art"));
			subjectService.save(new SubjectEntity(3, "Math"));
			subjectService.save(new SubjectEntity(4, "Brain"));
			subjectService.save(new SubjectEntity(5, "Space"));

			statusService.save(new StatusEntity(1, "ordered"));
			statusService.save(new StatusEntity(2, "accepted"));
			statusService.save(new StatusEntity(3, "payed"));
			statusService.save(new StatusEntity(4, "canceled"));

			publisherService.save(new PublisherEntity(1, "Abetka", "380999999991"));
			publisherService.save(new PublisherEntity(2, "UkrainePrint", "380999999992"));
			publisherService.save(new PublisherEntity(3, "PrintKids", "380999999993"));
			publisherService.save(new PublisherEntity(4, "PrintAdults", "380999999994"));
			publisherService.save(new PublisherEntity(5, "Gamers", "380999999995"));
			publisherService.save(new PublisherEntity(6, "Programs", "380999999996"));

			periodicalService.addPeriodical(new PeriodicalEntity(1, "BrainBook", 132, 11, 3, BigDecimal.valueOf(200.0), "Description", 4.3, publisherService.findAll().get(1), "101things.jpg"));
			periodicalService.addPeriodical(new PeriodicalEntity(2, "SecondBook", 123, 1, 3, BigDecimal.valueOf(100), "Description", 5.0, publisherService.findAll().get(2), "popularity.jpg"));
			periodicalService.addPeriodical(new PeriodicalEntity(3, "ThirdBook", 312, 2, 3, BigDecimal.valueOf(10), "Description", 2.1, publisherService.findAll().get(3), "rest.jpg"));
			periodicalService.addPeriodical(new PeriodicalEntity(4, "FourthBook", 523, 3, 3, BigDecimal.valueOf(1000), "Description", 2.2, publisherService.findAll().get(4), "java.jpg"));
			periodicalService.addPeriodical(new PeriodicalEntity(5, "FifthBook", 54, 12, 3, BigDecimal.valueOf(300), "Description", 3.2, publisherService.findAll().get(5), "garden.jpg"));
			periodicalService.addPeriodical(new PeriodicalEntity(6, "SixthBook", 132, 24, 3, BigDecimal.valueOf(314), "Description", 1.1, publisherService.findAll().get(0), "covid.jpg"));
			periodicalService.addPeriodical(new PeriodicalEntity(7, "SeventhBook", 2, 10, 3, BigDecimal.valueOf(251.22), "Description", 4.2, publisherService.findAll().get(1), "environment.jpg"));
			periodicalService.addPeriodical(new PeriodicalEntity(8, "EighthBook", 32, 1, 3, BigDecimal.valueOf(211), "Description", 5.0, publisherService.findAll().get(2), "brain.jpg"));

			periodicalHasSubjectService.save(new PeriodicalHasSubjectEntity(new PeriodicalHasSubjectEntityId(1, 1), subjectService.findAll().get(0), periodicalService.getAllPeriodicals().get(0)));
			periodicalHasSubjectService.save(new PeriodicalHasSubjectEntity(new PeriodicalHasSubjectEntityId(1, 2), subjectService.findAll().get(1), periodicalService.getAllPeriodicals().get(0)));
			periodicalHasSubjectService.save(new PeriodicalHasSubjectEntity(new PeriodicalHasSubjectEntityId(1, 3), subjectService.findAll().get(2), periodicalService.getAllPeriodicals().get(0)));
			periodicalHasSubjectService.save(new PeriodicalHasSubjectEntity(new PeriodicalHasSubjectEntityId(2, 1), subjectService.findAll().get(0), periodicalService.getAllPeriodicals().get(1)));
			periodicalHasSubjectService.save(new PeriodicalHasSubjectEntity(new PeriodicalHasSubjectEntityId(3, 1), subjectService.findAll().get(0), periodicalService.getAllPeriodicals().get(2)));
			periodicalHasSubjectService.save(new PeriodicalHasSubjectEntity(new PeriodicalHasSubjectEntityId(4, 2), subjectService.findAll().get(1), periodicalService.getAllPeriodicals().get(3)));
			periodicalHasSubjectService.save(new PeriodicalHasSubjectEntity(new PeriodicalHasSubjectEntityId(4, 3), subjectService.findAll().get(2), periodicalService.getAllPeriodicals().get(3)));
			periodicalHasSubjectService.save(new PeriodicalHasSubjectEntity(new PeriodicalHasSubjectEntityId(5, 4), subjectService.findAll().get(3), periodicalService.getAllPeriodicals().get(4)));
			periodicalHasSubjectService.save(new PeriodicalHasSubjectEntity(new PeriodicalHasSubjectEntityId(6, 2), subjectService.findAll().get(2), periodicalService.getAllPeriodicals().get(5)));
			periodicalHasSubjectService.save(new PeriodicalHasSubjectEntity(new PeriodicalHasSubjectEntityId(7, 3), subjectService.findAll().get(3), periodicalService.getAllPeriodicals().get(6)));
			periodicalHasSubjectService.save(new PeriodicalHasSubjectEntity(new PeriodicalHasSubjectEntityId(8, 1), subjectService.findAll().get(1), periodicalService.getAllPeriodicals().get(7)));

			userService.save(new UserEntity(1, "Username", "email@email.com", "Password123!", "380963604811", "adminName", "adminSurname", "admin", "address"));

		}
		System.out.println("File  created");
	}


	public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);

    }

}
