/**
 * 
 */
package models;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;

import exceptions.BoardException;

/**
 * @author Lukas
 *
 */
public class Board 
{
	private LinkedList<Segment> board;
	private UtilListIterator<Segment> utilListIteratorBoard = new UtilListIterator<Board.Segment>();
	
	//Constrains for the dimensions of the board
	private final int maxZ = 10;
	private final int maxX= 10;
	private final int maxY = 10;
	
	/**
	 * 
	 */
	public Board() 
	{
		this.board = new LinkedList<Segment>();
	}
	
	public void addCubeAt(Cube cube, int x, int y, int z)
	{
		//Assume that x,y,z is a valid position!
		
		ListIterator<Segment> iterator = this.board.listIterator();
		
		while(iterator.hasNext())
		{
			Segment seg = iterator.next();
			
			int headIndex = seg.getHeadIndex();
			int tailIndex =seg.getTailIndex();

			if(z>=headIndex && z<=tailIndex)	//cube position in the segment!
			{
				seg.addCubeAt(cube,x,y);
				break;
			}
			else if(z==headIndex-1) //new layerGroup at the head
			{
				//TODO create new layerGroup and test if neighbour distanz with the new layer is equal 0 -> merge both layerGroups together
				seg.createNewLayerGroupAt(cube,z,x,y);
				
				if(iterator.hasPrevious())
				{
					try 
					{
						Segment segNeighbour = utilListIteratorBoard.hasPrevNeighbour(iterator, seg);
						
						int tailIndexNeighbour = segNeighbour.getTailIndex();
						
						if(z==tailIndexNeighbour) //Maybe this case will never happen, because if two segments have a distance of only one unit, the case of new groupLayer at the tail of the prev seg will be executed first.
						{
							//Merge!
//							iterator.remove(); //remove the current seg //TODO HERE
							segNeighbour.merge(seg);
							iterator.add(segNeighbour);
						}
					} 
					catch (BoardException e) 
					{
						
					}
				}
				else	//no prev neighbour, because it's the first segment
				{
//					//finish
				}
//				if(i>0)
//				{
//					Segment segNeighbour = this.board.get(i-1);
//					int tailIndexNeighbour = segNeighbour.getTailIndex();
//
//					if((z==tailIndexNeighbour)
//							{
//						//merge!
//
//
//							}
//				}
//				else	
//				}
//
//				break;
			}
			else if(z==tailIndex+1) //new layerGroup at the tail
			{
				//TODO create new layerGroup and test if neighbour distanz with the new layer is equal 0 -> merge both layerGroups together
			}
		}
//		for(int i = 0; i<this.board.size(); i++)
//		{
//			Segment seg = this.board.get(i);
//			
//			int headIndex = seg.getHeadIndex();
//			int tailIndex =seg.getTailIndex();
//			
//			if(z>=headIndex && z<=tailIndex)
//			{
//				seg.addCubeAt(cube,x,y);
//				break;
//			}
//			else if(z==headIndex-1) //new layerGroup!
//			{
//				//TODO create new layerGroup and test if neighbour distanz with the new layer is equal 0 -> merge both layerGroups together
//				seg.createNewLayerGroupAt(cube,z,x,y);
//				
//				if(i>0)
//				{
//					Segment segNeighbour = this.board.get(i-1);
//					int tailIndexNeighbour = segNeighbour.getTailIndex();
//					
//					if((z==tailIndexNeighbour)
//					{
//						//merge!
//						
//						
//					}
//				}
//				else	//no prev neighbour, because it's the first segment
//				{
//					//finish
//				}
//				
//				break;
//			}
//			else if(z==tailIndex+1) //new layerGroup!
//			{
//				//TODO create new layerGroup and test if neighbour distanz with the new layer is equal 0 -> merge both layerGroups together
//			}
//		}
		
		System.out.println("invalid pos z. you can't add a cube at " + z); //TODO throw exception
		
	}
	
	/////////////////EXPLANATION///////////////
	
