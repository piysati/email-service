package com.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import com.project.dto.NotificationDTO;
import com.project.exception.BaseException;
import com.project.model.Notification;
import com.project.model.User;
import com.project.service.INotificationService;
import com.project.service.IUserService;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

	@Autowired
	private INotificationService notificationService;

	@Autowired
	private IUserService userService;

	@GetMapping("/hi")
	public String getSome(){
		System.out.println("it has triggered");
		return "HI";
	}

	@PostMapping("/notify/{userId}")
	public ResponseEntity<Object> notifyUser(@PathVariable("userId") String userId,
			@RequestBody NotificationDTO notification) throws BaseException {
		System.out.println("entered method");
		if (!StringUtils.isEmpty(userId) && !ObjectUtils.isEmpty(notification)) {
			notificationService.notify(userId, notification);
			return new ResponseEntity<>("Success", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/get/users")
	public ResponseEntity<List<User>> getUsers() throws BaseException {
		List<User> users = userService.getUsers();
		if (!ObjectUtils.isEmpty(users)) {
			return new ResponseEntity<>(users, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/get/notification/{userId}")
	public ResponseEntity<List<Notification>> getNotifications(@PathVariable("userId") String userId)
			throws BaseException {
		List<Notification> notifList = notificationService.getNotifications(userId);
		if (!ObjectUtils.isEmpty(notifList)) {
			return new ResponseEntity<>(notifList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
