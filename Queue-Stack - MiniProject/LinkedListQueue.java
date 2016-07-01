
public class LinkedListQueue<AnyType> implements Queue<AnyType>
{	
	// Un noeud de la file
	@SuppressWarnings("hiding")
	private class Node<AnyType> 
	{
		private AnyType data;
		private Node next;
		
		public Node(AnyType data, Node next) 
		{
			this.data = data;
			this.next = next;
		}

		public void setNext(Node next) 
		{
			this.next = next;
		}
		
		public Node<AnyType> getNext() 
		{
			return next;
		}
		
		public AnyType getData() 
		{
			return data;
		}
	}
   
	private int size = 0;		//Nombre d'elements dans la file.
	private Node<AnyType> last;	//Dernier element de la liste
	
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
		
		if(empty())
			return null;
		return last.getNext().getData(); 
		
		
	}
	
	//Retire l'element en tete de file
	//complexité asymptotique: O(1)
	public void pop() throws EmptyQueueException
	{
		
			
		if(!empty()){
			last.setNext(last.next.getNext());	
		}
		else{
			throw new EmptyQueueException(); 
		}
		
		size--; 
		
		
	}
	
	//Ajoute un element a la fin de la file
	//complexité asymptotique: O(1)
	public void push(AnyType item)
	{		
		
		//A completer
	if(!empty()){
		Node node = new Node(item, last.getNext()); 
		last.setNext(node); 
		last= node; 
	} 
		
	else{
		Node node = new Node(item, null); 
		last=node; 
		last.next=node; //il pointe vers lui meme car il est seul 
	}
		
	size++;
		
	}  
}


