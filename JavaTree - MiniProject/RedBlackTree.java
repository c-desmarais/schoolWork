import java.util.LinkedList;
import java.util.Queue;

public class RedBlackTree<T extends Comparable<? super T> > 
{
   private RBNode<T> root;  // Racine de l'arbre
   enum ChildType{ left, right }
   
   public RedBlackTree()
   { 
      root = null;
   }
   
   public void printFancyTree()
   {
      printFancyTree( root, "", ChildType.right);
   }
   
   private void printFancyTree( RBNode<T> t, String prefix, ChildType myChildType)
   {
      System.out.print( prefix + "|__"); // un | et trois _
      
      if( t != null )
      {
         boolean isLeaf = (t.isNil()) || ( t.leftChild.isNil() && t.rightChild.isNil() );
         
         System.out.println( t );
         String _prefix = prefix;
         
         if( myChildType == ChildType.left )
            _prefix += "|   "; // un | et trois espaces
         else
            _prefix += "   " ; // trois espaces
         
         if( !isLeaf )
         {
            printFancyTree( t.leftChild, _prefix, ChildType.left );
            printFancyTree( t.rightChild, _prefix, ChildType.right );
         }
      }
      else
         System.out.print("null\n");
   }
   
   public T find(int key)
   {
      return find(root, key);
   }
   
   private T find(RBNode<T> current, int key)
   {
	   if(!current.isNil()){
		   //si la cle est plus petite on cherche a gauche
		   if (key < current.value.hashCode()){
			   //envoie lenfant de gauche du noeud traite ainsi que la meme cle quon cherche
               return find(current.leftChild, key);
           }
		   //si la cle est plus grande on cherche a droite
           else if (key > current.value.hashCode()) {
               return find(current.rightChild, key);
           }
		   //si la cle est egale a ce quon cherche 
           else{
               return current.value;
           }
		   
	   }
	   return null; 
   }
   
   public void insert(T val)
   {
      insertNode( new RBNode<T>( val ) );
   }
   
   private void insertNode( RBNode<T> newNode )
   { 
      if (root == null)  // Si arbre vide
         root = newNode;
      else
      {
         RBNode<T> position = root; // On se place a la racine
         
         while( true ) // on insere le noeud (AVR standard)
         {
            int newKey = newNode.value.hashCode();
            int posKey = position.value.hashCode();
            
            if ( newKey < posKey ) 
            {
               if ( position.leftChild.isNil() ) 
               {
                  position.leftChild = newNode;
                  newNode.parent = position;
                  break;
               } 
                  else 
                  position = position.leftChild;
            } 
            else if ( newKey > posKey ) 
            {
               if ( position.rightChild.isNil() )
               {
                  position.rightChild = newNode;
                  newNode.parent = position;
                  break;
               }
               else 
                  position = position.rightChild;
            }
            else // pas de doublons
               return;
         }
      }
      
      insertionCases( newNode );
   }

   private void insertionCases( RBNode<T> X )
   {	
      insertionCase1( X );
   }
   
   //Si le noeud est a la racine de l'arbre, le noeud devient noir   
   private void insertionCase1 ( RBNode<T> X )
   {	
      if (X==root)
    	 X.setToBlack();
      else  
    	  insertionCase2( X );
   }

   //Si le parent du noeud est noir, tout est correct, sinon on passe a insertionCase3
   private void insertionCase2( RBNode<T> X )
   {	
	   if(!X.parent.isBlack())
		   insertionCase3( X );
   }

   //Si parent et oncle sont rouges, alors les mettre noir et vérifier les cas sur le grand-parent
   private void insertionCase3( RBNode<T> X )
   {	
      if(X.parent.isRed()&& X.uncle().isRed()){
    	  X.parent.setToBlack();
    	  X.uncle().setToBlack();
    	  if(X.grandParent()!= null) 
    		  insertionCases(X.grandParent()); 
    	 
      }
      else
    	  insertionCase4( X );
   }
	
   private void insertionCase4( RBNode<T> X )
   {
//parent est rouge,	l’oncle	est	noir, nœud est un enfant de gauche et le parent est un enfant de droite
	   if(X.parent.isRed() && X.uncle().isBlack() && ((X == X.parent.leftChild && X.parent == X.grandParent().rightChild) || (X == X.parent.rightChild && (X.parent == X.grandParent().leftChild)))){ 
		       // nœud est un enfant de gauche et le parent est un enfant de droite
		   	   if (X == X.parent.leftChild && X.parent == X.grandParent().rightChild){
				   rotateRight(X.parent); 
				   insertionCase5(X.rightChild);
			   }
			   //nœud est un enfant de droite et le parent est un enfant de gauche
			   else{
				   rotateLeft(X.parent); 
				   insertionCase5(X.leftChild); 
			   }
	   }
	   else {
		   insertionCase5( X );
	   }
				  
   }

