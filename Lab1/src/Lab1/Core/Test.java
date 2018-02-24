package Lab1.Core;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
	
	private final List<MarkRange> marks = Arrays.asList(
			new MarkRange(0.00, 0.50, "2.0"),
			new MarkRange(0.51, 0.55, "3.0"),
			new MarkRange(0.56, 0.60, "3.5"),
			new MarkRange(0.61, 0.70, "4.0"),
			new MarkRange(0.71, 0.80, "4.5"),
			new MarkRange(0.81, 0.90, "5.0"),
			new MarkRange(0.91, 1.00, "5.5")
		);

	public int calculatePointsScoredForStudent(StudentCard student) {

		return student.getAnswers().stream().mapToInt(x -> isQuestionAnswerCorrect(x) ? 1 : 0).sum();
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
		return this.getQuestions().stream().filter(q -> q.getId().equals(questionId)).findFirst().get();
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public int getAllQuestionOptionsCount() {
		return questions.stream().mapToInt(x -> x.getAnswers().size()).sum();
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

	public List<MarkRange> getMarks() {
		return marks;
	}

	public String calculateMarkByPoints(int pointsScored) {
		int allPoints = this.getQuestions().size();
		double percentageScored = pointsScored / allPoints;
		
		for(MarkRange r: getMarks()) {
			if(r.isInRange(percentageScored))
				return r.getMark();
		}
		
		return "UNKNOWN";
	}

	public class MarkRange {
		private double from;
		private double to;
		private String mark;

		public MarkRange(double from, double to, String mark) {
			this.from = from;
			this.to = to;
			this.mark = mark;
		}

		public double getFrom() {
			return from;
		}

		public double getTo() {
			return to;
		}

		public String getMark() {
			return mark;
		}
		
		public boolean isInRange(double p) {
			double r = round(p);
			
			return from < r && r < to;
		}
		
		private double round(double v) {
			return new BigDecimal(v).setScale(2, RoundingMode.HALF_UP).doubleValue();
		}
	}
}
