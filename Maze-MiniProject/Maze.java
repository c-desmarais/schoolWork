import java.util.*;

// Classe a remplir pour realiser le TP en utilisant les classes fournies

public class Maze{

	public Maze(int width, int height, int seed){
		
		this.width = width;
		this.height = height;

		// Initialisation du labyrinthe avec tous les murs
		walls = new ArrayList<Wall>();
		for(int i = 0; i < height; ++i){
			for(int j = 0; j < width; ++j){
				if(i > 0){
					walls.add(new Wall(j+i*height, j+(i-1)*height));
				}
				if(j > 0){
					walls.add(new Wall(j+i*height, j-1+i*height));
				}
			}
		}

		// Creation du graphe modelisant le labyrinthe
		// Le graphe est decrit par une liste d'adjascence
		mazeGraph = new MazeGraph(height, width);
		
		// On permute les murs de maniere aleatoire
		generator = new Random(seed);
		for(int i = 0; i < walls.size(); ++i){
			// nombre aleatoire ne depassant pas maze.size()-1
            int j = generator.nextInt(walls.size());
            
            //on altere le maze a l'index aleatoire avec celui de l'index du for
            if( i != j ){
                Wall tempWall = walls.get(j);
                walls.set(j, walls.get(i) );
                walls.set(i, tempWall);	
            }
		}
		
		// Initialisation des structures annexes
		ds = new DisjointSet(width*height);
		path = new ArrayList<Integer>();
	}

	public void generate(){
		// EXERCICE 1
		for(int i = 0; i < walls.size(); ++i){
			//Si un mur sépare deux pièces non connectées
			if(!ds.areInSameSet(walls.get(i).room1, walls.get(i).room2)){
				//detruire le mur 
				ds.union(walls.get(i).room1, walls.get(i).room2);
				mazeGraph.connect(walls.get(i).room1, walls.get(i).room2);
				//enlever les murs de la liste walls
				walls.remove(walls.get(i--));
			}
		}
		
	}

	public void solve(){
		// verifie si il y a un chemin possible
        if(!ds.areInSameSet(0, height*width-1)){
            return;
        }
        
        // Creer une file et ajouter le debut
        Queue<Room> q = new LinkedList<Room>();
        Room room = mazeGraph.getStart();
        room.distance = 0;
        q.add(room);
       
        
        
		// EXERCICE 2
        while(!q.isEmpty()){
	        Room r= q.poll(); 
		        for(Room x : r.neighboors){ //pour tous les voisins du room x 
		        	if(x.source == null){ // si la room na pas ete visitee
		        		q.add(x); //ajouter a la liste de chambre
		        		x.source = r; 
		        		x.distance = r.distance + 1;		
		        	}
		    }
        }
        
        Room currentRoom = mazeGraph.getFinish(); 
        path.add(currentRoom.id);        
       
        while(currentRoom != mazeGraph.getStart()){ //parcourir les room de la fin au debut du labyrinthe
        	currentRoom = currentRoom.source;
        	path.add(currentRoom.id); 	
        	
        }
      
	}

	public class Wall{
		
		public Wall(int r1, int r2){
			room1 = r1;
			room2 = r2;
		}

		public int room1;
		public int room2;
	}
	
	public class MazeGraph{
		
		public MazeGraph(int height, int width){
			adjacencyList = new ArrayList<Room>();
			for(int i = 0; i < height*width; ++i)
				adjacencyList.add(new Room(i));
			nbRooms = height*width;
		}
		public int nbRooms;
		ArrayList<Room> adjacencyList;
		
		public void connect(int room1, int room2){
			if(room1 <0 || room1 >=nbRooms) return;
			if(room2 <0 || room2 >=nbRooms) return;
			if(room1  == room2) return;
			Room r1 = adjacencyList.get(room1);
			Room r2 = adjacencyList.get(room2);
			r1.addNeighboor(r2);
			r2.addNeighboor(r1);
		}
		public Room getStart(){
			if(nbRooms == 0) return null;
			return adjacencyList.get(0);
		}
		public Room getFinish(){
			if(nbRooms == 0) return null;
			return adjacencyList.get(adjacencyList.size()-1);
		}
	}

	public class Room{
		
		public Room(int i){
			id = i;
			distance = -1;
			source = null;
			neighboors = new ArrayList<Room>();
		}

		public Object neighboors(Room v) {
			// TODO Auto-generated method stub
			return null;
		}

		public int id;
		ArrayList<Room> neighboors;
		int distance;
		Room source;
		
		public void addNeighboor(Room room){
			if( !isNeighboor(room) )
				neighboors.add(room);
		}
		public boolean isNeighboor(Room room){
			return neighboors.contains(room);
		}

	}

	public ArrayList<Wall> walls;
	public MazeGraph mazeGraph;
	public ArrayList<Integer> path;

	public int height;
	public int width;
	public Random generator;
	public DisjointSet ds;
}

