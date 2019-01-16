package com.bec.reporting.tests;

import java.util.Properties;
import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;
import cucumber.api.Scenario;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features",
				glue = { "com.bec.reporting.steps" },
				plugin = { "pretty",
		"html:target/cucumber-reports",
		//"com.bec.reporting.ExtentCucumberFormatter:target/cucumber-reports/extent_report/report.html",
		//"json:target/cucumber-reports/json_report/Cucumber.json",
		//"junit:target/cucumber-reports/junit_xml_report/Cucumber.xml" 
						 }, 
				tags = { "@Feature" }, 
				monochrome = true)

public class TestRunner {
	public static Properties config;
	public static Scenario scenario;
}
