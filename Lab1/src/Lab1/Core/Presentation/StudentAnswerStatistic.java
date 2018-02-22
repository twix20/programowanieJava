package Lab1.Core.Presentation;

import java.util.HashMap;
import java.util.Map;

import Lab1.Core.Student.Student;

public class StudentAnswerStatistic {
	private Student student;
	
	//QuestionId, answeredCorrect
	private Map<Integer, Boolean> answers = new HashMap<>();
	
	public StudentAnswerStatistic(Student student) {
		this.setStudent(student);
	}

	public Map<Integer, Boolean> getAnswers() {
		return answers;
	}

	public void setAnswers(Map<Integer, Boolean> answers) {
		this.answers = answers;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}
}
