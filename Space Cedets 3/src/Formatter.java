import java.util.List;

public final class Formatter 
{
	public static int formatCodeFile(List<String> lines)
	{
		for(int i = 0; i < lines.size(); i++)
		{
			if (lines.get(i).contains("//"))
			{
				lines.set(i, lines.get(i).substring(0, lines.get(i).indexOf("//")));
			}
			
			lines.set(i, lines.get(i).trim());
			
			if (lines.get(i).equals(""))
			{
				lines.remove(i);
				continue;
			}
			
			if (!lines.get(i).endsWith(";"))
			{
				continue;
			}
				
			lines.set(i, lines.get(i).substring(0, lines.get(i).length() - 1));
		}
		
		return 0;
	}
	
	public static String removeBraces(String line)
	{
		return line.substring(line.indexOf('(') + 1, line.indexOf(')'));
	}

}
