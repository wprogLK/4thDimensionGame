package tests;

import java.util.Arrays;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.equalTo;

import models.FaceDirection;

import org.junit.Test;
import org.junit.runner.RunWith;
import ch.unibe.jexample.JExample;

@RunWith(JExample.class)
public class FaceDirectionTest 
{
	
	FaceDirection startX = FaceDirection.UP;
	FaceDirection startY = FaceDirection.LEFT;
	FaceDirection startZ = FaceDirection.UP;
	
	@Test
	public void FaceNextXTest() 
	{
		FaceDirection currentFace = startX;

		currentFace = currentFace.nextX();
		assertThat(currentFace,equalTo(FaceDirection.FRONT));
		
		currentFace = currentFace.nextX();
		assertThat(currentFace,equalTo(FaceDirection.DOWN));
		
		currentFace = currentFace.nextX();
		assertThat(currentFace,equalTo(FaceDirection.BACK));
		
		currentFace = currentFace.nextX();
		assertThat(currentFace,equalTo(FaceDirection.UP));
	}
	@Test
	public void FaceNextYTest() 
	{
		FaceDirection currentFace = startY;

		currentFace = currentFace.nextY();
		assertThat(currentFace,equalTo(FaceDirection.FRONT));
		
		currentFace = currentFace.nextY();
		assertThat(currentFace,equalTo(FaceDirection.RIGHT));
		
		currentFace = currentFace.nextY();
		assertThat(currentFace,equalTo(FaceDirection.BACK));
		
		currentFace = currentFace.nextY();
		assertThat(currentFace,equalTo(FaceDirection.LEFT));
	}

	@Test
	public void FaceNextZTest() 
	{
		FaceDirection currentFace = startZ;

		currentFace = currentFace.nextZ();
		assertThat(currentFace,equalTo(FaceDirection.LEFT));
		
		currentFace = currentFace.nextZ();
		assertThat(currentFace,equalTo(FaceDirection.DOWN));
		
		currentFace = currentFace.nextZ();
		assertThat(currentFace,equalTo(FaceDirection.RIGHT));
		
		currentFace = currentFace.nextZ();
		assertThat(currentFace,equalTo(FaceDirection.UP));
	}
	

	@Test
	public void FacePrevXTest() 
	{
		FaceDirection currentFace = startX;

		currentFace = currentFace.prevX();
		assertThat(currentFace,equalTo(FaceDirection.BACK));
		
		currentFace = currentFace.prevX();
		assertThat(currentFace,equalTo(FaceDirection.DOWN));
		
		currentFace = currentFace.prevX();
		assertThat(currentFace,equalTo(FaceDirection.FRONT));
		
		currentFace = currentFace.prevX();
		assertThat(currentFace,equalTo(FaceDirection.UP));
	}
	
	
	@Test
	public void FacePrevYTest() 
	{
		FaceDirection currentFace = startY;

		currentFace = currentFace.prevY();
		assertThat(currentFace,equalTo(FaceDirection.BACK));
		
		currentFace = currentFace.prevY();
		assertThat(currentFace,equalTo(FaceDirection.RIGHT));
		
		currentFace = currentFace.prevY();
		assertThat(currentFace,equalTo(FaceDirection.FRONT));
		
		currentFace = currentFace.prevY();
		assertThat(currentFace,equalTo(FaceDirection.LEFT));
	}
	
	@Test
	public void FacePrevZTest() 
	{
		FaceDirection currentFace = startZ;

		currentFace = currentFace.prevZ();
		assertThat(currentFace,equalTo(FaceDirection.RIGHT));
		
		currentFace = currentFace.prevZ();
		assertThat(currentFace,equalTo(FaceDirection.DOWN));
		
		currentFace = currentFace.prevZ();
		assertThat(currentFace,equalTo(FaceDirection.LEFT));
		
		currentFace = currentFace.prevZ();
		assertThat(currentFace,equalTo(FaceDirection.UP));
	}
	
	//TODO
}
