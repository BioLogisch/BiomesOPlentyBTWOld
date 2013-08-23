package net.minecraft.src.betterore.common.config;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.ParserConfigurationException;

import net.minecraft.src.ChunkProviderEnd;
import net.minecraft.src.ChunkProviderFlat;
import net.minecraft.src.ChunkProviderGenerate;
import net.minecraft.src.ChunkProviderHell;
import net.minecraft.src.IChunkProvider;
import net.minecraft.src.SaveHandler;
import net.minecraft.src.World;
import net.minecraft.src.WorldInfo;
import net.minecraft.src.betterore.common.config.parser.BOConfigParser;
import net.minecraft.src.betterore.common.generator.IBOOreDistribution;
import net.minecraft.src.betterore.common.util.BOBiomeDescriptor;
import net.minecraft.src.betterore.common.util.BOCIStringMap;
import net.minecraft.src.betterore.common.util.BOFileUtils;
import net.minecraft.src.betterore.common.util.BOLogger;
import net.minecraft.src.betterore.common.util.BOMapCollection;
import net.minecraft.src.betterore.common.util.BOPropertyIO;
import net.minecraft.src.betterore.util.BOErrorHandler;

import org.xml.sax.SAXException;



public class BOWorldConfig
{
	public static Collection<BOConfigOption>[] loadedOptionOverrides = new Collection[3];
	public final World world;
	public final WorldInfo worldInfo;
	public final File globalConfigDir;
	public final File worldBaseDir;
	public final File dimensionDir;
	public int deferredPopulationRange;
	public boolean debuggingMode;
	public boolean vanillaOreGen;
	private Collection<IBOOreDistribution> oreDistributions;
	private Map<String,BOConfigOption> configOptions;
	private Map loadedOptions;
	private Map<String,Integer> worldProperties;
	private Map cogSymbolData;
	private Collection<BOBiomeDescriptor> biomeSets;

	private static Map _worldConfigs = new HashMap();


	public static BOWorldConfig getWorldConfig(World world)
	{
		BOWorldConfig cfg = (BOWorldConfig)_worldConfigs.get(world);

		while (cfg == null)
		{
			try
			{
				cfg = new BOWorldConfig(world);
				validateOptions(cfg.getConfigOptions(), true);
				validateDistributions(cfg.getOreDistributions(), true);
			}
			catch (Exception var4)
			{
				if (BOErrorHandler.onConfigError(var4))
				{
					cfg = null;
					continue;
				}

				cfg = BOWorldConfig.createEmptyConfig();
			}

			_worldConfigs.put(world, cfg);
		}

		return cfg;
	}

	

	public static void clearWorldConfig(World world)
	{
		_worldConfigs.remove(world);
	}
	
	public static void clearWorldConfigs()
	{
		_worldConfigs.clear();;
	}

	public static void validateDistributions(Collection distributions, boolean cull) throws IllegalStateException
	{
		Iterator it = distributions.iterator();

		while (it.hasNext())
		{
			IBOOreDistribution dist = (IBOOreDistribution)it.next();

			if (!dist.validate() && cull)
			{
				it.remove();
			}
		}
	}

	public static void validateOptions(Collection options, boolean cull)
	{
		Iterator it = options.iterator();

		while (it.hasNext())
		{
			BOConfigOption option = (BOConfigOption)it.next();

			if (cull && option instanceof BOConfigOption.DisplayGroup)
			{
				it.remove();
			}
		}
	}

	public static BOWorldConfig createEmptyConfig()
	{
		try
		{
			return new BOWorldConfig((File)null, (WorldInfo)null, (File)null, (World)null, (File)null);
		}
		catch (Exception var1)
		{
			throw new RuntimeException(var1);
		}
	}

	public BOWorldConfig() throws IOException, ParserConfigurationException, SAXException
	{
		this(BOFileUtils.getConfigDir(), (WorldInfo)null, (File)null, (World)null, (File)null);
	}

	public BOWorldConfig(WorldInfo worldInfo, File worldBaseDir) throws IOException, ParserConfigurationException, SAXException
	{
		this(BOFileUtils.getConfigDir(), worldInfo, worldBaseDir, (World)null, (File)null);
	}

