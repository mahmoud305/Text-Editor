package textgen;

import java.util.AbstractList;


/** A class that implements a doubly linked list
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 *
 * @param <E> The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail;
	int size;

	/** Create a new empty LinkedList */
	public MyLinkedList() {
		// TODO: Implement this method
		head = new LLNode<E>(null);
		tail = new LLNode<E>(null);
		size=0;
		head.next=tail;
		tail.prev=head;
	}

	/**
	 * Appends an element to the end of the list
	 * @param element The element to add
	 */
	public boolean add(E element ) 
	{
		// TODO: Implement this method
		if(element==null) {
			throw new NullPointerException("null cannot be inserted") ;
		 
		}
		LLNode<E> newNode= new LLNode<E> (element);
		size++;
		if(head.next==tail) {// empty list
			
			head.next=newNode;
			newNode.prev=head;
			tail.prev=newNode;
			newNode.next=tail;
			return true;
		}
		
		
		LLNode<E> previousNode=tail.prev;
		tail.prev=newNode;
		newNode.prev=previousNode;
		previousNode.next=newNode;
		newNode.next=tail;
		return true;
	}

	/** Get the element at position index 
	 * @throws IndexOutOfBoundsException if the index is out of bounds. */
	public E get(int index) 
	{
		if(index>size||index<0) {
			throw new IndexOutOfBoundsException ("the index doesnot exist as it is out of bounds ");
		//	return null;
		}
		// TODO: Implement this method.
		LLNode<E> temp=head.next;
		int i=0;
		while(i<index) {
			temp=temp.next;
			i++;
		}
		return temp.data;
	}

	/**
	 * Add an element to the list at the specified index
	 * @param The index where the element should be added
	 * @param element The element to add
	 */
	public void add(int index, E element ) 
	{	
		if(element==null) {
		throw new NullPointerException("null cannot be inserted") ; 
		}	
		if(index>size+1||index<0) {
			throw new IndexOutOfBoundsException ("the index doesnot exist as it is out of bounds ");
		//	return null;
		}
		LLNode<E> newNode=new LLNode<E>(element);
		LLNode<E> temp= head.next;
		int i=0;
		while(i<index) {
			temp=temp.next;
			i++;
		}
		temp=temp.prev;
		newNode.prev=temp;
		newNode.next=temp.next;
		temp.next=newNode;
		newNode.next.prev=newNode;
		size++;
		
		// TODO: Implement this method
	}


	/** Return the size of the list */
	public int size() 
	{
		// TODO: Implement this method
		return size;
	}

	/** Remove a node at the specified index and return its data element.
	 * @param index The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException If index is outside the bounds of the list
	 * 
	 */
	public E remove(int index) 
	{
		// TODO: Implement this method
		if(index>size||index<0) {
			throw new IndexOutOfBoundsException("out of bounds cannot be deleted ");
		}
		LLNode<E> deletedNode=head.next;
		int i=0;
		while(i<index) {
			deletedNode=deletedNode.next;
			i++;
		}
		E deletedValue=deletedNode.data;//store the data to return it ;
		LLNode<E> previousNode=deletedNode.prev;
	
		deletedNode.next.prev=previousNode;
		previousNode.next=deletedNode.next;
		size--;
		return deletedValue;
	}

	/**
	 * Set an index position in the list to a new element
	 * @param index The index of the element to change
	 * @param element The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 */
	public E set(int index, E element) 
	{
		if(element==null)
			throw new NullPointerException("cannot insert null values");
		if(index>size||index<0) {
			throw new IndexOutOfBoundsException("out of bounds cannot be replaced ");
		}
		int i=0;
		LLNode<E> temp=head;
		while(i<index)
		{
			temp=temp.next;
			i++;
		}
		E replacedValue=temp.data;
		temp.data=element;
		// TODO: Implement this method
		return replacedValue;
	}


	public void print() {
		LLNode<E> temp=head.next;
		System.out.println("list : ");
		while(temp!=tail) {
			System.out.print(temp.data+" ");
			temp=temp.next;
		}
		System.out.println();
	}   
	
	
}

class LLNode<E> 
{
	LLNode<E> prev;
	LLNode<E> next;
	E data;

	// TODO: Add any other methods you think are useful here
	// E.g. you might want to add another constructor
	public LLNode() {}
	public LLNode(E e) 
	{
		this.data = e;
		this.prev = null;
		this.next = null;
	}

}
