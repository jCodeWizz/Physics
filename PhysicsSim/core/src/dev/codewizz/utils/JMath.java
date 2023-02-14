package dev.codewizz.utils;

public class JMath {

	public static float clamp(float value, float min, float max) {
		if(min == max) { return min; }
		if(min > max) Debug.throwStateError("JMath.clamp: min > max (Unclampable)");
		
		if(value < min) 
			return min;
		
		if(value > max)
			return max;
		
		return value;
	}
	
}
