package Lab1.Core;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
	
	private List<StudentAnswer> studentAnswers = new ArrayList<>();

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

	public List<StudentAnswer> getStudentAnswers() {
		return studentAnswers;
	}

	public void setStudentAnswers(List<StudentAnswer> studentAnswers) {
		this.studentAnswers = studentAnswers;
	}
	
	
}
