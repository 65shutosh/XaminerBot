package org.cdac.xaminer.repository;

import java.util.List;

import org.cdac.xaminer.entity.Registration;
import org.cdac.xaminer.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TopicRepository extends JpaRepository<Topic, Integer>{

	@Query("select id,topicName from Topic where registration=?1 ")
	List<Topic> findSubjectByUserId(Registration registration);

	@Query("select topicName from Topic where subjectId=?1")
	List<Topic> findTopicBySubjectId(Integer id);

	
	@Query("select id,topicName from Topic where subjectId=?1")
	List<Topic> findTopicIdBySubject(Integer id);
	
}
//jpql