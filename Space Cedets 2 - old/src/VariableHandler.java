import java.util.Vector;

interface Function
{
	int operation(int data);
}

public class VariableHandler
{
	private Vector<Variable> vars = new Vector<Variable>();
	
	public int addVar(Variable var)
	{
		if (this.readVar(var.name) == 0)
		{
			return -1;
		}
		
		vars.add(var);
		
		return 0;
	}
	
	public int editVar(String name, Function func)
	{
		for (int i = 0; i < vars.size(); i++)
		{
			if (vars.elementAt(i).name.equals(name))
			{		
				vars.elementAt(i).data = func.operation(vars.elementAt(i).data);
				
				return 0;
			}
		}
		
		return -1;
	}
	
	public int setVar(String name, int data)
	{
		for (int i = 0; i < vars.size(); i++)
		{
			if (vars.elementAt(i).name.equals(name))
			{
				vars.elementAt(i).data = data;
				
				return 0;
			}
		}
		
		return -1;
	}
	
	public int readVar(String name)
	{
		for (int i = 0; i < vars.size(); i++)
		{
			if (vars.elementAt(i).name.equals(name))
			{
				return vars.elementAt(i).data;
			}
		}
		
		return -1;
	}
}