	public BOWorldConfig(World world) throws IOException, ParserConfigurationException, SAXException
	{
		this(BOFileUtils.getConfigDir(), (WorldInfo)world.getWorldInfo(), (File)null, world, (File)null);
	}

	private BOWorldConfig(File globalConfigDir, WorldInfo worldInfo, File worldBaseDir, World world, File dimensionDir) throws IOException, ParserConfigurationException, SAXException
	{
		BOLogger.log.fine("Create WorldConfig : " + worldBaseDir);
		this.deferredPopulationRange = 0;
		this.debuggingMode = false;
		this.vanillaOreGen = false;
		this.oreDistributions = new LinkedList();
		this.configOptions = new BOCIStringMap(new LinkedHashMap());
		this.loadedOptions = new BOCIStringMap(new LinkedHashMap());
		this.worldProperties = new BOCIStringMap(new LinkedHashMap());
		this.cogSymbolData = new BOCIStringMap(new LinkedHashMap());
		this.biomeSets = new LinkedList();
		String configFile;

		if (world != null)
		{
			if (world.getSaveHandler() != null && world.getSaveHandler() instanceof SaveHandler)
			{
				worldBaseDir = ((SaveHandler)world.getSaveHandler()).getWorldDirectory();
			}
			else
			{
				worldBaseDir = null;
			}
			configFile = world.provider.dimensionId == 0 ? null : "DIM" + world.provider.dimensionId;
			BOLogger.log.fine("Create WorldConfigfrom world : " + configFile);


			if (configFile == null)
			{
				dimensionDir = worldBaseDir;
			}
			else if (worldBaseDir == null)
			{
				dimensionDir = new File(configFile);
			}
			else
			{
				dimensionDir = new File(worldBaseDir, configFile);
			}

			worldInfo = world.getWorldInfo();
		}

		this.world = world;
		this.worldInfo = worldInfo;
		populateWorldProperties(this.worldProperties, world, worldInfo);
		this.worldBaseDir = worldBaseDir;
		this.dimensionDir = dimensionDir;
		this.globalConfigDir = globalConfigDir;

		if (dimensionDir != null)
		{
			BOLogger.log.finer("Loading config data for dimension \'" + dimensionDir + "\' ...");
		}
		else if (worldBaseDir != null)
		{
			BOLogger.log.finer("Loading config data for world \'" + worldBaseDir + "\' ...");
		}
		else
		{
			if (globalConfigDir == null)
			{
				return;
			}

			BOLogger.log.finer("Loading global config \'" + globalConfigDir + "\' ...");
		}

		configFile = null;
		File[] configFileList = new File[3];
		int configFileDepth = this.buildFileList("CustomOreGen_Config.xml", configFileList);

		if (configFileDepth < 0)
		{
			if (dimensionDir != null)
			{
				BOLogger.log.warning("No config file found for dimension \'" + dimensionDir + "\' at any scope!");
			}
			else if (worldBaseDir != null)
			{
				BOLogger.log.finer("No config file found for world \'" + worldBaseDir + "\' at any scope.");
			}
			else
			{
				BOLogger.log.finer("No global config file found.");
			}
		}
		else
		{
			File var16 = configFileList[configFileDepth];
			File[] optionsFileList = new File[3];
			int optionsFileDepth = this.buildFileList("CustomOreGen_Options.txt", optionsFileList);
			File optionsFile = optionsFileList[Math.max(Math.max(1, configFileDepth), optionsFileDepth)];
			BOConfigOption vangen;

			for (int defpopOption = configFileDepth; defpopOption < optionsFileList.length; ++defpopOption)
			{
				if (optionsFileList[defpopOption] != null && optionsFileList[defpopOption].exists())
				{
					BOPropertyIO.load(this.loadedOptions, new FileInputStream(optionsFileList[defpopOption]));
				}

				if (loadedOptionOverrides[defpopOption] != null)
				{
					Iterator dbgmd = loadedOptionOverrides[defpopOption].iterator();

					while (dbgmd.hasNext())
					{
						vangen = (BOConfigOption)dbgmd.next();

						if (vangen.getValue() != null)
						{
							this.loadedOptions.put(vangen.getName(), vangen.getValue().toString());
						}
					}
				}
			}

			(new BOConfigParser(this)).parseFile(var16);
			BOConfigOption var20;

			if (optionsFile != null && !optionsFile.exists())
			{
				for (BOConfigOption option : this.configOptions.values()) {
					if (option.getValue() != null) {
						this.loadedOptions.put(option.getName(), option.getValue().toString());
					}
				}

				optionsFile.createNewFile();
				String var19 = "CustomOreGen @VERSION@ Config Options";
				BOPropertyIO.save(this.loadedOptions, new FileOutputStream(optionsFile), var19);
			}

			BOConfigOption var21 = (BOConfigOption)this.configOptions.get("deferredPopulationRange");

			if (var21 != null && var21 instanceof BONumericOption)
			{
				Double var18 = (Double)var21.getValue();
				this.deferredPopulationRange = var18 != null && var18.doubleValue() > 0.0D ? (int)Math.ceil(var18.doubleValue()) : 0;
			}
			else
			{
				BOLogger.log.warning("Numeric Option \'" + var21 + "\' not found in config file - defaulting to \'" + this.deferredPopulationRange + "\'.");
			}

			var20 = (BOConfigOption)this.configOptions.get("debugMode");

			if (var20 != null && var20 instanceof BOChoiceOption)
			{
				String var22 = (String)var20.getValue();
				this.debuggingMode = var22 == null ? false : var22.equalsIgnoreCase("on") || var22.equalsIgnoreCase("true");
			}
			else
			{
				BOLogger.log.warning("Choice Option \'" + var20 + "\' not found in config file - defaulting to \'" + this.debuggingMode + "\'.");
			}

			vangen = (BOConfigOption)this.configOptions.get("vanillaOreGen");

			if (vangen != null && vangen instanceof BOChoiceOption)
			{
				String value = (String)vangen.getValue();
				this.vanillaOreGen = value == null ? false : value.equalsIgnoreCase("on") || value.equalsIgnoreCase("true");
			}
			else
			{
				BOLogger.log.warning("Choice Option \'" + vangen + "\' not found in config file - defaulting to \'" + this.vanillaOreGen + "\'.");
			}
		}

	}

