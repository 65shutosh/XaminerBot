package org.cdac.xaminer.entity;

import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Test")
public class Test {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
	private Calendar createdDate;

	@OneToMany(mappedBy = "test", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<TestDetails> testDetails;

	@JsonIgnore
	@ManyToOne
	public Topic topic;
	
	public String usedTest="no";
	
	public Integer noOfQuestions;
	
	public String getUsedTest() {
		return usedTest;
	}

	public void setUsedTest(String usedTest) {
		this.usedTest = usedTest;
	}

	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	public Integer getNoOfQuestions() {
		return noOfQuestions;
	}

	public void setNoOfQuestions(Integer noOfQuestions) {
		this.noOfQuestions = noOfQuestions;
	}

	public Calendar getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Calendar createdDate) {
		this.createdDate = createdDate;
	}

	public List<TestDetails> getTestDetails() {
		return testDetails;
	}

	public void setTestDetails(List<TestDetails> testDetails) {
		this.testDetails = testDetails;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Test [id=" + id + ", createdDate=" + createdDate + ", testDetails=" + testDetails + ", topic=" + topic
				+ ", usedTest=" + usedTest + ", noOfQuestions=" + noOfQuestions + "]";
	}

	
}
