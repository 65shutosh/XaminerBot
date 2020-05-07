package org.cdac.xaminer.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.cdac.xaminer.entity.Questions;
import org.cdac.xaminer.entity.Registration;
import org.cdac.xaminer.entity.Test;
import org.cdac.xaminer.entity.TestDetails;
import org.cdac.xaminer.entity.Topic;
import org.cdac.xaminer.repository.QuestionsRepository;
import org.cdac.xaminer.repository.TestDetailRepository;
import org.cdac.xaminer.repository.TestRepository;
import org.cdac.xaminer.repository.TopicRepository;
import org.cdac.xaminer.repository.UserDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/examiner")
public class ExaminerController {

	@Autowired
	TopicRepository topicRepository;

	@Autowired
	UserDetailRepository userRepo;

	@Autowired
	QuestionsRepository questionRepo;

	@Autowired
	TestRepository testRepository;

	@Autowired
	TestDetailRepository testDetailRepository;

	@GetMapping(value = "/topics")
	public List<Topic> getResource() {
		return topicRepository.findAll();
	}

	@PostMapping(value = "/topicBulkUpload")
	public Map<String, List<String>> saveTopic(@RequestBody Map<String, List<String>> questionsJsonData) {
		System.out.println(questionsJsonData);
		return questionsJsonData;
	}

	@GetMapping(value = "examinerDetail")
	public List<Registration> getExaminees() {
		System.out.println(userRepo.findByUserTag("examinee", "yes"));
		return userRepo.findByUserTag("examinee", "yes");
	}

	@PostMapping(value = "/subject")
	public List<Topic> getExaminerSubject(@RequestBody Registration registration) {
		return topicRepository.findSubjectByUserId(registration);
	}

	@PostMapping(value = "/subjectTopics")
	public List<Topic> getExaminerSubjectTopics(@RequestBody Topic topic) {
		System.out.println(topicRepository.findTopicBySubjectId(topic.getId()));
		return topicRepository.findTopicBySubjectId(topic.getId());
	}

	@PostMapping(value = "/subjectTopicsId")
	public List<Topic> getExaminerSubjectTopicsId(@RequestBody Topic topic) {
		System.out.println(topicRepository.findTopicIdBySubject(topic.getId()));
		return topicRepository.findTopicIdBySubject(topic.getId());
	}

	@PostMapping(value = "/subjectTopicsQuestions")
	public Map<Integer, List<Questions>> getQuestionsByTopicId(@RequestBody Map<String, List<Integer>> mapOfTopics) {
		System.out.println(mapOfTopics);
		Topic topic = new Topic();
		List<Integer> listOfTopicId = mapOfTopics.get("id");
		Map<Integer, List<Questions>> mapOfQuestionsWithIdUsingTopicId = new HashMap<Integer, List<Questions>>();
//		List<Map<Integer, String>> listOfQuestionsWithId = new ArrayList<Map<Integer, String>>();
//		Map<Integer, String> mapOfQuestionsUsingId = new HashMap<Integer, String>();
		for (Integer id : listOfTopicId) {
			topic.setId(id);
			mapOfQuestionsWithIdUsingTopicId.put(id, questionRepo.getQuestionAndIdByTopicId(topic));
		}
		return mapOfQuestionsWithIdUsingTopicId;

	}

	@PostMapping(value = "/deleteExaminee")
	public List<Registration> deleteExamineeById(@RequestBody Registration registration) {
		userRepo.deleteById(registration.getId());
		return userRepo.findByUserTag("examinee", "yes");
	}

	@GetMapping(value = "examineeDetailForApproval")
	public List<Registration> getExamineesForApproval() {
		System.out.println(userRepo.findByUserTag("examinee", "no"));
		return userRepo.findByUserTag("examinee", "no");
	}

	@PostMapping(value = "/availableTests")
	public List<Test> getAvailableTests(@RequestBody Map<String, Integer> map) {
		System.out.println(map);
		Topic topic = new Topic();
		topic.setId(map.get("subjectId"));
		return testRepository.findBySubjectIdAndUsed(topic, "no");
	}

	@PostMapping(value = "/availableTestsToExaminee")
	public List<Test> getAvailableTestsToExaminee(@RequestBody Map<String, Integer> map) {
		System.out.println(map);
		Topic topic = new Topic();
		topic.setId(map.get("subjectId"));
		return testRepository.findBySubjectIdAndUsed(topic, "yes");
	}

	@PostMapping(value = "/makeTheTestUnAvailable")
	public List<Test> makeTestUnAvailable(@RequestBody Test test) {
		Optional<Test> reg = testRepository.findById(test.getId());
		Test r = reg.get();
		r.setUsedTest("no");
		testRepository.save(r);
		return null;
	}

	@PostMapping(value = "/makeTheTestAvailable")
	public List<Test> makeTestAvailable(@RequestBody Test test) {
		Optional<Test> reg = testRepository.findById(test.getId());
		Test r = reg.get();
		r.setUsedTest("yes");
		testRepository.save(r);
		return null;
	}

	@PostMapping(value = "/approveExaminee")
	public List<Registration> approveExamineeById(@RequestBody Registration registration) {
		Optional<Registration> reg = userRepo.findById(registration.getId());
		Registration r = reg.get();
		r.setApproved("yes");
		userRepo.save(r);
		return userRepo.findByUserTag("examinee", "no");
	}

	@PostMapping(value = "/createTest")
	public List<Questions> creatingTest(@RequestBody Map<String, List<Integer>> listOfQuestionIsInMap) {
		System.out.println("data received");
		System.out.println(listOfQuestionIsInMap);
		List<Integer> questionsList = new ArrayList<Integer>();
		questionsList = listOfQuestionIsInMap.get("id");
		Topic topic = new Topic();
		topic.setId(listOfQuestionIsInMap.get("topicId").get(0));
		Test test = new Test();
		test.setNoOfQuestions(listOfQuestionIsInMap.get("noOfQuestions").get(0));
		test.setTopic(topic);
		test = testRepository.save(test);
		System.out.println(test);

		//// now to save text question details*********************

		Questions questionsTS = new Questions();
		TestDetails saveTestDetail = new TestDetails();

		for (Integer data : questionsList) {
			System.out.println("iterations");
			saveTestDetail.setTest(test);
			questionsTS.setId(data);
			saveTestDetail.setQuestions(questionsTS);
			testDetailRepository.save(saveTestDetail);
			saveTestDetail = new TestDetails();
		}

		for (Integer data : questionsList) {
			System.out.println("iterating for prev used");
			Optional<Questions> questionsPreUsed = questionRepo.findById(data);
			Questions quest = questionsPreUsed.get();
			int pre = quest.getPreUsed();
			quest.setPreUsed(pre + 1);
			questionRepo.save(quest);
			quest = new Questions();
		}

		return null;
	}

}