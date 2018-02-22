package Lab1.Core;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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