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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "questions")
public class Questions {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Size(min = 2, max = 150, message = "The question should be between 2 and 150 characters")
	private String questionText;

	@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
	private Calendar createdDate;

	@JsonIgnore
	@OneToOne
	private Answers correctAnswer;

	@ManyToOne
	@JsonIgnore
	private Topic topic;

	private Integer preUsed = 0;

	@OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Answers> answers;

	@Override
	public String toString() {
		return "Questions [id=" + id + ", questionText=" + questionText + ", createdDate=" + createdDate
				+ ", correctAnswer=" + correctAnswer + ", topic=" + topic + ", preUsed=" + preUsed + ", answers="
				+ answers + "]";
	}

	public Integer getPreUsed() {
		return preUsed;
	}

	public void setPreUsed(Integer preUsed) {
		this.preUsed = preUsed;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	public Calendar getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Calendar createdDate) {
		this.createdDate = createdDate;
	}

	public Answers getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(Answers correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	public List<Answers> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answers> answers) {
		this.answers = answers;
	}

}
