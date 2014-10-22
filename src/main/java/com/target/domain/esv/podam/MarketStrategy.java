package com.target.domain.esv.podam;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import uk.co.jemos.podam.api.AttributeStrategy;
import uk.co.jemos.podam.exceptions.PodamMockeryException;

public class MarketStrategy implements AttributeStrategy<String> {

    Random rand = new Random();
    List<String> availableMarkets = Arrays.asList("MSP", "BOS");

    //createNewInstanceForClassWithoutConstructors
	public String getValue() throws PodamMockeryException {
		
		
        int index = rand.nextInt(availableMarkets.size());
        

        return availableMarkets.get(index);
	}

}
