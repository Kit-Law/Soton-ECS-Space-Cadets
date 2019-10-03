/* Program: SotonNameFinder
 * 
 * 		This program is designed so that a user can enter the email ID for someone
 * at soton and then it will output the corresponding name associated with that ID.
 * 
 * 	Author: Christopher (Kit) Lawrence
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class SotonNameFinder
{
	public static void main(String[] arg) throws IOException
	{
		System.out.print("Enter Email ID: ");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));	//Retrieves the ID from the user
		
		URL soruce = new URL("https://www.ecs.soton.ac.uk/people/" + br.readLine());	//Creates the by concatenating the user input and the main web page
		br = new BufferedReader(new InputStreamReader(soruce.openStream(), "UTF-8"));

		for (int i = 0; i < 7; i++)							//Discards the useless 7 leading lines of HTML
		{
			br.readLine();
		}
			
		String nameBuffer = br.readLine();						//Reads the useful line of HTML into a String
		String name = nameBuffer.substring(11, (nameBuffer.indexOf('|') - 1));		//Extracts the useful data (name), that is located at the 11th char
		
		if (name.equals("People"))							//If the user input is invalid then the source URL is linking to "https://www.ecs.soton.ac.uk/people/" and name will == "people"
		{
			System.out.print("That Was Not A Valid ID");
		}
		else										//Else the user's input was valid and the appropriate name will be output
		{			
			System.out.print("This ID Belongs To: " + name);
		}
		
		br.close();
	}
}
