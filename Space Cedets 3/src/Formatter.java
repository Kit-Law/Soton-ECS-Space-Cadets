import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class Formatter 
{
	public static int formatCodeFile(List<String> lines, HashMap<String, Function> fh)
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
			
			if (lines.get(i).startsWith("function"))
			{
				String funcName = lines.get(i).substring(lines.get(i).indexOf("function "), lines.get(i).indexOf('('));
				HashMap<String, Integer> fvh = new HashMap<String, Integer>();
				List<String> body = new ArrayList<String>();
				
				String[] parameters = lines.get(i).substring(lines.get(i).indexOf('('), lines.get(i).indexOf(')')).split(" ");
				
				for (int j = 1; j < parameters.length; j++)
				{
					if (parameters[j].contains("int"))
					{
						j++;
						fvh.put(parameters[j], 0);
					}
				}
				
				while (!lines.get(i).startsWith("endFunction"))
				{
					i++;
					body.add(lines.get(i));
				}
				
				fh.put(funcName, new Function(funcName, fvh, body));
			}
		}
		
		return 0;
	}
	
	public static String removeBraces(String line)
	{
		return line.substring(line.indexOf('(') + 1, line.indexOf(')'));
	}

}
