package Lab1.Core;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import Lab1.Core.Student.StudentQuestionAnswer;


/**
 * @author User
 * Question of test that contians all avalible answers
 */
public class Question {

	@SerializedName("id")
	@Expose
	private Integer id;
	@SerializedName("question")
	@Expose
	private String question;
	@SerializedName("answers")
	@Expose
	private List<Answer> answers = null;
	
	public boolean isStudentQuestionAnswerCorrect(StudentQuestionAnswer sqa) {
		
		boolean isCorrect = answers
								.stream()
								.anyMatch(a -> 
									a.getOption().equals(sqa.getOption()) &&
									a.getIsCorrect()
								);
		return isCorrect;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}
	
}