package reflection;

import java.util.*;
import java.lang.Thread;
import java.lang.reflect.*;

/**
 * This program is the answer code of question_10
 * 
 * @version 2016-12-06
 * @author Jimmy.Zhu
 */
public class Main
{
	public static void main(String[] args)
	{
		//Set the class name and the method name, here we invoke A.f1
		String classname = "reflection.A";
		String methodname = "f1";
		try{
			Class cl = Class.forName(classname);
			Method m = cl.getMethod(methodname);
			//Invoke cl.method 3 times , invoke once per 5 seconds
			InvokeMethod(5,3,m,cl);
		}catch(Exception e){
			e.printStackTrace();
		}
		System.exit(0);
	}
	/*
	 * @param internal seconds between two operation
	 * @param count the frequency invoke method m
	 * @param m the method invoking
	 * @param cl the class of method m
	 */
	public static void InvokeMethod(int internal,int count,Method m,Class cl){
		try
		{
			for(int i=0;i<count;i++){
				m.invoke(cl.newInstance());
				Thread t = new Thread();
				try
				{
					t.sleep(internal*1000);
				}
				catch (InterruptedException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		catch (IllegalAccessException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IllegalArgumentException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (InstantiationException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}