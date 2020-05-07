package org.cdac.xaminer.controller;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.cdac.xaminer.entity.Registration;
import org.cdac.xaminer.repository.UserDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class RegistrationController {

	@Autowired
	UserDetailRepository userRepo;

	@GetMapping(value = "all")
	public List<Registration> getResource() {
		return userRepo.findAll();
	}

	@PostMapping(value = "/save")
	public List<Registration> postRegistration(@RequestBody final Registration registration) {
		userRepo.save(registration);
		return userRepo.findAll();
	}

	@PostMapping(value = "/login")
	public Registration postLogin(@RequestBody Map<String, String> map) {
		map.get("userId");
		map.get("password");

		// userRepo.findByName(map.get("userId"));

		List<Registration> users = userRepo.findByUserName(map.get("userId"));
		for (Iterator iterator = users.iterator(); iterator.hasNext();) {
			Registration registration = (Registration) iterator.next();
			if (registration.getPassword().equals(map.get("password"))) {
				return registration;
			}
		}

		return null;

	}
}
