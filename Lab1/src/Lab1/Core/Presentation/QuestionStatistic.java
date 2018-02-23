package Lab1.Core.Presentation;

public class QuestionStatistic {
	private int testId;
	private int questionId;
	
	private int studentsAnsweredCorrectly;
	private int studentsAnsweredIncorrectly;
	
	public int getQuestionId() {
		return questionId;
	}
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	public int getStudentsAnsweredCorrectly() {
		return studentsAnsweredCorrectly;
	}
	public void setStudentsAnsweredCorrectly(int studentsAnsweredCorrectly) {
		this.studentsAnsweredCorrectly = studentsAnsweredCorrectly;
	}
	public int getStudentsAnsweredIncorrectly() {
		return studentsAnsweredIncorrectly;
	}
	public void setStudentsAnsweredIncorrectly(int studentsAnsweredIncorrectly) {
		this.studentsAnsweredIncorrectly = studentsAnsweredIncorrectly;
	}
	public int getTestId() {
		return testId;
	}
	public void setTestId(int testId) {
		this.testId = testId;
	}
}
