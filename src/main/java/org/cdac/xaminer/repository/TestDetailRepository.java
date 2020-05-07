package org.cdac.xaminer.repository;

import java.util.List;

import org.cdac.xaminer.entity.Questions;
import org.cdac.xaminer.entity.TestDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TestDetailRepository extends JpaRepository<TestDetails, Integer> {

	@Query("select td.questions from TestDetails as td where td.id=?1 ")
	Questions findQuestionById(Integer id);

}
