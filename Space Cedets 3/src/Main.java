import java.io.IOException;

public class Main 
{
	public static void main(String[] arg) throws IOException
	{
		Interpreter interp = new Interpreter("C:\\Users\\Kit\\Documents\\net.txt");
		
		System.out.println(interp.vh.get("bob"));
		System.out.println(interp.vh.get("jim"));
	}
}
