package com.qixuan.recommendation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SimilarityCompute {

	
	
	public static void main(String[] args) {
		
		/**
		 * User-Based
		 */
		/*long startTime=System.currentTimeMillis();
		
		Map<String, Map<String, String>> testMap = PreProcessing.getUserMap("test.txt");
		Map<String, Map<String, String>> trainMap = PreProcessing.getUserMap("train.txt");
		
		Map<String, Map<String, Double>> simMap = getSimMap(trainMap);
//		System.out.println("simMap.size(): "+simMap.size());
		
		double sumError = 0;
		int count = 0;
		Iterator<String> testIt = testMap.keySet().iterator();
		while(testIt.hasNext()){
			String usrName = testIt.next();
			Iterator<String> mvIt = testMap.get(usrName).keySet().iterator();
			while(mvIt.hasNext()){
				String movie = mvIt.next();
				int prediction = (int)(getRating(usrName,movie,trainMap,simMap)+0.5);	
//				int prediction = (int)(getRating(movie,usrName,trainMap,simMap)+0.5);
				sumError += Math.abs(prediction-Integer.valueOf(testMap.get(usrName).get(movie)));
				count++;
			}
		}
		long endTime=System.currentTimeMillis(); //获取结束时间  
		System.out.println("\nUBCF-Time Cost： "+(endTime-startTime)+" ms");
		
		System.out.println("UBCF-MAE = "+sumError/count);
		System.out.println("UBCF-RMSE = "+Math.sqrt(sumError/(count-1)));*/
		
		/*********************************************************************
		 * Item-Based
		 */
		/*long startTime2=System.currentTimeMillis();
		Map<String, Map<String, String>> testMap3 = PreProcessing.getUserMap("test.txt");
		Map<String, Map<String, String>> trainMap3 = PreProcessing.getUserMap("train.txt");
		
		Map<String, Map<String, Double>> simMap3 = getSimMap(trainMap3);
//		System.out.println("simMap.size(): "+simMap.size());
		
		double sumError2 = 0;
		int count2 = 0;
		Iterator<String> testIt3 = testMap3.keySet().iterator();
		while(testIt3.hasNext()){
			String usrName = testIt3.next();
			Iterator<String> mvIt = testMap3.get(usrName).keySet().iterator();
			while(mvIt.hasNext()){
				String movie = mvIt.next();
				int prediction = (int)(getRating2(usrName,movie,trainMap3,simMap3)+0.5);	
//				int prediction = (int)(getRating2(movie,usrName,trainMap,simMap)+0.5);
				sumError2 += Math.abs(prediction-Integer.valueOf(testMap3.get(usrName).get(movie)));
				count2++;
			}
		}
		long endTime2=System.currentTimeMillis(); //获取结束时间  
		System.out.println("\nIBCF-Time Cost： "+(endTime2-startTime2)+" ms");
		
		System.out.println("IBCF-MAE = "+sumError2/count2);
		System.out.println("IBCF-RMSE = "+Math.sqrt(sumError2/(count2-1)));*/
		
		
		/******************************************************
		 * Hybrid of UB and IB
		 */

		
		
		
		long startTime=System.currentTimeMillis();
		
		Map<String, Map<String, Double>> predicateMap = new HashMap<String, Map<String,Double>>();
		
		Map<String, Map<String, String>> testMap = PreProcessing.getUserMap("test.txt");
		Map<String, Map<String, String>> trainMap = PreProcessing.getUserMap("train.txt");
		
		Map<String, Map<String, Double>> simMap = getSimMap(trainMap);
//		System.out.println("simMap.size(): "+simMap.size());
		
		Iterator<String> testIt = testMap.keySet().iterator();
		while(testIt.hasNext()){
			String usrName = testIt.next();
			Map<String,Double> usMvMap;
			if(predicateMap.containsKey(usrName))
				usMvMap = predicateMap.get(usrName);
			else{
				usMvMap = new HashMap<String, Double>();
				predicateMap.put(usrName, usMvMap);
			}
			Iterator<String> mvIt = testMap.get(usrName).keySet().iterator();
			while(mvIt.hasNext()){
				String movie = mvIt.next();
				double prediction = getRating2(usrName,movie,trainMap,simMap);
				usMvMap.put(movie, prediction);
			}
		}
		
		Map<String, Map<String, Double>> predicateMap2 = new HashMap<String, Map<String,Double>>();
		
		Map<String, Map<String, String>> testMap2 = PreProcessing.getUserMap2("test.txt");
		Map<String, Map<String, String>> trainMap2 = PreProcessing.getUserMap2("train.txt");
		
		Map<String, Map<String, Double>> simMap2 = getSimMap(trainMap2);
//		System.out.println("simMap.size(): "+simMap.size());
		
		Iterator<String> testIt2 = testMap2.keySet().iterator();
		while(testIt2.hasNext()){
			String usrName = testIt2.next();
			Map<String,Double> usMvMap;
			
			Iterator<String> mvIt = testMap2.get(usrName).keySet().iterator();
			while(mvIt.hasNext()){

				String movie = mvIt.next();
				
				if(predicateMap2.containsKey(movie))
					usMvMap = predicateMap2.get(movie);
				else{
					usMvMap = new HashMap<String, Double>();
					predicateMap2.put(movie, usMvMap);
				}
				
				double prediction = getRating2(usrName,movie,trainMap2,simMap2);
				usMvMap.put(usrName, prediction);
			}
		}
		
		
		
		
//		System.out.println("simMap.size(): "+simMap.size());
		
		double sumError = 0;
		double sumErrorD = 0;
		int count = 0;
		Iterator<String> testIt3 = testMap.keySet().iterator();
		while(testIt3.hasNext()){
			String usrName = testIt3.next();
			Iterator<String> mvIt = testMap.get(usrName).keySet().iterator();
			while(mvIt.hasNext()){
				String movie = mvIt.next();
				int prediction = (int)(predicateMap.get(usrName).get(movie)*0.6545+predicateMap2.get(usrName).get(movie)*0.3455+0.5);	
				int de = prediction-Integer.valueOf(testMap.get(usrName).get(movie));
				sumError += Math.abs(de);
				sumErrorD += de*de;
				count++;
			}
		}
		long endTime=System.currentTimeMillis(); //获取结束时间  
		System.out.println("\nHybrid-CF-Time Cost： "+(endTime-startTime)+" ms");
		
		System.out.println("Hybrid-CF-MAE = "+sumError/count);
		System.out.println("Hybrid-CF-RMSE = "+Math.sqrt(sumErrorD/(count-1)));
		
		
	}
	
	
	static public double getRating2(String usrName, String movie, Map<String, Map<String, String>> trainMap,
			Map<String, Map<String, Double>> simMap){
		
		Map<String, Double> simVecter = simMap.get(usrName);
		
		/**
		 * 解决训练集不存在的情况
		 */
		if(simVecter==null)
			return 3;
		
		double rating = 0;
		double sum = 0;
//		System.out.println("usrName: "+usrName);
		double ar = getAverage(trainMap.get(usrName));
		
		Iterator<String> simIt = simVecter.keySet().iterator();
		while(simIt.hasNext()){
			String usr2 = simIt.next();
			if(trainMap.get(usr2).containsKey(movie)){
				double sim = simVecter.get(usr2);
				
//				if(sim==0){
//					sim = getAverage2(simVecter);
////					System.out.println("sim==0 :"+sim);
//				}
				
				sum += Math.abs(sim);
				double rate = Double.valueOf(trainMap.get(usr2).get(movie));
				double avg = getAverage(trainMap.get(usr2));
				//TODO
				/*****************打分函数**************/
				rating += sim*(rate-avg)/avg*ar;
			}
		}

//		System.out.println("rating: "+rating/sum);
		
		if(sum==0){
			return ar;
		}
		double r = ar + rating/sum;
//		if(r<0||r>5){
//			System.out.print("*");
////			System.out.println(r);
//			return r;
//		}
		return r;
	}
	
	static public double getRating(String usrName, String movie, Map<String, Map<String, String>> trainMap,
			Map<String, Map<String, Double>> simMap){
		
		Map<String, Double> simVecter = simMap.get(usrName);
		
		/**
		 * 解决训练集不存在的情况
		 */
		if(simVecter==null)
			return 3;
		
		double rating = 0;
		double sum = 0;
//		System.out.println("usrName: "+usrName);
		double ar = getAverage(trainMap.get(usrName));
		
		Iterator<String> simIt = simVecter.keySet().iterator();
		while(simIt.hasNext()){
			String usr2 = simIt.next();
			if(trainMap.get(usr2).containsKey(movie)){
				double sim = simVecter.get(usr2);
				
//				if(sim==0){
//					sim = getAverage2(simVecter);
////					System.out.println("sim==0 :"+sim);
//				}
				
				sum += Math.abs(sim);
				double rate = Double.valueOf(trainMap.get(usr2).get(movie));
				double avg = getAverage(trainMap.get(usr2));
				//TODO
				/*****************打分函数**************/
				rating += sim*(rate-avg)/*/avg*ar*/;
			}
		}

//		System.out.println("rating: "+rating/sum);
		
		if(sum==0){
			return ar;
		}
		double r = ar + rating/sum;
//		if(r<0||r>5){
//			System.out.print("*");
////			System.out.println(r);
//			return r;
//		}
		return r;
	}
	
	
	
	static public Map<String, Map<String, Double>> getSimMap2(Map<String, Map<String, String>> userMap){
		
		Map<String, Map<String, Double>> simMap = new HashMap<String, Map<String, Double>>();
		
		Iterator<String> usrIt = userMap.keySet().iterator();
		ArrayList<String> userArray = new ArrayList<String>();
		while(usrIt.hasNext()){
			userArray.add(usrIt.next());
		}
		
		int len = userArray.size();
		for(int i=0;i<len;i++){
			
			//用户X的trainMap和userName
			Map<String, String> usr1 = userMap.get(userArray.get(i));
			String usrName1 = userArray.get(i);
			
			for(int j=0;j<len;j++){
				if(i!=j){
					Map<String, String> usr2 = userMap.get(userArray.get(j));
					String usrName2 = userArray.get(j);
					Map<String, Double> usrNSim;
					if(simMap.containsKey(usrName1)){
						usrNSim = simMap.get(usrName1);
					}
					else{
						usrNSim = new HashMap<String, Double>();
						simMap.put(usrName1, usrNSim);
					}
					double sim = personSimilarity2(usr1,usr2);
//					if(sim==0){
//						System.out.println(usrName1+" 不像 "+usrName2);
//						System.out.println("personSimilarity:\t"+sim);
//					}
					usrNSim.put(usrName2, sim);
				}
			}
		}
		
		
		
		return simMap;
	}
	
	static public Map<String, Map<String, Double>> getSimMap(Map<String, Map<String, String>> userMap){
		
		Map<String, Map<String, Double>> simMap = new HashMap<String, Map<String, Double>>();
		
		Iterator<String> usrIt = userMap.keySet().iterator();
		ArrayList<String> userArray = new ArrayList<String>();
		while(usrIt.hasNext()){
			userArray.add(usrIt.next());
		}
		
		int len = userArray.size();
		for(int i=0;i<len;i++){
			
			//用户X的trainMap和userName
			Map<String, String> usr1 = userMap.get(userArray.get(i));
			String usrName1 = userArray.get(i);
			
			for(int j=0;j<len;j++){
				if(i!=j){
					Map<String, String> usr2 = userMap.get(userArray.get(j));
					String usrName2 = userArray.get(j);
					Map<String, Double> usrNSim;
					if(simMap.containsKey(usrName1)){
						usrNSim = simMap.get(usrName1);
					}
					else{
						usrNSim = new HashMap<String, Double>();
						simMap.put(usrName1, usrNSim);
					}
					double sim = personSimilarity(usr1,usr2);
//					if(sim==0){
//						System.out.println(usrName1+" 不像 "+usrName2);
//						System.out.println("personSimilarity:\t"+sim);
//					}
					usrNSim.put(usrName2, sim);
				}
			}
		}
		
		
		
		return simMap;
	}
	
	
	
	static public double getAverage2(Map<String,Double> usr){
		Iterator<Double> vIt1 = usr.values().iterator();
		double rSum1 = 0;
		int count1 = 0;
		while(vIt1.hasNext()){
			
			rSum1 += vIt1.next();
			count1++;
		}
		return rSum1/count1;
	}
	
	static public double getAverage(Map<String,String> usr){
		
		/**
		 * 解决训练集不存在的情况
		 */
		if(usr==null)
			return 3;
		
		Iterator<String> vIt1 = usr.values().iterator();
		double rSum1 = 0;
		int count1 = 0;
		while(vIt1.hasNext()){
			
			rSum1 += Integer.valueOf(vIt1.next());
			count1++;
		}
//		System.out.println(rSum1/count1);
		return rSum1/count1;
	}
	
	static public double personSimilarity(Map<String,String> usr1, Map<String,String> usr2){
		
		/**
		 * 注意，这里采用的算法的平均值是全集的，并非是交集。
		 * 应当另外测试交集平均值的。
		 */
		
		//计算第一个人的rating平均数
		Iterator<String> vIt1 = usr1.values().iterator();
		double rSum1 = 0;
		int count1 = 0;
		while(vIt1.hasNext()){
			
			rSum1 += Integer.valueOf(vIt1.next());
			count1++;
		}
		double rA1 = rSum1/count1;
//		System.out.println("rA1:"+rA1);
		
		
		//计算第二个人的rating和平均数
		Iterator<String> vIt2 = usr2.values().iterator();
		double rSum2 = 0;
		int count2 = 0;
		while(vIt2.hasNext()){
			
			rSum2 += Integer.valueOf(vIt2.next());
			count2++;
		}
		double rA2 = rSum2/count2;
//		System.out.println("rA2:"+rA2);

		//皮尔逊相似度的三个中间sigma求和量
		double sigmaxy = 0, sigmax = 0, sigmay = 0;
		//以其中一人开始遍历
		Iterator<String> it = usr1.keySet().iterator();
		int count = 0;
		while(it.hasNext()){
			
			String mv = it.next();
			if(usr2.containsKey(mv)){
				count++;
				//如果都评价过这个电影
				double r1 = Double.valueOf(usr1.get(mv));
				double r2 = Double.valueOf(usr2.get(mv));
//				System.out.println("r1:"+r1);
//				System.out.println("r2:"+r2);
				sigmaxy+=(r1-rA1)*(r2-rA2);
				sigmax+=(r1-rA1)*(r1-rA1);
				sigmay+=(r2-rA2)*(r2-rA2);
			}
		}
		
//		System.out.println("sigmax:"+sigmax);
//		System.out.println("sigmay:"+sigmay);
//		System.out.println("sigmaxy:"+sigmaxy);
//		System.out.println("-------------------");
		
		double psim = sigmaxy/(Math.sqrt(sigmax)*Math.sqrt(sigmay));
		if(Double.isNaN(psim)){
			//用户不相似
			return 0;
		}
//		double jaccard = count/(usr1.size()+usr2.size()-count);
//		System.out.println("psim: "+psim);
		return psim*Math.pow(count,2.5045);
	}
	
	static public double personSimilarity2(Map<String,String> usr1, Map<String,String> usr2){
		
		Iterator<String> it0 = usr1.keySet().iterator();
		int count0 = 0;
		double ar1 = 0, ar2 = 0;
		while(it0.hasNext()){
			
			String mv = it0.next();
			if(usr2.containsKey(mv)){
				count0++;
				//如果都评价过这个电影
				ar1 += Double.valueOf(usr1.get(mv));
				ar2 += Double.valueOf(usr2.get(mv));
			}
		}
		
		ar1 = ar1/count0;
		ar2 = ar2/count0;
		
		if(count0==0){
			ar1 = getAverage(usr1);
			ar2 = getAverage(usr2);
		}
			
		
//		System.out.println("ar1: "+ar1);
//		System.out.println("ar2: "+ar2);

		//皮尔逊相似度的三个中间sigma和量
		double sigmaxy = 0, sigmax = 0, sigmay = 0;
		//以其中一人开始遍历
		Iterator<String> it = usr1.keySet().iterator();
		int count = 0;
		while(it.hasNext()){
			
			String mv = it.next();
			if(usr2.containsKey(mv)){
				count++;
				//如果都评价过这个电影
				double r1 = Double.valueOf(usr1.get(mv));
				double r2 = Double.valueOf(usr2.get(mv));
//				System.out.println("r1:"+r1);
//				System.out.println("r2:"+r2);
				sigmaxy+=(r1-ar1)*(r2-ar2);
				sigmax+=(r1-ar1)*(r1-ar1);
				sigmay+=(r2-ar2)*(r2-ar2);
			}
		}
//		System.out.println("sigmax:"+sigmax);
//		System.out.println("sigmay:"+sigmay);
//		System.out.println("sigmaxy:"+sigmaxy);
//		System.out.println("-------------------");
		double psim = sigmaxy/(Math.sqrt(sigmax)*Math.sqrt(sigmay));
		if(Double.isNaN(psim)){
//			System.out.println("用户不相似");
			return 0;
		}
//		if(psim>1||psim<-1)
//			System.out.println("error psim:\t"+psim);
		return psim;
	}
}
