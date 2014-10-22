package com.target.domain.esv.podam;

import uk.co.jemos.podam.api.AttributeStrategy;
import uk.co.jemos.podam.exceptions.PodamMockeryException;

public class RandomBooleanStrategy implements AttributeStrategy<Boolean> {

	public Boolean getValue() throws PodamMockeryException {

		Boolean value = new Boolean(getRandomBoolean());
		return value;
	}

	
	  public static boolean getRandomBoolean() {
	       return Math.random() < 0.5;
	       //I tried another approaches here, still the same result
	   }
	  
	  
	  public Boolean doof(){
		  return new Boolean(Math.random() < 0.5);
	  }
	  
	  
}
