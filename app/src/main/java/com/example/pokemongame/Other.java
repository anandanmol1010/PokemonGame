package com.example.pokemongame;

import com.google.gson.annotations.SerializedName;

public class Other{

//	@SerializedName("dream_world")
//	private DreamWorld dreamWorld;

//	@SerializedName("showdown")
//	private Showdown showdown;

//	@SerializedName("official-artwork")
//	private OfficialArtwork officialArtwork;

	@SerializedName("home")
	private Home home;

//	public void setDreamWorld(DreamWorld dreamWorld){
//		this.dreamWorld = dreamWorld;
//	}

//	public DreamWorld getDreamWorld(){
//		return dreamWorld;
//	}

//	public void setShowdown(Showdown showdown){
//		this.showdown = showdown;
//	}

//	public Showdown getShowdown(){
//		return showdown;
//	}

//	public void setOfficialArtwork(OfficialArtwork officialArtwork){
//		this.officialArtwork = officialArtwork;
//	}

//	public OfficialArtwork getOfficialArtwork(){
//		return officialArtwork;
//	}

	public void setHome(Home home){
		this.home = home;
	}

	public Home getHome(){
		return home;
	}
}