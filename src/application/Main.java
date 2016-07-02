package application;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

import config.ConfigFactory;
import i18n.I18n;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import tools.LogFormater;
import view.MainLayoutController;
import z21Drive.Z21;

public class Main extends Application {
	private Scene scene;
	private MainLayoutController controller;
	private Logger logger = Logger.getLogger(getClass().getSimpleName());

	@Override
	public void start(Stage primaryStage) {
		try {
			Platform.setImplicitExit(true);
			logger.info("Loading Configs");
			ConfigFactory c = ConfigFactory.instance;
			logger.info("Starting Z21");
			Z21 z = Z21.instance;
			logger.info("Starting MainLayout");
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getClassLoader().getResource("view/MainLayout.fxml"));
			loader.setResources(I18n.getBundle());
			BorderPane mainLayout = (BorderPane) loader.load();
			controller = loader.getController();
			controller.setMainApp(this);

			scene = new Scene(mainLayout, 900, 600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setTitle(I18n.getMsg("title"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		enableLoggin();
		launch(args);
	}

	private static void enableLoggin() {
		try {
			Logger rootLogger = Logger.getLogger("");
			FileHandler logHandler = new FileHandler("decoder.log", 10 * 1024 * 1024, 1, false);
			logHandler.setFormatter(new LogFormater());
			logHandler.setLevel(Level.INFO);

			rootLogger.removeHandler(rootLogger.getHandlers()[0]);
			rootLogger.setLevel(Level.ALL);
			rootLogger.addHandler(logHandler);
			rootLogger.addHandler(new StreamHandler(System.out, new LogFormater()) {
				@Override
				public synchronized void publish(final LogRecord record) {
					super.publish(record);
					flush();
				}
			});
		} catch (SecurityException | IOException e) {
			e.printStackTrace();
		}
	}

	public Scene getScene() {
		return scene;
	}

	@Override
	public void stop() throws Exception {
		controller.shutDown();
		super.stop();
		System.out.println("STOP");
	}

}
