package Lab1.Core.Presentation;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.knowm.xchart.*;

import Lab1.Core.*;
import Lab1.Core.Student.*;

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
	
	public CategoryChart generateQuestionMarkRateHistogram() {
		Test test = getTest();
		List<Question> allQuestions = test.getQuestions();
		List<StudentCard> allStudents = test.getStudents();

		// Create Chart
	    CategoryChart chart = new CategoryChartBuilder()
	    		.width(1400)
	    		.height(600)
	    		.title(String.format("Test_%d: %s Question Pass Rate Histogram", test.getTestId(), test.getTestName()))
	    		.xAxisTitle("Question Id")
	    		.yAxisTitle("Answers")
	    		.build();
	    
	    // Customize Chart
	    chart.getStyler().setOverlapped(true);
	    
	    //Calculate histogram data for correct answers
	    List<Integer> correctAnswersData = new ArrayList<>();
	    List<Integer> incorrectAnswersData = new ArrayList<>();
    	for(StudentCard s: allStudents) {
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
	
	public CategoryChart generateStudentMarkRateHistogram(Supplier<List<MarkRange>> marksRangeAquire) {
		
		CategoryChart chart = new CategoryChartBuilder()
				.width(1400)
	    		.height(600)
	    		.title(String.format("Test_%d: %s Student Mark Rate Histogram", test.getTestId(), test.getTestName()))
	    		.xAxisTitle("Mark")
	    		.yAxisTitle("Quantity")
	    		.build();
		
		Test t = getTest();
		
		
		List<Double> data = t.getStudents().stream()
				.mapToDouble(s -> t.calculatePointsScoredForStudent(s))
				.boxed()
				.collect(Collectors.toList());
		int bins = marksRangeAquire.get().size();
		
		Histogram histogram = new Histogram(data, bins);
		
		List<String> xData = marksRangeAquire.get().stream()
				.map(x -> String.format("%.0f%%-%.0f%% %s", x.getFrom() * 100, x.getTo()* 100, x.getMark()))
				.collect(Collectors.toList());
		chart.addSeries("Students", xData, histogram.getyAxisData());
		
		return chart;
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
