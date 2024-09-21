package configparallel;


import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "Steps",
        plugin = {"pretty", "html:target/cucumber-reports.html"},
        monochrome = true,
        dryRun = false
)
public class CucumberConfiguration {
}