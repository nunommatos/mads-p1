import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
class Game
{
	static int diamond = 0;
	static ArrayList<ArrayList<String>> map= new ArrayList<ArrayList<String>>();
	static ArrayList<ArrayList<ArrayList<String>>> video = new ArrayList<ArrayList<ArrayList<String>>>();
	static ArrayList<Boolean> valids = new ArrayList<Boolean>();
	static ArrayList<Integer> rockPosition= new ArrayList<Integer>();
	static ArrayList<Rock> rocks = new ArrayList<Rock>();
	static ArrayList<ArrayList<Integer>> diamonds = new ArrayList<ArrayList<Integer>>();
	static int ind = 0;
	static int finish = 0;
	static int robotx;
	static int roboty;
	static int exitx;
	static int exity;
	static int indexVideo=0;
	static boolean endgame = false;
	
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
		addVideo();
		System.out.print("video: "+video);
		menu();
	}
   
	public static void addVideo(){
		video.add(new ArrayList<ArrayList<String>>());
		for( int i=0;i<map.size();i++){
			video.get(indexVideo).add(new ArrayList<String>());
			for(int k=0;k<map.get(i).size();k++){
				video.get(indexVideo).get(i).add(new String(map.get(i).get(k)));
			}
		}
	}
	
	public static void changemap(ArrayList<ArrayList<String>> newmap){
		for( int i=0;i<map.size();i++){
			for (int k = 0; k<map.get(i).size();k++)
				map.get(i).set(k, new String(newmap.get(i).get(k)));
		}
	}
	
	public static void getRobotPosition(ArrayList<ArrayList<String>> frame){
		for(int i=0;i<frame.size();i++)
			for(int k=0;k<frame.get(i).size();k++)
				if(frame.get(i).get(k).equals("R")){
					robotx=k;
					roboty=i;
				}
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
					ArrayList<Integer> tem = new ArrayList<Integer>();
					tem.add(a);
					tem.add(map.size());
					diamonds.add(tem);
				}
				if(strLine.charAt(a) == 'R'){
					robotx = a;
					roboty = map.size();
				}
				if(strLine.charAt(a) == 'L'){
					exitx = a;
					exity = map.size();
				}
				if(strLine.charAt(a) == '*')
				{
					Rock rock = new Rock(a,map.size(),false);
					rocks.add(rock);
				}
			}
			map.add(temp);
		}
		//Close the input stream
		in.close();
		}catch (Exception e){//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}
	
	public static void move(int x, int y){
		if(x<0 || y<0 || x>map.get(0).size() || y>map.size()){
			System.out.println("noup");
		}
		else{
			if(map.get(y).get(x).equals("x")){
				diamond--;
				map.get(roboty).set(robotx, " ");
				map.get(y).set(x, "R");
				robotx=x;
				roboty=y;
			}
			else if(map.get(y).get(x).equals(" ") || map.get(y).get(x).equals(".")){
				map.get(roboty).set(robotx, " ");
				map.get(y).set(x, "R");
				robotx=x;
				roboty=y;
			}
			else if(map.get(y).get(x).equals("O")){
				map.get(roboty).set(robotx, " ");
				map.get(y).set(x, "R");
				endgame=true;
				robotx=x;
				roboty=y;
			}
			else if(map.get(y).get(x).equals("*") && roboty==y){
				if(robotx>x && map.get(y).get(x-1).equals(" ")){
					map.get(y).set(x-1,"*");
					map.get(y).set(x,"R");
					map.get(y).set(robotx," ");
				}
				else if(robotx<x && map.get(y).get(x+1).equals(" ")){
					map.get(y).set(x+1,"*");
					map.get(y).set(x,"R");
					map.get(y).set(robotx," ");
				}
			}
			
			if(diamond == 0){
				map.get(exity).set(exitx, "O");
			}
			
			if(indexVideo<video.size()-1){
				for(int i = video.size()-1; i > indexVideo;i--)
					video.remove(i);
			}
			
			++indexVideo;
			addVideo();
			
			
			System.out.println("video2: "+video);
			System.out.println("IndexVideo: " + indexVideo);
		}
		
	}
	
	public static void retInd()
	{
		double max = Double.POSITIVE_INFINITY;
		for(int a = 0; a < diamonds.size(); a++)
		{
			double dx = Math.abs(robotx-diamonds.get(a).get(0));
			double dy = Math.abs(roboty-diamonds.get(a).get(1));
			
			if(dx + dy < max)
				ind = a;
		}
	}
	
	public static void menu() {
		Scanner in = new Scanner(System.in);
		// print menu

		System.out.println("L. Move Left:");
		System.out.println("R. Move Right:");
		System.out.println("U. Move Up:");
		System.out.println("D. Move Down:");
		System.out.println("W. Wait:");
		System.out.println("Z. Undo:");
		System.out.println("Y. Redo:");
		System.out.println("P. Pilot:");
		
		// handle user commands
		String menuItem;
		do {
			
			for(int a = 0; a < map.size(); a++)
			{
				for(int b = 0; b < map.get(a).size(); b++)
					System.out.print(map.get(a).get(b));
				System.out.println("");
			}
			
			if(finish<1){
			System.out.print("Option: ");
			menuItem = in.next().toUpperCase();
			switch (menuItem) {
			case "L":
				// nome da funcao
				move(robotx-1,roboty);
				rockFall();
				break;
			case "R":
				// nome da funcao
				move(robotx+1,roboty);
				rockFall();
				break;
			case "U":
				// nome da funcao
				move(robotx,roboty-1);
				rockFall();
				break;
			case "D":
				// nome da funcao
				move(robotx,roboty+1);
				rockFall();
				break;
			case "W":
				// nome da funcao
				move(robotx,roboty);
				rockFall();
				break;
			case "Z":
				if(indexVideo>0){
					--indexVideo;
					System.out.println(indexVideo);
					changemap(video.get(indexVideo));
					getRobotPosition(map);
					System.out.println(map);
				}
				else
					System.out.println("Cannot Undo!");
				break;
			case "Y":
				if(indexVideo < video.size()-1){
					indexVideo++;
					changemap(video.get(indexVideo));
					getRobotPosition(map);
					System.out.println(map);
				}
				else
					System.out.println("Cannot Redo!");
				break;
			case "P":
				automaticPilot();
				break;
			default:
				System.out.println("Invalid choice.");
			}}
			else
				finish++;
		} while (!endgame && finish < 2);
		System.out.println("Congratz");
		in.close();
	}
	
	public static void automaticPilot() {
		
		//faltou tempo
	}

	public static void rockFall()
	{
		for(int n = 0; n < rocks.size(); n++)
		{
			if(map.get(rocks.get(n).y+1).get(rocks.get(n).x).equals(" "))
			{
			//	System.out.println("ooo");
				rocks.get(n).move=true;
			}
		}
		
		for(int n = 0; n < rocks.size();n++)
		{
			if(map.get(rocks.get(n).y+1).get(rocks.get(n).x).equals(" ")|| 
				map.get(rocks.get(n).y+1).get(rocks.get(n).x).equals("R"))
			{
				valids.add(true);				
			}
			else if(map.get(rocks.get(n).y+1).get(rocks.get(n).x).equals("*") || 
					map.get(rocks.get(n).y+1).get(rocks.get(n).x).equals("x"))
			{
				if(map.get(rocks.get(n).y).get(rocks.get(n).x+1).equals(" "))
				{					
					valids.add(true);
				}
				
				else if(map.get(rocks.get(n).y).get(rocks.get(n).x-1).equals(" "))
				{					
					if(map.get(rocks.get(n).y+1).get(rocks.get(n).x-1).equals(" ") || 
							map.get(rocks.get(n).y+1).get(rocks.get(n).x-1).equals("R")	)
					{
						valids.add(true);
					}
				}
				
				else
					valids.add(false);
			}
			else
				valids.add(false);
			
				
		}
		
		
		for(int n = 0,i=0; n < rocks.size(); n++,i++)
		{
			if(map.get(rocks.get(n).y+1).get(rocks.get(n).x).equals(" "))
			{
				map.get(rocks.get(n).y).set(rocks.get(n).x, " ");				
				map.get(rocks.get(n).y+1).set(rocks.get(n).x, "*");
				rocks.get(n).y = rocks.get(n).y+1;
			}
			else if(map.get(rocks.get(n).y+1).get(rocks.get(n).x).equals("R") &&
					rocks.get(n).move)
			{
				finish=1;
				map.get(rocks.get(n).y).set(rocks.get(n).x, " ");				
				map.get(rocks.get(n).y+1).set(rocks.get(n).x, "*");
				rocks.get(n).y = rocks.get(n).y+1;
			}
			else if(rocks.get(n).move && (map.get(rocks.get(n).y+1).get(rocks.get(n).x).equals("x") ||
				map.get(rocks.get(n).y+1).get(rocks.get(n).x).equals("*")))
			{
				if(valids.get(i) && map.get(rocks.get(n).y).get(rocks.get(n).x+1).equals(" "))
				{					
					if(map.get(rocks.get(n).y+1).get(rocks.get(n).x+1).equals(" ")  || 
							map.get(rocks.get(n).y+1).get(rocks.get(n).x+1).equals("*")	)
					{
						if(map.get(rocks.get(n).y+1).get(rocks.get(n).x+1).equals("R"))
							finish = 1;
						map.get(rocks.get(n).y).set(rocks.get(n).x, " ");				
						map.get(rocks.get(n).y+1).set(rocks.get(n).x+1, "*");
						rocks.get(n).x = rocks.get(n).x+1;
						rocks.get(n).y = rocks.get(n).y+1;
						
					}
				}
				
				else if(valids.get(i) && (map.get(rocks.get(n).y).get(rocks.get(n).x-1).equals(" ")))
				{					
					if(valids.get(i) && (map.get(rocks.get(n).y+1).get(rocks.get(n).x-1).equals(" ") || 
						map.get(rocks.get(n).y+1).get(rocks.get(n).x-1).equals("*")))
					{
						if(map.get(rocks.get(n).y+1).get(rocks.get(n).x-1).equals("R"))
							finish = 1;
						map.get(rocks.get(n).y).set(rocks.get(n).x, " ");				
						map.get(rocks.get(n).y+1).set(rocks.get(n).x-1, "*");
						rocks.get(n).x =  rocks.get(n).x-1;
						rocks.get(n).y =  rocks.get(n).y+1;
					}
				}
			}
			else
				rocks.get(n).move = false;				
		}
		valids.clear();
	}
}

