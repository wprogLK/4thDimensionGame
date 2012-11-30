package models;

import java.util.LinkedList;
import java.util.ListIterator;

import exceptions.BoardException;

public class UtilListIterator<T>
{
//	public static void main(String[] args) 
//	{
//		LinkedList<String> link = new LinkedList<String>();
//		
//		link.add("A");
//		link.add("B");
//		link.add("C");
//		link.add("D");
//		link.add("E");
//		
//		ListIterator<String> iterator = link.listIterator();
//		
//		while(iterator.hasNext())
//		{
//			String current = iterator.next();
//			
//			System.out.println("The current is " + current);
//			try 
//			{
//				String prevNeighbour = hasPrevNeighbour(iterator, current);
//				System.out.println("The prev neighbour of "+ current +" is " + prevNeighbour);
//			} 
//			catch (BoardException e) 
//			{
//				System.out.println(e.getMessage());
//			}
//			try 
//			{
//				String nextNeighbour = hasNextNeighbour(iterator, current);
//				System.out.println("The next neighbour of "+ current +" is " + nextNeighbour);
//			} 
//			catch (BoardException e) 
//			{
//				System.out.println(e.getMessage());
//			}
//		}
//	}
	public T hasPrevNeighbour(ListIterator<T> iterator, T current) throws BoardException
	{
		T prev = current;
		int counter = 0;
		
		while(iterator.hasPrevious() && current.equals(prev))
		{
			 current = prev;
			 prev = iterator.previous();
			
			counter++;
		}
		
		for(int i=0; i<counter;i++)
		{
			iterator.next();
		}
		
		if(!prev.equals(current))
		{
			return prev;
		}
		else
		{
			System.out.println();
			throw new BoardException(current + " has no prev neighbour!");
		}
	}
	
	public T hasNextNeighbour(ListIterator<T> iterator, T current) throws BoardException
	{
		T next = current;
		int counter = 0;
		
		while(iterator.hasNext() && current.equals(next))
		{
			 current = next;
			 next = iterator.next();
			
			counter++;
		}
		
		for(int i=0; i<counter;i++)
		{
			iterator.previous();
		}
		
		if(!next.equals(current))
		{
			return next;
		}
		else
		{
			System.out.println();
			throw new BoardException(current + " has no next neighbour!");
		}
	}
}
