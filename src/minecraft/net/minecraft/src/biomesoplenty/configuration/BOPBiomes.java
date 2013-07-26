package net.minecraft.src.biomesoplenty.configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.WorldType;
import net.minecraft.src.biomesoplenty.biomes.BiomeGenAlps;
import net.minecraft.src.biomesoplenty.biomes.BiomeGenArctic;
import net.minecraft.src.biomesoplenty.biomes.BiomeGenBirchForest;
import net.minecraft.src.biomesoplenty.biomes.BiomeGenBog;
import net.minecraft.src.biomesoplenty.biomes.BiomeGenBorealForest;
import net.minecraft.src.biomesoplenty.biomes.BiomeGenBrushland;
import net.minecraft.src.biomesoplenty.biomes.BiomeGenChaparral;
import net.minecraft.src.biomesoplenty.biomes.BiomeGenConiferousForest;
import net.minecraft.src.biomesoplenty.biomes.BiomeGenConiferousForestSnow;
import net.minecraft.src.biomesoplenty.biomes.BiomeGenDeciduousForest;
import net.minecraft.src.biomesoplenty.biomes.BiomeGenDunes;
import net.minecraft.src.biomesoplenty.biomes.BiomeGenFen;
import net.minecraft.src.biomesoplenty.biomes.BiomeGenField;
import net.minecraft.src.biomesoplenty.biomes.BiomeGenFrostForest;
import net.minecraft.src.biomesoplenty.biomes.BiomeGenGlacier;
import net.minecraft.src.biomesoplenty.biomes.BiomeGenGrassland;
import net.minecraft.src.biomesoplenty.biomes.BiomeGenGrove;
import net.minecraft.src.biomesoplenty.biomes.BiomeGenHeathland;
import net.minecraft.src.biomesoplenty.biomes.BiomeGenHighland;
import net.minecraft.src.biomesoplenty.biomes.BiomeGenJadeCliffs;
import net.minecraft.src.biomesoplenty.biomes.BiomeGenLushSwamp;
import net.minecraft.src.biomesoplenty.biomes.BiomeGenMarsh;
import net.minecraft.src.biomesoplenty.biomes.BiomeGenMeadow;
import net.minecraft.src.biomesoplenty.biomes.BiomeGenMoor;
import net.minecraft.src.biomesoplenty.biomes.BiomeGenMountain;
import net.minecraft.src.biomesoplenty.biomes.BiomeGenMysticGrove;
import net.minecraft.src.biomesoplenty.biomes.BiomeGenOminousWoods;
import net.minecraft.src.biomesoplenty.biomes.BiomeGenOriginValley;
import net.minecraft.src.biomesoplenty.biomes.BiomeGenPolar;
import net.minecraft.src.biomesoplenty.biomes.BiomeGenPrairie;
import net.minecraft.src.biomesoplenty.biomes.BiomeGenRainforest;
import net.minecraft.src.biomesoplenty.biomes.BiomeGenRedwoodForest;
import net.minecraft.src.biomesoplenty.biomes.BiomeGenSacredSprings;
import net.minecraft.src.biomesoplenty.biomes.BiomeGenSavanna;
import net.minecraft.src.biomesoplenty.biomes.BiomeGenScrubland;
import net.minecraft.src.biomesoplenty.biomes.BiomeGenShield;
import net.minecraft.src.biomesoplenty.biomes.BiomeGenShrubland;
import net.minecraft.src.biomesoplenty.biomes.BiomeGenSludgepit;
import net.minecraft.src.biomesoplenty.biomes.BiomeGenSpruceWoods;
import net.minecraft.src.biomesoplenty.biomes.BiomeGenSteppe;
import net.minecraft.src.biomesoplenty.biomes.BiomeGenTemperateRainforest;
import net.minecraft.src.biomesoplenty.biomes.BiomeGenThicket;
import net.minecraft.src.biomesoplenty.biomes.BiomeGenTimber;
import net.minecraft.src.biomesoplenty.biomes.BiomeGenTropicalRainforest;
import net.minecraft.src.biomesoplenty.biomes.BiomeGenTropics;
import net.minecraft.src.biomesoplenty.biomes.BiomeGenTundra;
import net.minecraft.src.biomesoplenty.biomes.BiomeGenVolcano;
import net.minecraft.src.biomesoplenty.biomes.BiomeGenWetland;
import net.minecraft.src.biomesoplenty.biomes.BiomeGenWoodland;
import net.minecraft.src.biomesoplenty.world.WorldTypeBOP;

