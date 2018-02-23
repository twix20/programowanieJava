package Lab1.Core.Student;

public class StudentQuestionAnswer {
	private StudentCard student;
	private int testId;
	private int questionId;
	private String option;
	
	public StudentQuestionAnswer(StudentCard student, int questionId, int testId, String option) {
		this.setStudent(student);
		this.setTestId(testId);
		this.setQuestionId(questionId);
		this.setOption(option);
	}
	
	public StudentCard getStudent() {
		return student;
	}
	
	public void setStudent(StudentCard student) {
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
