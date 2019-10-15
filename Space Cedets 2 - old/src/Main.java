import java.io.IOException;

public class Main 
{

	public static void main(String[] arg) throws IOException
	{
		Interpreter interp = new Interpreter("C:\\Users\\Kit\\Documents\\t.txt");
		
		System.out.println(interp.vh.readVar("Z"));
	}
	
}
