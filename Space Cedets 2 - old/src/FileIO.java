import java.io.File;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileIO
{
	private File sourceFile = null;
	public BufferedReader in = null;
	public BufferedWriter out = null;
	
	FileIO(String dir)
	{
		sourceFile = new File(dir);
		
		try
		{
			in = new BufferedReader(new FileReader(sourceFile));
		}
		catch(Exception Found)
		{
			System.out.println("---File not found (read)---");
			
		}
		
		try
		{
			out = new BufferedWriter(new FileWriter(sourceFile, true));
		}
		catch(Exception Found)
		{
			System.out.println("---File not found (write)---");
		}
	}
	
	public void readLinesInto(List<String> lines) throws IOException
	{
		String buffer;
		
		while ((buffer = in.readLine()) != null)
		{
			lines.add(buffer);
		}
		
		return;
	}
}