	private int buildFileList(String fileName, File[] files)
	{
		if (files == null)
		{
			files = new File[3];
		}

		if (this.globalConfigDir != null)
		{
			files[0] = new File(this.globalConfigDir, fileName);
		}

		if (this.worldBaseDir != null)
		{
			files[1] = new File(this.worldBaseDir, fileName);
		}

		if (this.dimensionDir != null)
		{
			files[2] = new File(this.dimensionDir, fileName);
		}

		for (int i = files.length - 1; i >= 0; --i)
		{
			if (files[i] != null && files[i].exists())
			{
				return i;
			}
		}

		return -1;
	}

	private void loadOptions(File optionsFile, boolean createIfMissing) throws IOException
	{
		if (optionsFile != null)
		{
			Properties savedOptions = new Properties();

			if (optionsFile.exists())
			{
				savedOptions.load(new FileInputStream(optionsFile));
				this.loadedOptions.putAll(savedOptions);
			}
			else if (createIfMissing)
			{
				optionsFile.createNewFile();
				savedOptions.putAll(this.loadedOptions);
				String headerComment = "CustomOreGen [1.4.6]v2 Config Options";
				savedOptions.store(new FileOutputStream(optionsFile), headerComment);
			}
		}
	}

	private static void populateWorldProperties(Map properties, World world, WorldInfo worldInfo)
	{
		properties.put("world", worldInfo == null ? "" : worldInfo.getWorldName());
		properties.put("world.seed", Long.valueOf(worldInfo == null ? 0L : worldInfo.getSeed()));
		properties.put("world.version", Integer.valueOf(worldInfo == null ? 0 : worldInfo.getSaveVersion()));
		properties.put("world.isHardcore", Boolean.valueOf(worldInfo == null ? false : worldInfo.isHardcoreModeEnabled()));
		properties.put("world.hasFeatures", Boolean.valueOf(worldInfo == null ? false : worldInfo.isMapFeaturesEnabled()));
		properties.put("world.hasCheats", Boolean.valueOf(worldInfo == null ? false : worldInfo.areCommandsAllowed()));
		properties.put("world.gameMode", worldInfo == null ? "" : worldInfo.getGameType().getName());
		properties.put("world.gameMode.id", Integer.valueOf(worldInfo == null ? 0 : worldInfo.getGameType().getID()));
		properties.put("world.type", worldInfo == null ? "" : worldInfo.getTerrainType().getWorldTypeName());
		properties.put("world.type.version", Integer.valueOf(worldInfo == null ? 0 : worldInfo.getTerrainType().getGeneratorVersion()));
		String genName = "RandomLevelSource";
		String genClass = "ChunkProviderGenerate";

		if (world != null)
		{
			IChunkProvider chunkProvider = world.provider.createChunkGenerator();
			genName = chunkProvider.makeString();
			genClass = chunkProvider.getClass().getSimpleName();

			if (chunkProvider instanceof ChunkProviderGenerate)
			{
				genClass = "ChunkProviderGenerate";
			}
			else if (chunkProvider instanceof ChunkProviderFlat)
			{
				genClass = "ChunkProviderFlat";
			}
			else if (chunkProvider instanceof ChunkProviderHell)
			{
				genClass = "ChunkProviderHell";
			}
			else if (chunkProvider instanceof ChunkProviderEnd)
			{
				genName = "EndRandomLevelSource";
				genClass = "ChunkProviderEnd";
			}
		}

		properties.put("dimension.generator", genName);
		properties.put("dimension.generator.class", genClass);
		properties.put("dimension", world == null ? "" : world.provider.getDimensionName());
		properties.put("dimension.id", Integer.valueOf(world == null ? 0 : world.provider.dimensionId));
		properties.put("dimension.isSurface", Boolean.valueOf(world == null ? false : world.provider.isSurfaceWorld()));
		properties.put("dimension.groundLevel", Integer.valueOf(world == null ? 0 : world.provider.getAverageGroundLevel()));
		properties.put("dimension.height", Integer.valueOf(world == null ? 0 : world.getHeight()));
		properties.put("age", Boolean.FALSE);

	}

