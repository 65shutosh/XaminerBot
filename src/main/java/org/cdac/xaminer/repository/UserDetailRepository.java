package org.cdac.xaminer.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.cdac.xaminer.entity.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserDetailRepository extends JpaRepository<Registration, Integer> {

	List<Registration> findByUserName(String string);

	@Query("select id,name,userEmail from Registration where userTag=?1 and approved=?2 order by name")
	List<Registration> findByUserTag(String userTag, String approved);

	@Transactional
	@Modifying
	@Query("delete from Registration where id=?1 and userTag=?2")
	List<Registration> deleteExaminerById(Integer id, String userTag);

	@Modifying
	@Query("update Registration set approved = :#{#approved} where id=:#{#id}")
	public List<Registration> updateById(String approved, Integer id);

//	@Query("select approved from Registration where id=?1")
//	List<Registration> findOneById(Integer id);

}
