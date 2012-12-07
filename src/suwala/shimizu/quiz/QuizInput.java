package suwala.shimizu.quiz;

import suwala.shimizu.quizlogic.Input;

public class QuizInput implements Input{

	String answer;
	
	@Override
	public void toAnswer(String answer) {
		this.answer = answer;
	}

}