public class BOPBiomes 
{
    public static final BiomeGenBase[] villageSpawnBiomes = new BiomeGenBase[] {BiomeGenBase.plains, BiomeGenBase.desert};
    
    public static final BiomeGenBase[] strongholdSpawnBiomes = new BiomeGenBase[] {BiomeGenBase.desert, BiomeGenBase.forest, BiomeGenBase.extremeHills, BiomeGenBase.swampland, BiomeGenBase.taiga, BiomeGenBase.icePlains, BiomeGenBase.iceMountains, BiomeGenBase.desertHills, BiomeGenBase.forestHills, BiomeGenBase.extremeHillsEdge, BiomeGenBase.jungle, BiomeGenBase.jungleHills};
	
	public static final BiomeGenBase alps = new BiomeGenAlps(170).setColor(353825).setBiomeName("Alps").func_76733_a(5159473).setTemperatureRainfall(0.0F, 0.0F).setMinMaxHeight(5.0F, 5.0F);
	public static final BiomeGenBase arctic = new BiomeGenArctic(171).setColor(14090235).setBiomeName("Arctic").setEnableSnow().setTemperatureRainfall(0.05F, 0.0F).setMinMaxHeight(-0.1F, 0.1F);
	public static final BiomeGenBase birchForest = new BiomeGenBirchForest(175).setColor(353825).setBiomeName("Birch Forest").func_76733_a(5159473).setTemperatureRainfall(0.4F, 0.3F);
	public static final BiomeGenBase bog = new BiomeGenBog(176).setColor(522674).setBiomeName("Bog").func_76733_a(9154376).setMinMaxHeight(-0.1F, 0.1F).setTemperatureRainfall(0.8F, 0.9F);
	public static final BiomeGenBase borealForest = new BiomeGenBorealForest(177).setColor(353825).setBiomeName("Boreal Forest").func_76733_a(5159473).setMinMaxHeight(0.0F, 1.0F).setTemperatureRainfall(0.6F, 0.7F);
	public static final BiomeGenBase brushland = new BiomeGenBrushland(178).setColor(16421912).setBiomeName("Brushland").setTemperatureRainfall(2.0F, 0.0F).setMinMaxHeight(0.0F, 0.5F);
	public static final BiomeGenBase chaparral = new BiomeGenChaparral(180).setColor(9286496).setBiomeName("Chaparral").setTemperatureRainfall(0.8F, 0.4F).setMinMaxHeight(0.3F, 0.6F);
	public static final BiomeGenBase coniferousForest = new BiomeGenConiferousForest(182).setColor(747097).setBiomeName("Coniferous Forest").func_76733_a(5159473).setTemperatureRainfall(0.5F, 0.4F).setMinMaxHeight(0.1F, 0.8F);
	public static final BiomeGenBase coniferousForestSnow = new BiomeGenConiferousForestSnow(183).setColor(14090235).setBiomeName("Snowy Coniferous Forest").setTemperatureRainfall(0.0F, 0.0F).setMinMaxHeight(0.1F, 0.7F);
	public static final BiomeGenBase deciduousForest = new BiomeGenDeciduousForest(189).setColor(353825).setBiomeName("Deciduous Forest").func_76733_a(5159473).setTemperatureRainfall(0.7F, 0.8F);
	public static final BiomeGenBase dunes = new BiomeGenDunes(190).setColor(13786898).setBiomeName("Dunes").setDisableRain().setTemperatureRainfall(2.0F, 0.0F).setMinMaxHeight(0.5F, 1.3F);
	public static final BiomeGenBase fen = new BiomeGenFen(191).setColor(9286496).setBiomeName("Fen").setTemperatureRainfall(0.4F, 0.0F).setMinMaxHeight(-0.2F, 0.1F);
	public static final BiomeGenBase field = new BiomeGenField(191).setColor(9286496).setBiomeName("Field").setTemperatureRainfall(0.4F, 0.8F).setMinMaxHeight(0.0F, 0.1F);
	public static final BiomeGenBase frostForest = new BiomeGenFrostForest(192).setColor(14090235).setBiomeName("Frost Forest").setEnableSnow().setTemperatureRainfall(0.0F, 0.0F).setMinMaxHeight(0.1F, 0.4F);
	public static final BiomeGenBase glacier = new BiomeGenGlacier(195).setColor(6316128).setBiomeName("Glacier").setEnableSnow().setMinMaxHeight(0.4F, 1.0F).setTemperatureRainfall(0.0F, 0.0F);
	public static final BiomeGenBase grassland = new BiomeGenGrassland(196).setColor(9286496).setBiomeName("Grassland").setTemperatureRainfall(0.7F, 0.7F).setMinMaxHeight(0.2F, 0.2F);
	public static final BiomeGenBase grove = new BiomeGenGrove(198).setColor(9286496).setBiomeName("Grove").setTemperatureRainfall(0.4F, 0.8F).setMinMaxHeight(0.0F, 0.1F);
	public static final BiomeGenBase heathland = new BiomeGenHeathland(199).setColor(353825).setBiomeName("Heathland").func_76733_a(5159473).setTemperatureRainfall(0.8F, 0.1F).setMinMaxHeight(0.1F, 0.3F);
	public static final BiomeGenBase highland = new BiomeGenHighland(200).setColor(6316128).setBiomeName("Highland").setMinMaxHeight(0.9F, 1.9F).setTemperatureRainfall(0.5F, 0.5F);
	//public static final BiomeGenBase icyHills = new BiomeGenIcyHills(201).setColor(14090235).setBiomeName("Icy Hills").setEnableSnow().setTemperatureRainfall(0.0F, 0.0F).setMinMaxHeight(0.1F, 0.8F);
	public static final BiomeGenBase jadeCliffs = new BiomeGenJadeCliffs(202).setColor(14090235).setBiomeName("Jade Cliffs").setTemperatureRainfall(0.5F, 0.1F).setMinMaxHeight(0.1F, 2.0F);
	public static final BiomeGenBase lushSwamp = new BiomeGenLushSwamp(204).setColor(522674).setBiomeName("Lush Swamp").func_76733_a(9154376).setMinMaxHeight(-0.2F, 0.1F).setTemperatureRainfall(0.7F, 1.0F);
	public static final BiomeGenBase marsh = new BiomeGenMarsh(207).setColor(10486015).setBiomeName("Marsh").setMinMaxHeight(-0.5F, 0.0F).setTemperatureRainfall(0.5F, 0.9F);
	public static final BiomeGenBase meadow = new BiomeGenMeadow(208).setColor(9286496).setBiomeName("Meadow").setTemperatureRainfall(0.7F, 0.7F);
	public static final BiomeGenBase moor = new BiomeGenMoor(210).setColor(16421912).setBiomeName("Moor").setTemperatureRainfall(0.5F, 1.0F).setMinMaxHeight(0.7F, 0.8F);
	public static final BiomeGenBase mountain = new BiomeGenMountain(211).setColor(14090235).setBiomeName("Mountain").setTemperatureRainfall(0.5F, 0.1F).setMinMaxHeight(1.2F, 1.2F);
	public static final BiomeGenBase mysticGrove = new BiomeGenMysticGrove(212).setColor(353825).setBiomeName("Mystic Grove").func_76733_a(5159473).setTemperatureRainfall(0.9F, 1.0F);
	//public static final BiomeGenBase oasis = new BiomeGenOasis(217).setColor(16421912).setBiomeName("Oasis").setTemperatureRainfall(1.9F, 2.0F).setMinMaxHeight(0.3F, 0.4F);
	public static final BiomeGenBase ominousWoods = new BiomeGenOminousWoods(218).setColor(353825).setBiomeName("Ominous Woods").setDisableRain().func_76733_a(5159473).setTemperatureRainfall(0.8F, 0.9F);
	public static final BiomeGenBase originValley = new BiomeGenOriginValley(220).setColor(353825).setBiomeName("Origin Valley").func_76733_a(5159473).setTemperatureRainfall(0.7F, 0.8F).setMinMaxHeight(0.2F, 0.6F);
	public static final BiomeGenBase polar = new BiomeGenPolar(223).setColor(6316128).setBiomeName("Polar").setMinMaxHeight(-0.5F, 0.0F).setTemperatureRainfall(0.0F, 0.0F);
	public static final BiomeGenBase prairie = new BiomeGenPrairie(224).setColor(353825).setBiomeName("Prairie").func_76733_a(5159473).setTemperatureRainfall(0.9F, 0.6F).setMinMaxHeight(0.3F, 0.4F);
	public static final BiomeGenBase rainforest = new BiomeGenRainforest(226).setColor(5470985).setBiomeName("Rainforest").func_76733_a(5470985).setTemperatureRainfall(2.0F, 2.0F).setMinMaxHeight(0.7F, 1.8F);
	public static final BiomeGenBase redwoodForest = new BiomeGenRedwoodForest(227).setColor(747097).setBiomeName("Redwood Forest").func_76733_a(5159473).setTemperatureRainfall(0.8F, 0.4F).setMinMaxHeight(0.3F, 0.4F);
	public static final BiomeGenBase sacredSprings = new BiomeGenSacredSprings(228).setColor(522674).setBiomeName("Sacred Springs").func_76733_a(9154376).setMinMaxHeight(0.4F, 1.2F).setTemperatureRainfall(1.2F, 0.9F);
	public static final BiomeGenBase savanna = new BiomeGenSavanna(229).setColor(9286496).setBiomeName("Savanna").setTemperatureRainfall(1.5F, 0.1F).setMinMaxHeight(0.3F, 0.4F);
	public static final BiomeGenBase scrubland = new BiomeGenScrubland(230).setColor(9286496).setBiomeName("Scrubland").setTemperatureRainfall(1.2F, 0.0F).setMinMaxHeight(0.3F, 0.5F);
	public static final BiomeGenBase shield = new BiomeGenShield(232).setColor(522674).setBiomeName("Shield").func_76733_a(9154376).setMinMaxHeight(0.1F, 0.4F).setTemperatureRainfall(0.5F, 0.8F);
	public static final BiomeGenBase shrubland = new BiomeGenShrubland(234).setColor(9286496).setBiomeName("Shrubland").setMinMaxHeight(0.3F, 0.4F).setTemperatureRainfall(0.6F, 0.0F);
	public static final BiomeGenBase sludgepit = new BiomeGenSludgepit(235).setColor(522674).setBiomeName("Sludgepit").func_76733_a(9154376).setMinMaxHeight(0.1F, 0.3F).setTemperatureRainfall(0.8F, 0.9F);
	public static final BiomeGenBase spruceWoods = new BiomeGenSpruceWoods(236).setColor(353825).setBiomeName("Spruce Woods").func_76733_a(5159473).setTemperatureRainfall(0.6F, 0.7F);
	public static final BiomeGenBase steppe = new BiomeGenSteppe(237).setColor(9286496).setBiomeName("Steppe").setTemperatureRainfall(2.0F, 0.0F).setMinMaxHeight(0.3F, 0.4F);
	public static final BiomeGenBase temperateRainforest =new BiomeGenTemperateRainforest(238).setColor(353825).setBiomeName("Temperate Rainforest").func_76733_a(5159473).setTemperatureRainfall(0.7F, 0.8F).setMinMaxHeight(0.2F, 1.2F);
	public static final BiomeGenBase thicket = new BiomeGenThicket(239).setColor(353825).setBiomeName("Thicket").func_76733_a(5159473).setTemperatureRainfall(0.6F, 0.2F).setMinMaxHeight(0.2F, 0.2F);
	public static final BiomeGenBase timber = new BiomeGenTimber(240).setColor(353825).setBiomeName("Timber").func_76733_a(5159473).setTemperatureRainfall(0.7F, 0.8F).setMinMaxHeight(0.3F, 0.4F);
	public static final BiomeGenBase tropicalRainforest = new BiomeGenTropicalRainforest(241).setColor(9286496).setBiomeName("Tropical Rainforest").setTemperatureRainfall(1.2F, 0.9F).setMinMaxHeight(0.3F, 0.7F);
	public static final BiomeGenBase tropics = new BiomeGenTropics(242).setColor(9286496).setBiomeName("Tropics").setTemperatureRainfall(2.0F, 2.0F).setMinMaxHeight(0.1F, 1.5F);
	public static final BiomeGenBase tundra = new BiomeGenTundra(243).setColor(14090235).setBiomeName("Tundra").setTemperatureRainfall(0.2F, 0.8F).setMinMaxHeight(0.2F, 0.4F);
	public static final BiomeGenBase volcano = new BiomeGenVolcano(244).setColor(9286496).setBiomeName("Volcano").setDisableRain().setMinMaxHeight(0.6F, 0.9F).setTemperatureRainfall(2.0F, 0.0F);
	public static final BiomeGenBase wetland = new BiomeGenWetland(246).setColor(522674).setBiomeName("Wetland").func_76733_a(9154376).setMinMaxHeight(-0.2F, 0.4F).setTemperatureRainfall(0.8F, 0.9F);
	public static final BiomeGenBase woodland = new BiomeGenWoodland(247).setColor(353825).setBiomeName("Woodland").func_76733_a(5159473).setTemperatureRainfall(1.7F, 0.2F).setMinMaxHeight(0.3F, 0.4F);
    
