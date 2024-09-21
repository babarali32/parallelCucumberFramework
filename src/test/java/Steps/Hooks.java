package Steps;

import io.cucumber.java.Scenario;
import io.cucumber.java.Before;
import io.cucumber.java.After;
import utils.commonMethods;
import utils.constants;

import java.io.File;
import java.nio.file.Files;

public class Hooks extends commonMethods {

    @Before
    public void start() {
        openBrowser();
    }

    @After
    public void end(Scenario scenario) {
        String screenshotPath;
        if (scenario.isFailed()) {
            screenshotPath = takeScreenshot("failed/" + scenario.getName());
        } else {
            screenshotPath = takeScreenshot("passed/" + scenario.getName());
        }

        if (screenshotPath != null) {
            try {
                byte[] screenshot = Files.readAllBytes(new File(screenshotPath).toPath());
                scenario.attach(screenshot, "image/png", scenario.getName());
            } catch (Exception e) {
                System.out.println("Failed to attach screenshot to scenario: " + e.getMessage());
                e.printStackTrace();
            }
        }
            closeBrowser();
        }
    }