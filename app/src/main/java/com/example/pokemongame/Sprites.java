package com.example.pokemongame;

import com.google.gson.annotations.SerializedName;

public class Sprites{

	@SerializedName("back_shiny_female")
	private Object backShinyFemale;

	@SerializedName("back_female")
	private Object backFemale;

	@SerializedName("other")
	private Other other;

	@SerializedName("back_default")
	private String backDefault;

	@SerializedName("front_shiny_female")
	private Object frontShinyFemale;

	@SerializedName("front_default")
	private String frontDefault;

//	@SerializedName("versions")
//	private Versions versions;

	@SerializedName("front_female")
	private Object frontFemale;

	@SerializedName("back_shiny")
	private String backShiny;

	@SerializedName("front_shiny")
	private String frontShiny;

	public void setBackShinyFemale(Object backShinyFemale){
		this.backShinyFemale = backShinyFemale;
	}

	public Object getBackShinyFemale(){
		return backShinyFemale;
	}

	public void setBackFemale(Object backFemale){
		this.backFemale = backFemale;
	}

	public Object getBackFemale(){
		return backFemale;
	}

	public void setOther(Other other){
		this.other = other;
	}

	public Other getOther(){
		return other;
	}

	public void setBackDefault(String backDefault){
		this.backDefault = backDefault;
	}

	public String getBackDefault(){
		return backDefault;
	}

	public void setFrontShinyFemale(Object frontShinyFemale){
		this.frontShinyFemale = frontShinyFemale;
	}

	public Object getFrontShinyFemale(){
		return frontShinyFemale;
	}

	public void setFrontDefault(String frontDefault){
		this.frontDefault = frontDefault;
	}

	public String getFrontDefault(){
		return frontDefault;
	}

//	public void setVersions(Versions versions){
//		this.versions = versions;
//	}

//	public Versions getVersions(){
//		return versions;
//	}

	public void setFrontFemale(Object frontFemale){
		this.frontFemale = frontFemale;
	}

	public Object getFrontFemale(){
		return frontFemale;
	}

	public void setBackShiny(String backShiny){
		this.backShiny = backShiny;
	}

	public String getBackShiny(){
		return backShiny;
	}

	public void setFrontShiny(String frontShiny){
		this.frontShiny = frontShiny;
	}

	public String getFrontShiny(){
		return frontShiny;
	}
}