package reflection;

import java.util.*;

import loader.FileSystemClassLoader;

import java.io.File;
import java.lang.reflect.*;

/**
 * This program uses reflection to print all features of a class.
 * 
 * @version 1.1 2004-02-21
 * @author Cay Horstmann
 */
public class ReflectionTest
{
	private static Map<String, Integer> classesMap = new HashMap();
	private static String rootPath = "";

	public static void main(String[] args)
	{
		// read class name from command line args or user input
		String name = "";
		if (args.length > 0)
			name = args[0];
		else
		{
			Scanner in = new Scanner(System.in);
			System.out.println("Enter file path (e.g. C:/Users/I334601/Desktop/bin)");
			name = in.next();
			rootPath = name;
		}
		ReadAllFile(rootPath);
		traverseAll(name);
		printResults();
		System.exit(0);
	}

	public static void ReadAllFile(String filePath)
	{
		File f = null;
		f = new File(filePath);
		File[] files = f.listFiles();
		List<File> list = new ArrayList<File>();

		for (File file : files)
		{
			if (file.isDirectory())
			{
				ReadAllFile(file.getAbsolutePath());
			}
			else
			{
				if (file.getPath().substring(file.getPath().length() - 6).equals(".class"))
				{
					list.add(file);
				}
			}
		}
		for (int i = 0; i < list.size(); i++)
		{
			classesMap.put(list.get(i).toString(), 0);
		}
	}

	public static void traverseAll(String name)
	{
		// print class name and superclass name (if != Object)
		try
		{
			for (String key : classesMap.keySet())
			{
				FileSystemClassLoader fscl = new FileSystemClassLoader(name);
				String className = key.substring(name.length() + 1, key.length() - 6);
				className = className.replace(File.separatorChar, '.');
				// System.out.println("!!!" + className);
				Class cl = fscl.loadClass(className);
				Class supercl = cl.getSuperclass();
				if (supercl != null && supercl != Object.class)
					detectItem(supercl.getName());
				detectConstructors(cl);
				detectMethods(cl);
				detectFields(cl);
			}
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	public static void detectItem(String name)
	{
		name = rootPath.replace('/', '\\') + "\\" + name.replace('.', File.separatorChar) + ".class";
		if (classesMap.containsKey(name) == true)
		{
			Integer cCount = classesMap.get(name);
			cCount++;
			classesMap.put(name, cCount);
		}
	}

	/**
	 * Prints all constructors of a class
	 * 
	 * @param cl
	 *            a class
	 */
	public static void detectConstructors(Class cl)
	{
		Constructor[] constructors = cl.getDeclaredConstructors();

		for (Constructor c : constructors)
		{
			// print parameter types
			Class[] paramTypes = c.getParameterTypes();
			for (int j = 0; j < paramTypes.length; j++)
			{
				detectItem(paramTypes[j].getName());
			}
		}
	}

	/**
	 * Prints all methods of a class
	 * 
	 * @param cl
	 *            a class
	 */
	public static void detectMethods(Class cl)
	{
		Method[] methods = cl.getDeclaredMethods();

		for (Method m : methods)
		{
			Class retType = m.getReturnType();

			// print parameter types
			Class[] paramTypes = m.getParameterTypes();
			for (int j = 0; j < paramTypes.length; j++)
			{
				detectItem(paramTypes[j].getName());
			}
		}
	}

	/**
	 * Prints all fields of a class
	 * 
	 * @param cl
	 *            a class
	 */
	public static void detectFields(Class cl)
	{
		Field[] fields = cl.getDeclaredFields();

		for (Field f : fields)
		{
			Class type = f.getType();
			detectItem(type.getName());
		}
	}

	public static void printResults()
	{
		for (String key : classesMap.keySet())
		{
			Integer value = classesMap.get(key);
			System.out.println(key + "   " + value);
		}
	}
}