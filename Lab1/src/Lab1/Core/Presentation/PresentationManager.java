package Lab1.Core.Presentation;

import java.util.List;

import Lab1.Core.Question;
import Lab1.Core.Test;
import Lab1.Core.Student.StudentCard;
import Lab1.Core.Student.StudentQuestionAnswer;

public class PresentationManager {
	PresentationResult testResult = null;

	public PresentationResult calculateForTest(Test test) {
		if(test.getStudents().size() == 0)
			return null;
		
		PresentationResult r = new PresentationResult(test);
		
		//Question statistics
		for(Question q : test.getQuestions()) {
			r.getQuestionStatistics().add(evaluateQuestion(q.getId(), test));
		}
		
		return testResult = r;
	}
	
	public boolean canGenerateHistogram() {
		
		return getTestResult() != null && getTestResult().getTest() != null;
	}

	public PresentationResult getTestResult() {
		return testResult;
	}

	private QuestionStatistic evaluateQuestion(int questionId, Test test) {
		
		int correct = 0;
		int incorrect = 0;
		
		for(StudentCard sa: test.getStudents()) {
			
			List<StudentQuestionAnswer> saForQuestions = sa.getAnswersForQuestion(questionId);
			for(StudentQuestionAnswer a: saForQuestions) {
				
				if(test.isQuestionAnswerCorrect(a))
					correct++;
				else
					incorrect++;
			}
		}
		
		QuestionStatistic stat = new QuestionStatistic();
		stat.setTestId(test.getTestId());
		stat.setQuestionId(questionId);
		stat.setStudentsAnsweredCorrectly(correct);
		stat.setStudentsAnsweredIncorrectly(incorrect);
		
		return stat;
	}

}