   private void insertionCase5( RBNode<T> X )
   {
//parent est rouge,	l’oncle	est	noir, X est	l’enfant de	droite de P et P est l’enfant de droite de G
	  if (X.parent.isRed() && X.uncle().isBlack() && (((X== X.parent.rightChild) && (X.parent == X.grandParent().rightChild)) || ((X== X.parent.leftChild) && (X.parent == X.grandParent().leftChild)))){
		  if((X== X.parent.rightChild) && (X.parent == X.grandParent().rightChild)){
			  
			  if(X.grandParent().isBlack())
				  X.grandParent().setToRed();
			  else
				  X.grandParent().setToBlack();
			  
			  X.parent.setToBlack();
			  
			  rotateLeft(X.grandParent());
		  }
		  //cas symétrique : gauche - gauche
		  else{
			  if(X.grandParent().isBlack())
				  X.grandParent().setToRed();
			  else
				  X.grandParent().setToBlack();
			  
			  X.parent.setToBlack(); 
			  
			  rotateRight(X.grandParent()); 
		  }
		
	  }
				  
      return; 
   }
   
   //Les commentaires sont basés sur les arbres suivants (G!=root et G==root respectivement):  
  /*
         A             B
           F         C  F
        B    I     E D  I J
      C   H
     E D              
  
  */
   private void rotateLeft( RBNode<T> G )
   {    
	   RBNode<T> Rc = G.rightChild; 		   
       RBNode<T> LcRc = Rc.leftChild;

       if(G!=root) {
    	   RBNode<T> P = G.parent; //Node A  
    	   Rc.leftChild = G; //B devient lenfant de gauche de F
    	   G.rightChild = LcRc; //H devient lenfant de droite de B
    	   Rc.parent = P; //A devient le parent de F
    	   G.parent = Rc; //F devient le parent de B  
       
    	   if(G.parent.leftChild == G) //Si B cest lenfant de gauche on garde larbre a gauche
    		   P.leftChild = Rc; //L'enfant de gauche de A est F 
    	   else 
    		   P.rightChild= Rc; //L'enfant de droite de A est F
       }
   
       else {
    	   Rc.leftChild = G; //B va devenir lenfant de gauche de F
    	   G.rightChild =  LcRc;  //I va devenir lenfant de droite de B
    	   root = Rc; 
    	   Rc.parent = null;     
       }

	   return; 
   }
   
   //Les commentaires sont basés sur les arbres suivants (G!=root et G==root respectivement): 
   /*
          A              B
            B          C  F
         C    F       E D
       E  D                 
   
   */
   private void rotateRight( RBNode<T> G )
   { 
	  //Node qui va remonter jusqu'a la position du noeud qui est présentement G 
	   RBNode<T> Lc = G.leftChild; 	//node G
	   
	 //Lenfant droit de la node qui va remonter  
       RBNode<T> RcLC = Lc.rightChild;

       if(G!=root) {
    	   RBNode<T> P= G.parent; //Node A 
    	   Lc.rightChild= G; //C a pour enfant de droite B 
    	   G.leftChild = RcLC; //L'enfant de gauche de B devient D
    	   Lc.parent= P; //Le parent de C devient A
    	   G.parent = Lc; //Le parent de B devient C 
       
    	   if(G.parent.leftChild == G) //Si B cest lenfant de gauche on garde larbre a gauche
    		   P.leftChild = Lc; //L'enfant de gauche de A devient C
    	   else 
    		   P.rightChild= Lc; //L'enfant de droite de A devient C
       }
   
       else {
    	   Lc.rightChild = G; //B va devenir lenfant de droite de LC
    	   G.leftChild =  RcLC;  //D devient lenfant de gauche de B 
    	   root = Lc; //Root va changer (devient C)
    	   Lc.parent = null;     //La root na pas de parent
       }

	   return; 
   }


   public void printTreePreOrder()
   {
      if(root == null)
         System.out.println( "Empty tree" );
      else
      {
         System.out.print( "PreOrdre ( ");
         printTreePreOrder( root );
         System.out.println( " )");
      }
      return;
   }
   
   private void printTreePreOrder( RBNode<T> P )
   {     
	   if (P.value != null){
		   
		   if(P.value != root.value )
			   System.out.print (", "); //Pour qu'il n'y ai pas de virgule au dernier affichage
           System.out.print("{" + P.toString() + "}" ); //imprime la node 
                     
           printTreePreOrder(P.leftChild); //imprime la node gauche
           printTreePreOrder(P.rightChild); //imprime la node droite
           
       }
      return; 
   }
   
