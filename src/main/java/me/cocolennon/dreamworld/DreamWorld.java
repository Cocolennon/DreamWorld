package me.cocolennon.dreamworld;

import me.cocolennon.dreamworld.blocks.ModBlocks;
import me.cocolennon.dreamworld.command.DreamWorldCommand;
import me.cocolennon.dreamworld.entities.ModEntityTypes;
import me.cocolennon.dreamworld.items.ModCreativeTabs;
import me.cocolennon.dreamworld.worldgen.features.ModFeatures;
import me.cocolennon.dreamworld.items.ModItems;
import me.cocolennon.dreamworld.util.SleepHandler;
import me.cocolennon.dreamworld.worldgen.structures.ModStructures;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.resources.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DreamWorld implements ModInitializer {
	public static final String MOD_ID = "dreamworld";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing Dream World");
		ModEntityTypes.initialize();
		ModCreativeTabs.initialize();
		ModBlocks.initialize();
		ModItems.initialize();
		ModFeatures.initialize();
		ModStructures.initialize();
		SleepHandler.initialize();
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			DreamWorldCommand.register(dispatcher);
		});
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
