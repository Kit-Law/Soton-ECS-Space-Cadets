import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Interpreter 
{
	private List<String> lines = new ArrayList<String>();
	
	public HashMap<String, Integer> vh = new HashMap<String, Integer>();
	public HashMap<String, Function> fh = new HashMap<String, Function>();
	
	Interpreter(String fileDir) throws IOException
	{
		FileIO file = new FileIO(fileDir);
		file.readLinesInto(lines);
		
		if (Formatter.formatCodeFile(lines, fh) == -1)
		{
			System.out.println("Error - File not formatted correctly.");
			
			return;
		}
		
		this.openBody("", 0);
	}
	
	private Integer getData(String input)
	{
		if (vh.containsKey(input))
		{
			return vh.get(input);
		}
		
		return Integer.parseInt(input);
	}
	
	private int skipBody(int lineIndex, String openKey, String closeKey, String searchKey)
	{
		int blocks = 1;
		
		while (blocks > 0)							//Skips if bodes'
		{
			lineIndex++;
			
			if (lines.get(lineIndex).startsWith(closeKey))
			{
				blocks--;
			}
			else if (lines.get(lineIndex).startsWith(openKey))
			{
				blocks++;
			}
			else if ((lines.get(lineIndex).startsWith(searchKey)) && (blocks == 1))
			{
				return lineIndex;
			}
		}
		
		return lineIndex;
	}
	
	private boolean interpretCondition(String condition)
	{
		String[] words = condition.split(" ");
		
		switch (words[1])
		{
			case "==" :
				return (getData(words[0]) == getData(words[2]));
			case "!=" :
				return (getData(words[0]) != getData(words[2]));
			case "<=" :
				return (getData(words[0]) <= getData(words[2]));
			case ">=" :
				return (getData(words[0]) >= getData(words[2]));
			case "<" :
				return (getData(words[0]) <= getData(words[2]));
			case ">" :
				return (getData(words[0]) >= getData(words[2]));
		}
		
		return false;
	}
	
	private int openBody(String whileCondition, int bodyStartIndex) throws IOException
	{
		for(int i = bodyStartIndex; i < lines.size(); i++)
		{
			String[] words = lines.get(i).split(" ");

			if (words[0].equals("int"))
			{
				if ((words.length == 4) && (words[2].equals("=")))
				{
					vh.put(words[1], Integer.parseInt(words[3]));
				}
				else
				{
					vh.put(words[1], 0);					
				}		
			}
			else if (words[0].contains("if"))
			{
				if (!interpretCondition(Formatter.removeBraces(lines.get(i))))
				{
					i = skipBody(i, "if", "endIf", "else");
				}
			}
			else if (words[0].contains("else"))
			{
				i = skipBody(i, "if", "endIf", "");
			}
			else if (vh.containsKey(words[0]))
			{
				if ((words.length == 3) && words[1].equals("="))
				{
					vh.put(words[0], Integer.parseInt(words[2]));
				}
				else if ((words.length == 5) && words[1].equals("="))
				{
					Integer LHS = getData(words[2]);;
					Integer RHS = getData(words[4]);;

					if (words[3].equals("+"))
					{
						vh.put(words[0], LHS + RHS);
					}
					else if (words[3].equals("-"))
					{
						vh.put(words[0], LHS - RHS);
					}
					else if (words[3].equals("*"))
					{
						vh.put(words[0], LHS *= RHS);
					}
					else if (words[3].equals("/"))
					{
						vh.put(words[0], LHS / RHS);
					}
					else if (words[3].equals("%"))
					{
						vh.put(words[0], LHS % RHS);
					}
				}
				else
				{					
					if (words[1].equals("+="))
					{
						vh.put(words[0], vh.get(words[0]) + getData(words[2]));
					}
					else if (words[1].equals("-="))
					{
						vh.put(words[0], vh.get(words[0]) - getData(words[2]));
					}
					else if (words[1].equals("*="))
					{
						vh.put(words[0], vh.get(words[0]) * getData(words[2]));
					}
					else if (words[1].equals("/="))
					{
						vh.put(words[0], vh.get(words[0]) / getData(words[2]));
					}
					else if (words[1].equals("%="))
					{
						vh.put(words[0], vh.get(words[0]) % getData(words[2]));
					}
				}
			}
			else if (words[0].equals("while"))
			{
				String condition = Formatter.removeBraces(lines.get(i));
				
				if (interpretCondition(condition))
				{
					i = skipBody(i, "while", "endWhile", "");
				}
				
				i = openBody(condition, i + 1);
			}
			else if (words[0].equals("endWhile"))									//change this to endwhile and use the interpretCondition methord
			{
				if (whileCondition.isEmpty() && (bodyStartIndex == 0))
				{
					return 0;
				}
				
				if (interpretCondition(whileCondition))
				{
					return i;
				}
				
				i = bodyStartIndex - 1;
			}
		}
		
		return -1;
	}
	
}