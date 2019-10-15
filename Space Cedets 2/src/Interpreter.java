import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Interpreter 
{
	private List<String> lines = new ArrayList<String>();
	public HashMap<String, Integer> vh = new HashMap<String, Integer>();

	Interpreter(String fileDir) throws IOException
	{
		FileIO file = new FileIO(fileDir);
		file.readLinesInto(lines);
		
		if (this.format() == -1)
		{
			System.out.println("Error - File not formatted correctly.");
			
			return;
		}
		
		this.openBody("", 0);
	}
	
	private int format()
	{
		for(int i = 0; i < lines.size(); i++)
		{
			lines.set(i, lines.get(i).trim());
			
			if (!lines.get(i).endsWith(";"))
			{
				return -1;
			}
			
			lines.set(i, lines.get(i).substring(0, lines.get(i).length() - 1));
		}
		
		return 0;
	}
	
	private int openBody(String whileCondition, int bodyStartIndex) throws IOException
	{
		for(int i = bodyStartIndex; i < lines.size(); i++)
		{
			String[] words = lines.get(i).split(" ");

			if (words[0].equals("clear"))
			{
				vh.put(words[1], 0);
			}
			else if (words[0].equals("incr"))
			{
				vh.replace(words[1], vh.get(words[1]) + 1);
			}
			else if (words[0].equals("decr"))
			{
				vh.replace(words[1], vh.get(words[1]) - 1);
			}
			else if (words[0].equals("while"))
			{
				i = openBody(lines.get(i), i + 1);
			}
			else if (words[0].equals("end"))
			{
				if (whileCondition.isEmpty() && (bodyStartIndex == 0))
				{
					return 0;
				}
				
				String[] condition = whileCondition.split(" ");
				if (vh.get(condition[1]) == Integer.parseInt(condition[3]))
				{
					return i;
				}
				
				i = bodyStartIndex - 1;
			}
		}
		
		return -1;
	}
	
}