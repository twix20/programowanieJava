package Lab1.Core.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Student {
	private int studentId;
	private String firstName;
	private String lastName;
	
	private List<StudentQuestionAnswer> answers = new ArrayList<>();

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public List<StudentQuestionAnswer> getAnswersForQuestion(int questionId){
		return getAnswers()
				.stream()
				.filter(q -> q.getQuestionId() == questionId)
				.collect(Collectors.toList());
	}

	public List<StudentQuestionAnswer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<StudentQuestionAnswer> answers) {
		this.answers = answers;
	}
	
	
}
