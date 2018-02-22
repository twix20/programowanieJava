package Lab1.Core.Presentation;

import java.util.List;

import Lab1.Core.Question;
import Lab1.Core.Test;
import Lab1.Core.Student.Student;
import Lab1.Core.Student.StudentQuestionAnswer;

public class PresentationManager {
	PresentationResult testResult = null;
	
	public PresentationResult calculateForTest(Test test) {
		if(test.getStudentAnswers().size() == 0)
			return null;
		
		PresentationResult r = new PresentationResult(test);
		
		//Student statistics
		for(Student sa: test.getStudentAnswers()) {
			r.getStudentStatistics().add(evaluetaStudentAnswer(sa, test));
		}
		
		//Question statistics
		for(Question q : test.getQuestions()) {
			r.getQuestionStatistics().add(evaluateQuestion(q.getId(), test));
		}
		
		return testResult = r;
	}
	
	public PresentationResult getTestResult() {
		return testResult;
	}

	public void setTestResult(PresentationResult testResult) {
		this.testResult = testResult;
	}
	
	private StudentAnswerStatistic evaluetaStudentAnswer(Student student, Test test) {
		StudentAnswerStatistic stat = new StudentAnswerStatistic(student);
		
		for(StudentQuestionAnswer sqa: student.getAnswers()) {
			
			int questionId = sqa.getQuestionId();
			boolean isCorrect = test.isQuestionAnswerCorrect(sqa);
			
			
			stat.getAnswers().put(questionId, isCorrect);
		}
		
		return stat;
	}

	private QuestionStatistic evaluateQuestion(int questionId, Test test) {
		
		int correct = 0;
		int incorrect = 0;
		
		for(Student sa: test.getStudentAnswers()) {
			
			List<StudentQuestionAnswer> saForQuestions = sa.getAnswersForQuestion(questionId);
			for(StudentQuestionAnswer a: saForQuestions) {
				
				boolean isCorrect = test.isQuestionAnswerCorrect(a);
				if(isCorrect)
					correct++;
				else
					incorrect++;
			}
		}
		
		QuestionStatistic stat = new QuestionStatistic();
		stat.setQuestionId(questionId);
		stat.setStudentsAnsweredCorrectly(correct);
		stat.setStudentsAnsweredIncorrectly(incorrect);
		
		return stat;
	}
}
