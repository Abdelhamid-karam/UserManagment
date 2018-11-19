package UM;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty", "html:target/TestResults","json:target/TestResults/report.json", "junit:target/TestResults/junit.xml"}, features = "src/test/resources")
public class TestRunner {

}
