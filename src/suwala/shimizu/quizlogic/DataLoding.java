package suwala.shimizu.quizlogic;

import java.io.IOException;

public interface DataLoding {
	
	public String readStringData(String key);
	public int readIntData(String key);
	public String readStringData(String key,int index);
	public int readIntData(String key,int index) throws IOException;
	public int getRecordCount();
}
