package com.typeface.imageconnect;

import com.typeface.imageconnect.service.CommentService;
import com.typeface.imageconnect.service.ImageService;
import com.typeface.imageconnect.service.ImageShareService;
import com.typeface.imageconnect.service.NotificationService;
import com.typeface.imageconnect.service.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class ImageConnectApplication {

	private final UserService userService;


	private final ImageService imageService;
	private final ImageShareService imageShareService;
	private final CommentService commentService;
	private final NotificationService notificationService;


	@Autowired
	public ImageConnectApplication(UserService userService, ImageService imageService,
			ImageShareService imageShareService, CommentService commentService,
			NotificationService notificationService) {
		this.userService = userService;
		this.imageService = imageService;
		this.imageShareService = imageShareService;
		this.commentService = commentService;
		this.notificationService = notificationService;
	}

	public static void main(String[] args) {
		SpringApplication.run(ImageConnectApplication.class, args);
	}



	@PreDestroy
	public void deleteAllRecords() {
		userService.deleteAllRecords();
		imageShareService.deleteAllRecords();
		imageService.deleteAllRecords();
		commentService.deleteAllRecords();
		notificationService.deleteAllRecords();

	}

	/**
	 * This script will do the heavy lifting of generating and sharing the data
	 */

	@PostConstruct
	public void startUpScript() {
		userService.createDummyUsers();
		imageService.uploadImagesToS3AndAssignOwners();
		imageShareService.shareImages();
	}



}
