package Lab5.GenClassificator.Data;

import java.util.List;
import javax.xml.bind.annotation.*;

import Lab5.GenClassificator.Entities.Examined;;


@XmlRootElement(name="allExamined")
@XmlAccessorType(XmlAccessType.FIELD)
public class ExaminedXmlResult {
	
	@XmlElement(name="examined")
	private List<Examined> allExamined;
	
	public ExaminedXmlResult() { }
	
	public ExaminedXmlResult(List<Examined> allExamined) {
		this.setAllExamined(allExamined);
	}
	

	public List<Examined> getAllExamined() {
		return allExamined;
	}

	public void setAllExamined(List<Examined> allExamined) {
		this.allExamined = allExamined;
	}

}
