package com.andrewgaming.aputils;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.andrewgaming.aputils.Constants.MOD_ID;




public class AndrewsPackUtilities {
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public void onInitialize() {
		LOGGER.info("Loaded Andrew's Pack Utilities");
	}
}