	public Collection<IBOOreDistribution> getOreDistributions()
	{
		return this.oreDistributions;
	}

	public Collection<IBOOreDistribution> getOreDistributions(String namePattern)
	{
		LinkedList<IBOOreDistribution> matches = new LinkedList();

		if (namePattern != null)
		{
			Pattern pattern = Pattern.compile(namePattern, 2);
			Matcher matcher = pattern.matcher("");
			for (IBOOreDistribution dist : this.oreDistributions) {
				matcher.reset(dist.toString());

				if (matcher.matches())
				{
					matches.add(dist);
				}
			}
		}

		return Collections.unmodifiableCollection(matches);
	}

	public BOConfigOption getConfigOption(String optionName)
	{
		return this.configOptions.get(optionName);
	}

	public Collection<BOConfigOption> getConfigOptions()
	{
		return new BOMapCollection<String,BOConfigOption>(this.configOptions) {
			protected String getKey(BOConfigOption v)
			{
				return v.getName();
			}
		};
	}

	public Collection<BOConfigOption> getConfigOptions(String namePattern)
	{
		LinkedList<BOConfigOption> matches = new LinkedList();

		if (namePattern != null)
		{
			Pattern pattern = Pattern.compile(namePattern, 2);
			Matcher matcher = pattern.matcher("");
			for (BOConfigOption option : this.configOptions.values()) {
				matcher.reset(option.getName());

				if (matcher.matches())
				{
					matches.add(option);
				}
			}
		}

		return Collections.unmodifiableCollection(matches);
	}

	public String loadConfigOption(String optionName)
	{
		return (String)this.loadedOptions.get(optionName);
	}

	public Object getWorldProperty(String propertyName)
	{
		return this.worldProperties.get(propertyName);
	}

	public Collection<BOBiomeDescriptor> getBiomeSets() {
		return biomeSets;
	}

	public Collection<BOBiomeDescriptor> getBiomeSets(String namePattern) {
		LinkedList<BOBiomeDescriptor> matches = new LinkedList();

		if (namePattern != null)
		{
			Pattern pattern = Pattern.compile(namePattern, Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher("");
			for (BOBiomeDescriptor desc : this.biomeSets) {
				matcher.reset(desc.getName());

				if (matcher.matches())
				{
					matches.add(desc);
				}
			}
		}

		return Collections.unmodifiableCollection(matches);
	}

}