	/*
	 * -Board:																Example
	 * 																					L0
	 * 																					L1
	 * 		
	 * 																					L3
	 * 																					L4
	 * 																					L5
	 * - Segment:
	 * 				A segment contains layerGroups which "touch" each other. Example
	 * 																					L3
	 * 																					L4
	 * 																					L5
	 * 
	 * 
	 * 
	 * 
	 * - LayerGroup:	
	 * 				A layerGroup can contains layers which are in the same z-Coordinate. Example: 
	 * 																										 |z
	 * 																										 |
	 *          																							 |_ _ _ _ _y
	 * 																									     /CCC CCC
	 *              																						/C  CCC  CC
	 * 																									   /
	 *  																								  /C CCC C CCCC
	 *  																							     /CCC CC CC
	 *  																								x
	 *  
	 * 
	 * 																										 |z
	 * 																										 |
	 * - Layer:																								 |_ _ _ _ _y
	 * 				A layer contains lineGroups which touch each other in the same z-Coordinate.  Example:   /CCC CCC
	 * - LineGroup:																							/C  CCC  CC
	 * 				A lineGroup can contains a few lines. Example: CCCC CCC CC.							   /
	 * - Line																							  x
	 * 				A line contains cubes which touch each other. Example: CCCC
	 */
	
	///////////INNER CLASSES/////////////
	
	private class Segment
	{
		private LinkedList<LayerGroup> segment;
		private UtilListIterator<LayerGroup> utilListIterator = new UtilListIterator<LayerGroup>();
		
		public  UtilListIterator<LayerGroup> getUtilListIterator()
		{
			return this.utilListIterator;
		}
		
		public void createNewLayerGroupAt(Cube cube, int z, int x, int y) {
			// TODO Auto-generated method stub
			
		}

		public int getHeadIndex()
		{
			return this.segment.getFirst().getLayerGroupIndex() ;  //get the layerGroupIndex (= z - coordinate) of the first group of layers
		}

		public int getTailIndex()
		{
			return this.segment.getLast().getLayerGroupIndex() ; //get the layerGroupIndex (= z - coordinate) of the last group of layers
		}
		
	
		public void addCubeAt(Cube cube, int x, int y)
		{
			for(LayerGroup group: this.segment)
			{
				 
			}
			
		}
	}
	
	
	private class LayerGroup
	{
		private LinkedList<Layer> layerGroup;
		private int layerGroupIndex; //z-coordinate
		private UtilListIterator<Layer> utilListIterator = new UtilListIterator<Layer>();
		
		public  UtilListIterator<Layer> getUtilListIterator()
		{
			return this.utilListIterator;
		}
		
		
		public int getLayerGroupIndex()
		{
			return this.layerGroupIndex;
		}
		
		public int getHeadIndex()
		{
			return this.layerGroup.getFirst().getHeadIndex(); //the cubeIndex (=y coordinate) of the first cube in the first layer of the group)
		}
		
		public int getTailIndex()
		{
			return this.layerGroup.getLast().getTailIndex(); //the cubeIndex (=y coordinate) of the last cube in the last layer of the group)
		}
		
		public void addLayer(Layer layer)
		{
			
		}
	}
	
	
	private class Layer
	{
		private LinkedList<LineGroup> layer;
		private UtilListIterator<LineGroup> utilListIterator = new UtilListIterator<LineGroup>();
		
		public  UtilListIterator<LineGroup> getUtilListIterator()
		{
			return this.utilListIterator;
		}
		
		public int getHeadIndex()
		{
			return this.layer.getFirst().getLineGroupIndex() ;  //get the lineGroupIndex (= x - coordinate) of the first group of lines
		}
		
		public int getTailIndex()
		{
			return this.layer.getLast().getLineGroupIndex() ; //get the lineGroupIndex (= x - coordinate) of the last group of lines
		}
	}
	
	private class LineGroup //(ex. lane class)
	{
		private LinkedList<Line> lineGroup;
		private int lineGroupIndex; //x-coordinate
		private UtilListIterator<Line> utilListIterator = new UtilListIterator<Line>();
		
		public  UtilListIterator<Line> getUtilListIterator()
		{
			return this.utilListIterator;
		}
		
		public int getLineGroupIndex()
		{
			return this.lineGroupIndex;
		}
		
		public int getHeadIndex()
		{
			return this.lineGroup.getFirst().getHeadIndex(); //the cubeIndex (=y coordinate) of the first cube in the first line of the group
		}
		
		public int getTailIndex()
		{
			return this.lineGroup.getLast().getTailIndex(); //the cubeIndex (=y coordinate) of the last cube in the last line of the group
		}
	}
	
	private class Line
	{
		private LinkedList<Cube> line;
		private UtilListIterator<Cube> utilListIterator = new UtilListIterator<Cube>();
		
		public  UtilListIterator<Cube> getUtilListIterator()
		{
			return this.utilListIterator;
		}
		public int getHeadIndex()
		{
			return this.line.getFirst().getCubeIndex();
		}
		
		public int getTailIndex()
		{
			return this.line.getLast().getCubeIndex();
		}
	}
}
