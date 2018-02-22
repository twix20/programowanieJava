package Lab1.Core.Presentation;

import java.util.ArrayList;
import java.util.List;

import Lab1.Core.Test;

public class PresentationResult {
	private Test test;
	
	private List<QuestionStatistic> questionStatistics = new ArrayList<>();
	private List<StudentAnswerStatistic> studentStatistics = new ArrayList<>();
	
	public PresentationResult(Test test) {
		this.setTest(test);
	}

	public List<QuestionStatistic> getQuestionStatistics() {
		return questionStatistics;
	}

	public void setQuestionStatistics(List<QuestionStatistic> questionStatistics) {
		this.questionStatistics = questionStatistics;
	}

	public List<StudentAnswerStatistic> getStudentStatistics() {
		return studentStatistics;
	}

	public void setStudentStatistics(List<StudentAnswerStatistic> studentStatistics) {
		this.studentStatistics = studentStatistics;
	}

	public Test getTest() {
		return test;
	}

	public void setTest(Test test) {
		this.test = test;
	}
}
