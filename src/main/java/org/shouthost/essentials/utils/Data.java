package org.shouthost.essentials.utils;

import com.google.gson.stream.JsonReader;
import org.shouthost.essentials.core.Essentials;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.shouthost.essentials.json.kits.Kit;

import java.io.*;
import java.util.ArrayList;

public class Data {
	public static void LoadKits(){
		Runnable load = new Runnable() {
			@Override
			public void run() {
				for(String file:Essentials.kits.list()){
					Gson gson = new Gson();
					if(file == null) break;
					File f = new File(Essentials.kits, file);
					if(f.exists() && f.isFile()){
						BufferedReader br = null;
						try {
							br = new BufferedReader(new FileReader(f));
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						}
						Kit newKit = gson.fromJson(br, Kit.class);
						if(!Essentials.usableKit.contains(newKit))
							Essentials.usableKit.add(newKit);
						try {
							br.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		};
		load.run();
	}


}
