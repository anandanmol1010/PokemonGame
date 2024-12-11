package com.example.pokemongame;

import com.google.gson.annotations.SerializedName;

public class StatsItem{

//	@SerializedName("stat")
//	private Stat stat;

	@SerializedName("base_stat")
	private int baseStat;

	@SerializedName("effort")
	private int effort;

//	public void setStat(Stat stat){
//		this.stat = stat;
//	}

//	public Stat getStat(){
//		return stat;
//	}

	public void setBaseStat(int baseStat){
		this.baseStat = baseStat;
	}

	public int getBaseStat(){
		return baseStat;
	}

	public void setEffort(int effort){
		this.effort = effort;
	}

	public int getEffort(){
		return effort;
	}
}