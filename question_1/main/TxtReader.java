package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TxtReader
{
	public static void main(String [] args) throws IOException{
		try
		{		
			File f=new File("E:/workspace/JavaCourse/test.txt");
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			
			Map<String, Integer> WordMap = new HashMap();
						
			String str = new String();
			int tempNum = 0;
			int tempCount = 0;
			str = br.readLine();
			while(str != null){
				str = str.replaceAll(" ", "");
				String[] splitStr = str.split(",");
				tempNum = Integer.parseInt(splitStr[1]);
				str = br.readLine();
				if(WordMap.containsKey(splitStr[0])){
					tempCount = WordMap.get(splitStr[0]);
					tempCount += tempNum;
					WordMap.put(splitStr[0], tempCount);
				}
				else{
					WordMap.put(splitStr[0], tempNum);
				}
			}
			for(String key : WordMap.keySet()){
				System.out.println(key+", " + WordMap.get(key));				
			}
		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
