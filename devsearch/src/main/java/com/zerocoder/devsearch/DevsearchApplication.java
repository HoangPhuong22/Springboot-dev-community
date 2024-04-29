package com.zerocoder.devsearch;

import com.zerocoder.devsearch.dao.ProfileDAO;
import com.zerocoder.devsearch.dao.UserDAO;
import com.zerocoder.devsearch.entity.Profile;
import com.zerocoder.devsearch.entity.Project;
import com.zerocoder.devsearch.entity.User;
import com.zerocoder.devsearch.service.ProfileService;
import com.zerocoder.devsearch.service.ProjectService;
import com.zerocoder.devsearch.service.UserService;
import jakarta.persistence.EntityManager;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
public class DevsearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(DevsearchApplication.class, args);
	}
//	@Bean
//	public CommandLineRunner run(ProjectService projectService) {
//		return (args) -> {
//			Project project = projectService.getProjectTagById(3L);
//			System.out.println(project);
//			System.out.println(project.getTag());
//		};
//	}
}
