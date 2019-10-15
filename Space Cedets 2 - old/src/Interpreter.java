import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Interpreter {

	private List<String> lines = new ArrayList<String>();
	public VariableHandler vh = new VariableHandler();

	Interpreter(String fileDir) throws IOException
	{
		FileIO file = new FileIO(fileDir);
		file.readLinesInto(lines);

		this.openBody("", 0);
	}
	
	public int openBody(String condition, int bodyStartIndex) throws IOException
	{
		String buffer = new String();
		
		for(int i = bodyStartIndex; i < lines.size(); i++)
		{
			Pattern p = Pattern.compile("\\p{L}");
			Matcher m = p.matcher(lines.get(i));
			if (!m.find())
			{
				System.out.println("Error in code.");
			}
			int startIndex = m.start();
			
			if (lines.get(i).substring(startIndex).equals("end;"))
			{
				buffer = lines.get(i).substring(startIndex);
			}
			else
			{
				buffer = lines.get(i).substring(startIndex, lines.get(i).indexOf(' ', startIndex));
			}

			if (buffer.equals("clear"))
			{
				buffer = lines.get(i).substring(lines.get(i).indexOf(' ', startIndex) + 1, lines.get(i).indexOf(';'));
				
				int b;
				b = vh.addVar(new Variable(buffer, 0));
				
				if (b == -1)
				{
					vh.setVar(buffer, 0);
				}
			}
			else if (buffer.equals("incr"))
			{
				buffer = lines.get(i).substring(lines.get(i).indexOf(' ', startIndex) + 1, lines.get(i).indexOf(';'));
				
				vh.editVar(buffer, (int data) -> { return data + 1; });
			}
			else if (buffer.equals("decr"))
			{
				buffer = lines.get(i).substring(lines.get(i).indexOf(' ', startIndex) + 1, lines.get(i).indexOf(';'));
				
				vh.editVar(buffer, (int data) -> { return data - 1; });
			}
			else if (buffer.equals("while"))
			{
				i = openBody(lines.get(i).substring(lines.get(i).indexOf("while "), lines.get(i).indexOf(" do;")), i + 1);
			}
			else if (buffer.equals("end;"))
			{
				if (condition.isEmpty())
				{
					return -1;
				}
				
				int data = vh.readVar(condition.substring(condition.indexOf("while ") + 6, condition.indexOf(" not")));
				String test = condition.substring(condition.indexOf("not ") + 4);
				if (data == Integer.parseInt(test))
				{
					return i;
				}
				
				i = bodyStartIndex - 1;
			}
		}
		
		return -1;
	}
	
}