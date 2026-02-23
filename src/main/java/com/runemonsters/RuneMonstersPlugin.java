package com.runemonsters;

import com.google.inject.Provides;
import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.chat.QueuedMessage;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.EventBus;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.util.ImageUtil;

import java.awt.image.BufferedImage;
import java.io.File;

import com.runemonsters.swing.RuneMonstersPanel;

@Slf4j
@PluginDescriptor(
	name = "RuneMonsters"
)
public class RuneMonstersPlugin extends Plugin
{
	public static final String DATA_DIRECTORY = System.getProperty("user.home") + "\\.runelite" + "\\runemonsters\\";

	@Inject
	private Client client;

	@Inject
	private RuneMonstersConfig config;

	@Inject
	private ClientToolbar clientToolbar;

	@Inject
	private EventBus eventBus;

	@Inject
	private ChatMessageManager chatMessageManager;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private ConfigManager configManager;

	@Inject
	private CardDrop cardDrop;

	@Inject
	private RuneMonstersPanel runeMonstersPanel;
	private NavigationButton navigationButton;

	@Override
	protected void startUp() throws Exception
	{
		log.debug("Example started!");


		CardUtilities.loadCardsFromFile();
		CardUtilities.loadUnlockedCardsFromFile();

		runeMonstersPanel = injector.getInstance(RuneMonstersPanel.class);
		final BufferedImage icon = ImageUtil.loadImageResource(getClass(), "/RuneMonstersPanelIcon.png");
		navigationButton = NavigationButton.builder()
				.tooltip("RuneMonsters")
				.icon(icon)
				.priority(10)
				.panel(runeMonstersPanel)
				.build();

		clientToolbar.addNavigation(navigationButton);

		eventBus.register(cardDrop);

		File directory = new File(DATA_DIRECTORY);
		if (!directory.exists()) {
			directory.mkdirs(); // Create the directory if it doesn't exist
		}
		//overlayManager.add(cardBrowserOverlayPanel);
	}

	@Override
	protected void shutDown() throws Exception
	{
		log.debug("Example stopped!");
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{
		//if (gameStateChanged.getGameState() == GameState.LOGGED_IN)
		//{
		//	client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "Example says " + config.greeting(), null);
		//}
	}

	@Provides
	RuneMonstersConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(RuneMonstersConfig.class);
	}

	public void sendCardMessage(String message) {
		chatMessageManager.queue(
				QueuedMessage.builder()
						.type(ChatMessageType.CONSOLE)
						.runeLiteFormattedMessage(message)
						.build()
		);
	}
}
