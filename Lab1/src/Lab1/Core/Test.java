package Lab1.Core;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import Lab1.Core.Student.Student;
import Lab1.Core.Student.StudentQuestionAnswer;

public class Test {
	@SerializedName("test_id")
	@Expose
	private Integer testId;
	@SerializedName("test_name")
	@Expose
	private String testName;
	@SerializedName("questions")
	@Expose
	private List<Question> questions = null;
	
	private List<Student> studentAnswers = new ArrayList<>();
	
	public boolean isQuestionAnswerCorrect(StudentQuestionAnswer answer) {
		Question question = getQuestionById(answer.getQuestionId());
		
		return question.isStudentQuestionAnswerCorrect(answer);
	}

	public Integer getTestId() {
		return testId;
	}

	public void setTestId(Integer testId) {
		this.testId = testId;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}
	
	public Question getQuestionById(int questionId) {
		return Utilities.toStream(this.getQuestions())
				.filter(q -> q.getId().equals(questionId))
				.findFirst()
				.get();
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}
	
	public int getAnswersCount() {
		int n = 0;
		
		for(Question q: questions) {
			n += q.getAnswers().size();
		}
		
		return n;
	}
	
	public List<Student> getStudentAnswers() {
		return studentAnswers;
	}

	public void setStudentAnswers(List<Student> studentAnswers) {
		this.studentAnswers = studentAnswers;
	}
	
	
}
