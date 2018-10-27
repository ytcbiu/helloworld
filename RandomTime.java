package com.springboot.utils;

import java.util.ArrayList;
import java.util.List;

public class RandomTime {
	// 时间最小值
	private static final float MINVALUE = 0.01F;  

	// 时间最大值
	// private static final Integer MAXVALUE = 200;

	private static final Integer TIMES = 2;

	public static boolean isRight(float time, float count) {
		float avg = time / count;
		if (avg < MINVALUE) {
			return false;
		}
		return true;
	}

	public static float randomRedPacket(float time, float minS, float maxS, int count) {
		//当次数剩余一个时，把当前剩余全部返回  
		if (count == 1) {
			return time;
		}
		//如果当前最小时间等于最大时间，之间返回当前时间  
		if (minS == maxS) {
			return minS;
		}
		float max = maxS > time ? time : maxS;
		//随机产生一个时间  
		float one = (float) (Math.random() * (max - minS) + minS);
		float balance = time - one;
		//判断此次分配后，后续是否合理  
		if (isRight(balance, count - 1)) {
			return one;
		} else {
			//重新分配  
			float avg = balance / (count - 1);
			//如果本次时间过大，导致下次不够分，走这一条  
			if (avg < MINVALUE) {
				return randomRedPacket(time, minS, one, count);
			} else {
				return randomRedPacket(time, one, maxS, count);
			}
		}
	}

	public static List<Integer> spiltRedPackets(float time, int count) {
		List<Integer> list = new ArrayList<Integer>();
		float sum = 0;
//		if (count == 1) {
//			list.add(new Random().nextInt(time));
//			return list;
//		}
		if (!isRight(time, count)) {
			return null;
		}

		float max = time / count * TIMES;
		max = max > time ? time : max;
		for (int i = 0; i < count; i++) {
			float value = randomRedPacket(time, MINVALUE, max, count - i);
			sum+=value;
			list.add((int) (value * 1000));
			time -= value;
		}
		System.out.println(sum);
		return list;
	}

	public static void main(String[] args) {
		System.out.println(RandomTime.spiltRedPackets(21600, 25000));
	}
}
