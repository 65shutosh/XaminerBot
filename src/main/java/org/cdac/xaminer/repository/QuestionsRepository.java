package org.cdac.xaminer.repository;

import java.util.List;

import org.cdac.xaminer.entity.Questions;
import org.cdac.xaminer.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QuestionsRepository extends JpaRepository<Questions, Integer>{

	
	@Query("select id,questionText,preUsed from Questions where topic=?1 ")
	List<Questions> getQuestionAndIdByTopicId(Topic topic);

	

}