    public static final ArrayList<BiomeGenBase> bopBiomes = new ArrayList<BiomeGenBase>();
    
    public static final BiomeGenBase[] bopVillageSpawnBiomes = new BiomeGenBase[] {alps, arctic, birchForest, bog, borealForest, brushland, chaparral, coniferousForest, coniferousForestSnow, deciduousForest, dunes, fen, field, frostForest, glacier, grassland, grove, heathland, highland, /*icyHills,*/ jadeCliffs, lushSwamp, marsh, meadow, moor, mountain, mysticGrove, /*oasis,*/ ominousWoods, originValley, polar, prairie, rainforest, redwoodForest, sacredSprings, savanna, scrubland, shield, shrubland, sludgepit, spruceWoods, steppe, temperateRainforest, thicket, timber, tropicalRainforest, tropics, tundra, wetland, woodland };
    
    public static final BiomeGenBase[] bopStrongholdSpawnBiomes = new BiomeGenBase[] {alps, arctic, birchForest, bog, borealForest, brushland, chaparral, coniferousForest, coniferousForestSnow, deciduousForest, dunes, fen, field, frostForest, glacier, grassland, grove, heathland, highland, /*icyHills,*/ jadeCliffs, lushSwamp, marsh, meadow, moor, mountain, mysticGrove, /*oasis,*/ ominousWoods, originValley, polar, prairie, rainforest, redwoodForest, sacredSprings, savanna, scrubland, shield, shrubland, sludgepit, spruceWoods, steppe, temperateRainforest, thicket, timber, tropicalRainforest, tropics, tundra, volcano, wetland, woodland };
    
