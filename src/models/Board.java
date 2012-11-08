/**
 * 
 */
package models;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author Lukas
 *
 */
public class Board 
{
	private LinkedList<LayerGroup> board;
	
	//Constrains for the dimensions of the board
	private final int maxZ = 10;
	private final int maxX= 10;
	private final int maxY = 10;
	
	/**
	 * 
	 */
	public Board() 
	{
		this.board = new LinkedList<LayerGroup>();
	}
	
	
	/////////////////EXPLANATION///////////////
	
	/*
	 * - LayerGroup:	
	 * 				A layerGroup can contains layers which "touch" each other and contains at least one cube. Example: L_5
	 * 																												   L_6
	 * 																								  			       L_7
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

	private class LayerGroup
	{
		private LinkedList<Layer> layerGroup;
		private int layerGroupIndex; //z-coordinate
		
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
	}
	
	
	private class Layer
	{
		private LinkedList<LineGroup> layer;
	
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
