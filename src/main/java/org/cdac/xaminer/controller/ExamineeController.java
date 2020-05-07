package org.cdac.xaminer.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.cdac.xaminer.entity.Questions;
import org.cdac.xaminer.entity.Test;
import org.cdac.xaminer.entity.TestDetails;
import org.cdac.xaminer.repository.QuestionsRepository;
import org.cdac.xaminer.repository.TestDetailRepository;
import org.cdac.xaminer.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/examinee")
public class ExamineeController {

	@Autowired
	TestRepository testRepo;

	@Autowired
	QuestionsRepository questionRepo;

	@Autowired
	TestDetailRepository testDR;

	@GetMapping(value = "/availableTests")
	public List<Test> getAvailableTestsToExaminee() {
		return testRepo.findByUsedTest("yes");
	}

	@PostMapping(value = "/testDetails")
	public Optional<Test> loadTestDetails(@RequestBody Test test) {

		return testRepo.findById(test.getId());

	}

//	@PostMapping(value = "/loadQuestionP")
//	public Map<Integer, Map<Integer, List<Answers>>> loadQuestionsForTest(
//			@RequestBody Map<String, List<Integer>> listOfQuestionIdInMap) {
//		List<Integer> questionIdList = listOfQuestionIdInMap.get("id");
////		Map<Integer, Map<Integer, List<Answers>>> detail = new HashMap<Integer, Map<Integer, List<Answers>>>();
////		Map<Integer, List<Answers>> questionDetail = new HashMap<Integer, List<Answers>>();
////		List<Answers> list = new ArrayList<Answers>();
////		int i = 0;
//		for (Integer data : questionIdList) {
//			Optional<Questions> optQ = questionRepo.findById(data);
//			Map<Integer, Optional<Questions>> map = new HashMap<Integer, Optional<Questions>>();
//			map.put(data, optQ);
//			System.out.println(map);
//		}
//		
//		return null;
//
//	}
//Map<Integer, Questions> 
	@PostMapping(value = "/loadQuestionP")
	public List<Questions> loadQuestionsForTest(@RequestBody Map<String, List<Integer>> listOfQuestionIdInMap) {
		List<Integer> questionDetailIdList = listOfQuestionIdInMap.get("id");
		List<Integer> questionlist = new ArrayList<Integer>();
		Questions tr = new Questions();
		for (Integer data : questionDetailIdList) {
			tr = testDR.findQuestionById(data);
			System.out.println(tr.toString());
			questionlist.add(tr.getId());
			System.out.println(questionlist);
		}
		List<Questions> lq = new ArrayList<Questions>();
		for (Integer data: questionlist) {
			tr =new Questions();
			Optional<Questions> oq= questionRepo.findById(data);
			tr = oq.get();
			lq.add(tr);		
			System.out.println("/////////////////////////////////////////////////////////////////////////////////");
		System.out.println(lq);
		System.out.println("/////////////////////////////////////////////////////////////////////////////");
		}
		System.out.println("*********************************************************************8");
		System.out.println(lq);
		return lq;

	}
}
