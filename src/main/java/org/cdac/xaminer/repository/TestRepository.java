package org.cdac.xaminer.repository;

import java.util.List;

import org.cdac.xaminer.entity.Test;
import org.cdac.xaminer.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TestRepository extends JpaRepository<Test, Integer> {

	@Query("select id,createdDate,noOfQuestions from Test where topic=?1 and usedTest=?2")
	List<Test> findBySubjectIdAndUsed(Topic topic, String string);

	List<Test> findByUsedTest(String string);

}
