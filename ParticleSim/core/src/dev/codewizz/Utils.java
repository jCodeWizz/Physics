package dev.codewizz;

public class Utils {
	
	public static float clamp(float value, float max, float min) {
		if(value >= max)
			return max;
		if(value <= min) {
			return min;
		} else {
			return value;
		}
	}
}
