package Lab1.Core;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Answer {

	@SerializedName("option")
	@Expose
	private String option;
	@SerializedName("is_correct")
	@Expose
	private Boolean isCorrect;
	
	public String getOption() {
		return option;
	}
	
	public void setOption(String option) {
		this.option = option;
	}
	
	public Boolean getIsCorrect() {
		return isCorrect;
	}
	
	public void setIsCorrect(Boolean isCorrect) {
		this.isCorrect = isCorrect;
	}

}