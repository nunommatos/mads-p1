import java.io.*;
import java.util.ArrayList;
class Game
{
	static int diamond = 0;
	static ArrayList<ArrayList<String>> map= new ArrayList<ArrayList<String>>();
	static int robotx;
	static int roboty;
	
	public String getMap() {
		return map.toString();
	}

	public int getDiamond() {
		return diamond;
	}

	public static void setDiamond(int diamond) {
		Game.diamond = diamond;
	}

	public static void main(String[] args){
		FileRead();
	}
   
	public static void FileRead()
	{
      try{
		// Open the file that is the first 
		// command line parameter
		FileInputStream fstream = new FileInputStream("example1.map");
		// Get the object of DataInputStream
		DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String strLine;
		//Read File Line By Line
		while ((strLine = br.readLine()) != null) 	{
			// Print the content on the console
			ArrayList<String> temp = new ArrayList<String>();
			for(int a=0; a < strLine.length();a++){
				temp.add(Character.toString(strLine.charAt(a)));
				if(strLine.charAt(a) == 'x'){
					diamond++;
				}
				if(strLine.charAt(a) == 'R'){
					robotx = a+1;
					roboty = map.size()+1;
				}
			}
			map.add(temp);
			System.out.println (map);
			System.out.println ("diamond:"+diamond);
			System.out.println ("Robot:"+robotx+""+roboty);
		}
		//Close the input stream
		in.close();
		}catch (Exception e){//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}
}

