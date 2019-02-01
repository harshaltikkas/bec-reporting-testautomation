# Bec-Reporting-TestAutomation
Project to automate the UI testcases of BEC-Universe-Reporting 

<img src="https://bec-reporting.cf/img/benchmark_logo_2.e0a765418e2a8f64b17c0d3f7c35ded8.png" />
_______________________________________

## 1. Download the project.

In order to start using the project you need to create your own Fork on Github and then clone the project:

```bash
git clone https://github.com/harshaltikkas/bec-reporting-testautomation.git
```

## 2.Setup
* Check configuration.properties for browsers and their os and versions to be included for the test 
* Set environment variables with your [CrossBrowser Username and Access Key] in configuration.properties

## 3.Running your tests
* To run a single test, run `mvn install -Dbrowser=<BROWSER>`
* To run particular test in multiple in parallel browsers, run `make -j 2 test_firefox test_ie .... n`
* To run parallel all tests, run `make run_all_in_parallel`


## Notes
* You need to provide webdrivers of individual browser on whome you want to execute test cases depend upon different OS.
* You need to configure "Make" i.e. "make-3.81-bin" on your machine.
* Need to set classpath for maven,make till bin(folder).
* `make -j 2 test_firefox test_ie .... n` , make -j 2 saying two instances of browser will launch (firefox & ie).
* You can view your test results on the [CrossBrowser Automate dashboard](https://app.crossbrowsertesting.com/selenium?tab=recent)
