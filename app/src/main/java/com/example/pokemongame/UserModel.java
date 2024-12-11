package com.example.pokemongame;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class UserModel{

//	@SerializedName("cries")
//	private Cries cries;

	@SerializedName("location_area_encounters")
	private String locationAreaEncounters;

//	@SerializedName("types")
//	private List<TypesItem> types;

	@SerializedName("base_experience")
	private int baseExperience;

	@SerializedName("held_items")
	private List<Object> heldItems;

	@SerializedName("weight")
	private int weight;

	@SerializedName("is_default")
	private boolean isDefault;

	@SerializedName("past_types")
	private List<Object> pastTypes;

	@SerializedName("sprites")
	private Sprites sprites;

	@SerializedName("past_abilities")
	private List<Object> pastAbilities;

//	@SerializedName("abilities")
//	private List<AbilitiesItem> abilities;

//	@SerializedName("game_indices")
//	private List<GameIndicesItem> gameIndices;

//	@SerializedName("species")
//	private Species species;

	@SerializedName("stats")
	private List<StatsItem> stats;

//	@SerializedName("moves")
//	private List<MovesItem> moves;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

//	@SerializedName("forms")
//	private List<FormsItem> forms;

	@SerializedName("height")
	private int height;

	@SerializedName("order")
	private int order;

//	public void setCries(Cries cries){
//		this.cries = cries;
//	}

//	public Cries getCries(){
//		return cries;
//	}

	public void setLocationAreaEncounters(String locationAreaEncounters){
		this.locationAreaEncounters = locationAreaEncounters;
	}

	public String getLocationAreaEncounters(){
		return locationAreaEncounters;
	}

//	public void setTypes(List<TypesItem> types){
//		this.types = types;
//	}

//	public List<TypesItem> getTypes(){
//		return types;
//	}

	public void setBaseExperience(int baseExperience){
		this.baseExperience = baseExperience;
	}

	public int getBaseExperience(){
		return baseExperience;
	}

	public void setHeldItems(List<Object> heldItems){
		this.heldItems = heldItems;
	}

	public List<Object> getHeldItems(){
		return heldItems;
	}

	public void setWeight(int weight){
		this.weight = weight;
	}

	public int getWeight(){
		return weight;
	}

	public void setIsDefault(boolean isDefault){
		this.isDefault = isDefault;
	}

	public boolean isIsDefault(){
		return isDefault;
	}

	public void setPastTypes(List<Object> pastTypes){
		this.pastTypes = pastTypes;
	}

	public List<Object> getPastTypes(){
		return pastTypes;
	}

	public void setSprites(Sprites sprites){
		this.sprites = sprites;
	}

	public Sprites getSprites(){
		return sprites;
	}

	public void setPastAbilities(List<Object> pastAbilities){
		this.pastAbilities = pastAbilities;
	}

	public List<Object> getPastAbilities(){
		return pastAbilities;
	}

//	public void setAbilities(List<AbilitiesItem> abilities){
//		this.abilities = abilities;
//	}

//	public List<AbilitiesItem> getAbilities(){
//		return abilities;
//	}

//	public void setGameIndices(List<GameIndicesItem> gameIndices){
//		this.gameIndices = gameIndices;
//	}

//	public List<GameIndicesItem> getGameIndices(){
//		return gameIndices;
//	}

//	public void setSpecies(Species species){
//		this.species = species;
//	}

//	public Species getSpecies(){
//		return species;
//	}

	public void setStats(List<StatsItem> stats){
		this.stats = stats;
	}

	public List<StatsItem> getStats(){
		return stats;
	}

//	public void setMoves(List<MovesItem> moves){
//		this.moves = moves;
//	}

//	public List<MovesItem> getMoves(){
//		return moves;
//	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

//	public void setForms(List<FormsItem> forms){
//		this.forms = forms;
//	}

//	public List<FormsItem> getForms(){
//		return forms;
//	}

	public void setHeight(int height){
		this.height = height;
	}

	public int getHeight(){
		return height;
	}

	public void setOrder(int order){
		this.order = order;
	}

	public int getOrder(){
		return order;
	}
}