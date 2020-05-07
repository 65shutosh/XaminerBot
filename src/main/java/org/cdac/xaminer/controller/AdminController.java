package org.cdac.xaminer.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.cdac.xaminer.entity.Registration;
import org.cdac.xaminer.entity.Topic;
import org.cdac.xaminer.repository.TopicRepository;
import org.cdac.xaminer.repository.UserDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	TopicRepository topicRepo;

	@Autowired
	UserDetailRepository userRepo;

	@GetMapping(value = "all")
	public List<Registration> allData() {
		return userRepo.findAll();
	}

	@GetMapping(value = "/topics")
	public Map<String, List<String>> getResource() {
		List<Topic> topic_data = topicRepo.findAll();
		Map<String, List<String>> mapOfLists = new HashMap<String, List<String>>();
		for (Topic tp : topic_data) {
			List<String> list = new ArrayList<String>();
			if (tp.getParentId() == null) {
				for (Topic tp2 : topic_data) {
					if (tp.getId() == tp2.getSubjectId()) {
						list.add(tp2.getTopicName());
					}
				}
				mapOfLists.put(tp.getTopicName(), list);
			}

		}
		System.out.println(mapOfLists);
		return mapOfLists;
	}

	@GetMapping(value = "examinerDetail")
	public List<Registration> getExaminers() {
//		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
//		Map<String, String> map = new HashMap<String, String>();
		System.out.println(userRepo.findByUserTag("examiner", "yes"));
		return userRepo.findByUserTag("examiner", "yes");
	}

	@PostMapping(value = "/newSubject")
	public List<Topic> cretingNewSubject(@RequestBody Topic topic) {
//		String topicN = map.get("topicName");
//		Topic topic = new Topic();
//		topic.setTopicName(topicN);
		topicRepo.save(topic);
		System.out.println(topic);
		return topicRepo.findAll();
	}

	@PostMapping(value = "/deleteExaminer")
	public List<Registration> deleteExaminerById(@RequestBody Topic topic) {
		userRepo.deleteById(topic.getId());
		return userRepo.findByUserTag("examiner", "yes");
	}

	@GetMapping(value = "examinerDetailForApproval")
	public List<Registration> getExaminersForApproval() {
		System.out.println(userRepo.findByUserTag("examiner", "no"));
		return userRepo.findByUserTag("examiner", "no");
	}

//	@PostMapping(value = "/approveExaminer")
//	public List<Registration> approveExaminerById(@RequestBody Registration registration) {
//		userRepo.updateById("yes", registration.getId());
//		return userRepo.findByUserTag("examiner", "no");
//	}

	@PostMapping(value = "/approveExaminer")
	public List<Registration> approveExaminerById(@RequestBody Registration registration) {
		Optional<Registration> reg = userRepo.findById(registration.getId());
		Registration r = reg.get();
		r.setApproved("yes");
		userRepo.save(r);
		return userRepo.findByUserTag("examiner", "no");
	}

	@PostMapping(value = "/changePassword")
	public List<Registration> changePassword(@RequestBody Registration registration) {
		Optional<Registration> reg = userRepo.findById(registration.getId());
		Registration r = reg.get();
		r.setPassword(registration.getPassword());
		;
		userRepo.save(r);
		return null;
	}

//	@PostMapping(value = "/approveExaminer")
//	public List<Registration> approveExaminerById(@RequestBody Registration registration) {
//		//userRepo.updateById("yes", registration.getId());
//		Registration reg=userRepo.findon
//		reg.setApproved("yes");
//		return userRepo.findByUserTag("examiner", "no");
//	}

}
