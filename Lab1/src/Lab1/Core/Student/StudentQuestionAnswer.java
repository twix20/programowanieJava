package Lab1.Core.Student;

public class StudentQuestionAnswer {
	private Student student;
	private int testId;
	private int questionId;
	private String option;
	
	public StudentQuestionAnswer(Student student, int questionId, int testId, String option) {
		this.setStudent(student);
		this.setTestId(testId);
		this.setQuestionId(questionId);
		this.setOption(option);
	}
	
	public Student getStudent() {
		return student;
	}
	
	public void setStudent(Student student) {
		this.student = student;
	}
	public int getQuestionId() {
		return questionId;
	}
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	public String getOption() {
		return option;
	}
	public void setOption(String option) {
		this.option = option;
	}

	public int getTestId() {
		return testId;
	}

	public void setTestId(int testId) {
		this.testId = testId;
	}


}
