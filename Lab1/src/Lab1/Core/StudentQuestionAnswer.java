package Lab1.Core;

public class StudentQuestionAnswer {
	private StudentAnswer student;
	private int questionId;
	private String option;
	
	public StudentQuestionAnswer(StudentAnswer student, int questionId, String option) {
		this.setStudent(student);
		this.questionId = questionId;
		this.option = option;
	}
	
	public StudentAnswer getStudent() {
		return student;
	}
	
	public void setStudent(StudentAnswer student) {
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


}