    public BOPBiomes(WorldType worldtype)
    {
    }

	public static void init()
	{
		bopBiomes.add(alps);
		bopBiomes.add(arctic);
		bopBiomes.add(birchForest);
		bopBiomes.add(bog);
		bopBiomes.add(borealForest);
		bopBiomes.add(brushland);
		bopBiomes.add(chaparral);
		bopBiomes.add(coniferousForest);
		bopBiomes.add(coniferousForestSnow);
		bopBiomes.add(deciduousForest);
		bopBiomes.add(dunes);
		bopBiomes.add(fen);
		bopBiomes.add(field);
		bopBiomes.add(frostForest);
		bopBiomes.add(glacier);
		bopBiomes.add(grassland);
		bopBiomes.add(grove);
		bopBiomes.add(heathland);
		bopBiomes.add(highland);
		//bopBiomes.add(icyHills);
		bopBiomes.add(jadeCliffs);
		bopBiomes.add(lushSwamp);
		bopBiomes.add(marsh);
		bopBiomes.add(meadow);
		bopBiomes.add(moor);
		bopBiomes.add(mountain);
		bopBiomes.add(mysticGrove);
		//bopBiomes.add(oasis);
		bopBiomes.add(ominousWoods);
		bopBiomes.add(originValley);
		bopBiomes.add(polar);
		bopBiomes.add(prairie);
		bopBiomes.add(rainforest);
		bopBiomes.add(redwoodForest);
		bopBiomes.add(sacredSprings);
		bopBiomes.add(savanna);
		bopBiomes.add(scrubland);
		bopBiomes.add(shield);
		bopBiomes.add(shrubland);
		bopBiomes.add(sludgepit);
		bopBiomes.add(spruceWoods);
		bopBiomes.add(steppe);
		bopBiomes.add(temperateRainforest);
		bopBiomes.add(thicket);
		bopBiomes.add(timber);
		bopBiomes.add(tropicalRainforest);
		bopBiomes.add(tropics);
		bopBiomes.add(tundra);
		bopBiomes.add(volcano);
		bopBiomes.add(wetland);
		bopBiomes.add(woodland);
	}
	
    public static ArrayList<BiomeGenBase> getBiomesForWorldType() 
    {
        return bopBiomes;
    }
    
    public static BiomeGenBase[] getVillageSpawnBiomes()
    {
        Set<BiomeGenBase> newBiomesForWorld = new HashSet(Arrays.asList(villageSpawnBiomes));
        
        for (BiomeGenBase biome : bopVillageSpawnBiomes)
        {
     	   newBiomesForWorld.add(biome);
        }
        
        return newBiomesForWorld.toArray(new BiomeGenBase[0]);
    }
    
    public static BiomeGenBase[] getStrongholdSpawnBiomes()
    {
        Set<BiomeGenBase> newBiomesForWorld = new HashSet(Arrays.asList(strongholdSpawnBiomes));
        
        for (BiomeGenBase biome : bopStrongholdSpawnBiomes)
        {
     	   newBiomesForWorld.add(biome);
        }
        
        return newBiomesForWorld.toArray(new BiomeGenBase[0]);
    }
}