   public void printTreePostOrder()
   {
      if(root == null)
         System.out.println( "Empty tree" );
      else
      {
         System.out.print( "PostOrdre ( ");
         printTreePostOrder( root );
         System.out.println( ")");
      }
      return;
   }
  
   private void printTreePostOrder( RBNode<T> P )
   {	 
	   if (P.value != null){  
           printTreePostOrder(P.leftChild);
           printTreePostOrder(P.rightChild);
           System.out.print("{" + P.toString() + "}" );
           
           if(P.value == root.value)
        	   System.out.print (" ");
           else
        	   System.out.print (", "); //pour ne pas avoir de virgule dans le dernier affichage
       }
      return; 
   }
   

   public void printTreeAscendingOrder()
   {
      if(root == null)
         System.out.println( "Empty tree" );
      else
      {
         System.out.print( "AscendingOrdre ( ");
         printTreeAscendingOrder( root );
         System.out.println( " )");
      }
      return;
   }
  
   private void printTreeAscendingOrder( RBNode<T> P )
   {      
	   if (P.value != null){  
		   printTreeAscendingOrder(P.leftChild);
		   System.out.print("{" + P.toString() + "}" );
		   System.out.print(", " );  //La dernière virgule est présente dans "AffichageAttendu" donc elle est laissée ici
		   printTreeAscendingOrder(P.rightChild);		   
	   }
	   return;
   }
   
   	
   public void printTreeDescendingOrder()
   {
      if(root == null)
         System.out.println( "Empty tree" );
      else
      {
         System.out.print( "DescendingOrdre ( ");
         printTreeDescendingOrder( root );
         System.out.println( " )");
      }
      return;
   }

   private void printTreeDescendingOrder( RBNode<T> P )
   {     
	   if (P.value != null){
		   printTreeDescendingOrder(P.rightChild);
		   System.out.print("{" + P.toString() + "}" );
		   System.out.print(", " ); //La dernière virgule est présente dans "AffichageAttendu" donc elle est laissée ici
		   printTreeDescendingOrder(P.leftChild);	
   
	   }
   }
   public void printTreeLevelOrder()
   { 
      if(root == null)
         System.out.println( "Empty tree" );
      else
      {
         System.out.print( "LevelOrdre ( ");
         
         Queue<RBNode<T>> q = new LinkedList<RBNode<T>>();
         
         q.add(root);

        	while(!q.isEmpty()){ //tant que la queue n'est pas vide
        		RBNode<T>P = q.poll(); // retire element
        		if(P.value != root.value) 
        			System.out.print(", " ); // pour n'avoir des virqules qu'entre les elements
        		System.out.print("{" + P.toString() + "}" );
        		
        		 if(P.leftChild.value != null) //ajoute les enfants de l'element à la queue
        			q.add(P.leftChild);
        		 
        		 if(P.rightChild.value != null)
        			 q.add(P.rightChild);

         }
         
         System.out.println( " )");
      }
      return;
   }
   

 
   private static class RBNode<T extends Comparable<? super T> > 
   {
      enum RB_COLOR { BLACK, RED }  // Couleur possible
      
      RBNode<T>  parent;      // Noeud parent
      RBNode<T>  leftChild;   // Feuille gauche
      RBNode<T>  rightChild;  // Feuille droite
      RB_COLOR   color;       // Couleur du noeud
      T          value;       // Valeur du noeud
      
      // Constructeur (NIL)   
      RBNode() { setToBlack(); }
      
      // Constructeur (feuille)   
      RBNode(T val)
      {
         setToRed();
         value = val;
         leftChild = new RBNode<T>();
         leftChild.parent = this;
         rightChild = new RBNode<T>();
         rightChild.parent = this;
      }
      
      RBNode<T> grandParent()
      {  
    	  if (this.parent==null)// si n'a pas de parent
    		  return null;
    				  
    	  return this.parent.parent;
      }
      
      RBNode<T> uncle()
      {  
         return this.parent.sibling();
      }
      
      RBNode<T> sibling()
      {  
    	 if(this.parent.leftChild == this)
    		 return this.parent.rightChild;
    	 return this.parent.leftChild;
      }
      
      public String toString()
      {
         return value + " (" + (color == RB_COLOR.BLACK ? "black" : "red") + ")"; 
      }
      
      boolean isBlack(){ return (color == RB_COLOR.BLACK); }
      boolean isRed(){ return (color == RB_COLOR.RED); }
      boolean isNil(){ return (leftChild == null) && (rightChild == null); }
      
      void setToBlack(){ color = RB_COLOR.BLACK; }
      void setToRed(){ color = RB_COLOR.RED; }
   }
}
