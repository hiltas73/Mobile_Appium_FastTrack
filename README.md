**MOBILE TESTING with APPIUM**

Here I go over the Mobile Testing with [Appium](https://appium.io/docs/en/latest/) using [BDD Cucumber](https://cucumber.io/) in [Page Object Model](https://www.selenium.dev/documentation/test_practices/encouraged/page_object_models/). 

**Appium** is an open source project and ecosystem of related software, designed to facilitate UI automation of many app platforms, including mobile (iOS, Android, Tizen), browser (Chrome, Firefox, Safari), desktop (macOS, Windows), TV (Roku, tvOS, Android TV, Samsung), and more!

**Cucumber** also defines files with all stakeholders using Behavior-Driven-Development (BDD), which makes your job easier to understand even by non-technical people.

**POM design** has some advantages in which we can separate test code from some page-specific code such as locators. Using POM, a single repository for the services or operations the page offers rather than having these services scattered throughout the tests. This also brings the advantage of easy maintenance.
In addition to local run, I also added remote run using SauceLabs with both Android and IOS devices.
