package Lab1.Core.Presentation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;

import Lab1.Core.MarkRange;
import Lab1.Core.Test;
import Lab1.Core.Student.StudentCard;
import Lab1.Core.Student.StudentQuestionAnswer;


/**
 * @author User
 * Result for evaluating test
 */
public class PresentationResult {
	private Test test;
	
	private List<QuestionStatistic> questionStatistics = new ArrayList<>();
	
	public PresentationResult(Test test) {
		this.setTest(test);
	}
	
	/**
	 * @return finds question that was answered correctly the most times
	 */
	public QuestionStatistic getEasiestQuestion() {
		return Collections.max(this.questionStatistics, new Comparator<QuestionStatistic>() {
			@Override
		    public int compare(QuestionStatistic first, QuestionStatistic second) {
				return first.getStudentsAnsweredCorrectly() >= second.getStudentsAnsweredCorrectly() ? 1 : -1;
			}
		});
	}
	
	/**
	 * @return finds question that was answered incorrectly the most times
	 */
	public QuestionStatistic getHardesQuestion() {
		return Collections.max(this.questionStatistics, new Comparator<QuestionStatistic>() {
			@Override
		    public int compare(QuestionStatistic first, QuestionStatistic second) {
				return first.getStudentsAnsweredIncorrectly() >= second.getStudentsAnsweredIncorrectly() ? 1 : -1;
			}
		});
	}
	
	/**
	 * @return generatest histogram of question correct/incorrect times
	 */
	public CategoryChart generateQuestionMarkRateHistogram() {
		Test test = getTest();
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
	    	
	    List<Integer> questionIds = test
	    		.getQuestions()
	    		.stream()
	    		.map(x->x.getId())
	    		.collect(Collectors.toList());
	    
	    PreciseHistogram<Integer> correctAnswersHistogram = new PreciseHistogram<Integer>(correctAnswersData, questionIds);
	    PreciseHistogram<Integer> incorrectAnswersHistogram = new PreciseHistogram<Integer>(incorrectAnswersData, questionIds);

	    chart.addSeries("Correct Answers", correctAnswersHistogram.getXAxis(), correctAnswersHistogram.getYAxis());
	    chart.addSeries("Incorrect Answers", incorrectAnswersHistogram.getXAxis(), incorrectAnswersHistogram.getYAxis());
	    
	    return chart;
	}
	
	/**
	 * @param marksRangeAquire
	 * @return generates histogram for student mark occurences
	 */
	public CategoryChart generateStudentMarkRateHistogram(Supplier<List<MarkRange>> marksRangeAquire) {
		
		CategoryChart chart = new CategoryChartBuilder()
				.width(1400)
	    		.height(600)
	    		.title(String.format("Test_%d: %s Student Mark Rate Histogram", test.getTestId(), test.getTestName()))
	    		.xAxisTitle("Mark")
	    		.yAxisTitle("Quantity")
	    		.build();
		
		Test t = getTest();
		List<Double> studentMarks = t.getStudents().stream()
				.map(s -> {
					int pointsScored = t.calculatePointsScoredForStudent(s);
					return t.calculateMarkByPoints(pointsScored, marksRangeAquire);
				})
				.collect(Collectors.toList());

		List<MarkRange> marksRanges = marksRangeAquire.get();

		List<Double> allPossibleMarks = marksRanges.stream().map(x -> x.getMark()).collect(Collectors.toList());
		
		PreciseHistogram<Double> histogram = new PreciseHistogram<Double>(studentMarks, allPossibleMarks);
		
		List<String> xData = marksRanges.stream()
				.map(x -> String.format("%.0f%%-%.0f%% %s", x.getFrom() * 100, x.getTo()* 100, x.getMark()))
				.collect(Collectors.toList());
		
		chart.addSeries("Students", xData, histogram.getYAxis());
		
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
