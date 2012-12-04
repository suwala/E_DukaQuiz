package suwala.shimizu.quizframe;

import java.io.IOException;

public interface Loading {
	
	public String readDataBase(String fileName) throws IOException;
	public String readFile(String fileName) throws IOException;
	public String readAssets(String fileName) throws IOException;

}
