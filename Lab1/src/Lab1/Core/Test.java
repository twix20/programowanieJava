package Lab1.Core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import Lab1.Core.Student.StudentCard;
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

	private List<StudentCard> students = new ArrayList<>();
	
	public int calculatePointsScoredForStudent(StudentCard student) {
		
		return student.getAnswers().stream()
				.mapToInt(x -> isQuestionAnswerCorrect(x) ? 1 : 0)
				.sum();
	}

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
		return this.getQuestions()
				.stream()
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

	public int getAllQuestionOptionsCount() {
		return questions.stream()
				.mapToInt(x -> x.getAnswers().size())
				.sum();
	}

	public List<StudentCard> getStudents() {
		return students;
	}

	public void setStudents(List<StudentCard> studentAnswers) {
		this.students = studentAnswers;
	}

	public boolean areAnswersLoaded() {
		return this.getStudents().size() != 0;
	}
	
	public int getMarksCount() {
		return getMarks().size();
	}
	public List<String> getMarks() {
		return Arrays.asList("2.0", "3.0", "3+", "4.0", "4+", "5.0", "5+");
	}

	public double calculateMarkByPoints(int pointsScored) {
		int allPoints = this.getQuestions().size();
		double percentageScored = pointsScored / allPoints;

		return percentageScored < 0.50 ? 2.0
				: percentageScored < 0.55 ? 3.0
						: percentageScored < 0.60 ? 3.5
								: percentageScored < 0.70 ? 4.0
										: percentageScored < 0.80 ? 4.5 
												: percentageScored < 0.90 ? 5.0 : 5.5;
	}
}
