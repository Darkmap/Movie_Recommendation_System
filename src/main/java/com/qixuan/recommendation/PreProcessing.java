package com.qixuan.recommendation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class PreProcessing {
	
	
	public static void main(String[] args) {
		
		
		Map<String, Map<String, String>> users = getUserMap("test.txt");
		
		Iterator<Map<String, String>> uit = users.values().iterator();
		while(uit.hasNext()){
			
//			Iterator<String> it = uit.next().values().iterator();
			System.out.println(uit.next().size());
//			while(it.hasNext())
//				System.out.println(it.next());
		}
		
//		System.out.println(users.size());
	}
	static public Map<String, Map<String, String>> getUserMap2(String filename){
		
		//用户集合
		Map<String,Map<String,String>> users = new HashMap<String, Map<String,String>>();
		
		try {
			Scanner input = new Scanner(new File(filename));
			
			while(input.hasNext()){
				
				String line = input.nextLine();
				
				String[] userStr = line.split("\t");
				
				if(userStr.length!=4)
					continue;
				
				String usr = userStr[1];
				String mv = userStr[0];
				String rating = userStr[2];
				
				if(users.containsKey(usr)){
					//如果已经存在该用户了
					Map<String,String> user = users.get(usr);
					//如果不存在该评分
					if(!user.containsKey(mv))
						user.put(mv, rating);
					
				}
				else{
					//如果还不存在该用户
					Map<String,String> user = new HashMap<String, String>();
					user.put(mv, rating);
					users.put(usr, user);
				}
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return users;
	}
	
	
	static public Map<String, Map<String, String>> getUserMap(String filename){
		
		//用户集合
		Map<String,Map<String,String>> users = new HashMap<String, Map<String,String>>();
		
		try {
			Scanner input = new Scanner(new File(filename));
			
			while(input.hasNext()){
				
				String line = input.nextLine();
				
				String[] userStr = line.split("\t");
				
				if(userStr.length!=4)
					continue;
				
				String usr = userStr[0];
				String mv = userStr[1];
				String rating = userStr[2];
				
				if(users.containsKey(usr)){
					//如果已经存在该用户了
					Map<String,String> user = users.get(usr);
					//如果不存在该评分
					if(!user.containsKey(mv))
						user.put(mv, rating);
					
				}
				else{
					//如果还不存在该用户
					Map<String,String> user = new HashMap<String, String>();
					user.put(mv, rating);
					users.put(usr, user);
				}
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return users;
	}
}
