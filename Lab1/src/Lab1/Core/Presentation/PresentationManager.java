package Lab1.Core.Presentation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.Histogram;
import org.knowm.xchart.style.Styler.LegendPosition;

import Lab1.Core.Question;
import Lab1.Core.Test;
import Lab1.Core.Student.Student;
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

	public CategoryChart generateHistogram() {
		Test test = this.getTestResult().getTest();
		List<Question> allQuestions = test.getQuestions();
		List<Student> allStudents = test.getStudents();
		
		// Create Chart
	    CategoryChart chart = new CategoryChartBuilder()
	    		.width(800)
	    		.height(600)
	    		.title(String.format("Test_%d: %s histogram", test.getTestId(), test.getTestName()))
	    		.xAxisTitle("Question Id")
	    		.yAxisTitle("Answers")
	    		.build();
	    
	    // Customize Chart
	    chart.getStyler().setLegendPosition(LegendPosition.InsideNW);
	    chart.getStyler().setAvailableSpaceFill(.96);
	    chart.getStyler().setOverlapped(true);
	    
	    //Calculate histogram data for correct answers
	    List<Integer> correctAnswersData = new ArrayList<>();
	    List<Integer> incorrectAnswersData = new ArrayList<>();
    	for(Student s: allStudents) {
    		for(StudentQuestionAnswer sqa: s.getAnswers()) {
    			int questionId = sqa.getQuestionId();
    			
    			if(test.isQuestionAnswerCorrect(sqa))
    				correctAnswersData.add(questionId);
    			else
    				incorrectAnswersData.add(questionId);
    		}
    	}
	    	
	    int numBins = allQuestions.size();
	    List<Integer> questionIds = test
	    		.getQuestions().stream().map(x->x.getId())
	    		.collect(Collectors.toList());
	    
		Histogram histogramCorrect = new Histogram(correctAnswersData, numBins);
	    Histogram histogramIncorrect = new Histogram(incorrectAnswersData, numBins);
	    
	    chart.addSeries("Correct Answers", questionIds, histogramCorrect.getyAxisData());
	    chart.addSeries("Incorrect Answers", questionIds, histogramIncorrect.getyAxisData());
	    
	    return chart;
	}
	public PresentationResult getTestResult() {
		return testResult;
	}

	private QuestionStatistic evaluateQuestion(int questionId, Test test) {
		
		int correct = 0;
		int incorrect = 0;
		
		for(Student sa: test.getStudents()) {
			
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
