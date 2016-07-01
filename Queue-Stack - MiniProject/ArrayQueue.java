

public class ArrayQueue<AnyType> implements Queue<AnyType>
{
	private int size = 0;		//Nombre d'elements dans la file.
	private int startindex = 0;	//Index du premier element de la file
	private AnyType[] table;
   
	@SuppressWarnings("unchecked")
	public ArrayQueue() 
	{
		
		table =  (AnyType[]) new Object[5];
		
		
	}
	
	//Indique si la file est vide
	public boolean empty() 
	{ 
		return size == 0; 
	}
	
	//Retourne la taille de la file
	public int size() 
	{ 
		return size; 
	}
	
	//Retourne l'element en tete de file
	//Retourne null si la file est vide
	//complexité asymptotique: O(1)
	public AnyType peek()
	{
		//A completer
		if(empty()){
			return null;
		} 
		
		return table[startindex]; 
	}
	
	//Retire l'element en tete de file
	//complexité asymptotique: O(1)
	public void pop() throws EmptyQueueException
	{
		if(size==0){
			throw new EmptyQueueException(); 
		}
		
		table[startindex]=null;
		startindex= (startindex+1)%table.length ;
		size--; 
			
		
	}
	
	
	//Ajoute un element a la fin de la file
	//Double la taille de la file si necessaire (utiliser la fonction resize definie plus bas)
	//complexité asymptotique: O(1) ( O(N) lorsqu'un redimensionnement est necessaire )
	public void push(AnyType item)
	{
		
		//si le tableau est plein
		if (size == table.length)
		{
			resize(2); 
			
		}

		
		int index = (startindex + size) % table.length; 
		table[index] = item; 
		size++;
	
		
	}
   
	//Redimensionne la file. La capacite est multipliee par un facteur de resizeFactor.
	//Replace les elements de la file au debut du tableau
	//complexité asymptotique: O(N)
	@SuppressWarnings("unchecked")
	private void resize(int resizeFactor) 
	{
		AnyType[] oldTable = table;
		
	
		//creer un tableau pour stocker les anciennes valeurs
		table = (AnyType[]) new Object[oldTable.length*resizeFactor];
		
		
		//mettre les anciennes valeurs dans le tableau temporaire 
		for (int i = 0; i<size; i ++){
			table[i] = oldTable[(startindex +i)%oldTable.length ];
		}
		
		startindex = 0;
		
		
	}
	   
}

