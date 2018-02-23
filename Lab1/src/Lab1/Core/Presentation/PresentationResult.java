package Lab1.Core.Presentation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import Lab1.Core.Test;

public class PresentationResult {
	private Test test;
	
	private List<QuestionStatistic> questionStatistics = new ArrayList<>();
	
	public PresentationResult(Test test) {
		this.setTest(test);
	}
	
	public QuestionStatistic getEasiestQuestion() {
		return Collections.max(this.questionStatistics, new Comparator<QuestionStatistic>() {
			@Override
		    public int compare(QuestionStatistic first, QuestionStatistic second) {
				return first.getStudentsAnsweredCorrectly() >= second.getStudentsAnsweredCorrectly() ? 1 : -1;
			}
		});
	}
	
	public QuestionStatistic getHardesQuestion() {
		return Collections.max(this.questionStatistics, new Comparator<QuestionStatistic>() {
			@Override
		    public int compare(QuestionStatistic first, QuestionStatistic second) {
				return first.getStudentsAnsweredIncorrectly() >= second.getStudentsAnsweredIncorrectly() ? 1 : -1;
			}
		});
	}

	public List<QuestionStatistic> getQuestionStatistics() {
		return questionStatistics;
	}

	public void setQuestionStatistics(List<QuestionStatistic> questionStatistics) {
		this.questionStatistics = questionStatistics;
	}

	public Test getTest() {
		return test;
	}

	public void setTest(Test test) {
		this.test = test;
	}
}
