package Lab1.Core;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import Lab1.Core.Student.StudentCard;
import Lab1.Core.Student.StudentQuestionAnswer;
import au.com.bytecode.opencsv.CSVReader;

public class TestManager {
	private List<Test> tests = new ArrayList<>();
	
	public void loadTestFromFile(String filePath) {
		
		Gson gson = new Gson();
		try {
			JsonReader reader = new JsonReader(new FileReader(filePath));
			Test test = gson.fromJson(reader, Test.class); // contains the whole reviews list
			tests.add(test);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void loadAnswersFromFile(int testId, String filePath) {
		// TODO Auto-generated method stub
		Test test= getTestById(testId);
		if(test == null) return;
		
		List<StudentCard> studentAnswers = new ArrayList<>();
		try {
			CSVReader csvReader = new CSVReader(new FileReader(filePath));
			
			csvReader.readNext(); //Ignore header
			
			String [] nextLine;
			while ((nextLine = csvReader.readNext()) != null) {
			   // nextLine[] is an array of values from the line
				StudentCard sa = new StudentCard();
				
				sa.setStudentId(Integer.parseInt(nextLine[0]));
				sa.setFirstName(nextLine[1]);
				sa.setLastName(nextLine[2]);
				
				for(int i = 3; i < nextLine.length; i++) {
					int questionId = i - 2;
					String option = nextLine[i];
					
					sa.getAnswers().add(new StudentQuestionAnswer(sa, questionId, testId, option));
				}
				
				studentAnswers.add(sa);
			}
			
			test.setStudents(studentAnswers);
			
			
			csvReader.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			
		}
		
	}
		
	public Test getTestById(int testId) {
		Test[] tests = this.tests.toArray((new Test[this.tests.size()]));
		
		return Arrays.stream(tests)
				.filter(x -> x.getTestId() == testId)
				.findFirst()
				.get();
	}
	
	public List<Test> getTests(){
		return this.tests;
	}
}
