[![Build Status](https://travis-ci.org/RationaleEmotions/SimpleSe.svg?branch=master)](https://travis-ci.org/RationaleEmotions/SimpleSe)
# SimpleSe Page-objects

In English you would read this as **Simple _Se_**-lenium **Page Objects**.

We decided to sound a bit desi as well. So in **Hindi** you would just read this name as **Simple sey page objects**.

## What's with the name ?

Obviously we wanted to associated with **Selenium** which explains the suffix **_Se_**.

This library is called as **SimpleSe** because it attempts at providing a simple way to work with page objects.

## What's in it for me ?

Page objects as a concept has been built by different people in different ways.
This library attempts at building page objects keeping in mind the following things:

* Simple to use (try staying away from complex approaches)
* De-couple the java code from the locators so that a locator change would merely warrant a change in the 
externalised data source (JSON file in our case)
* Support localization i.e., being able to define locators for different countries and still have a unified way of 
working with the html elements.
* Support a simple way of expressing *explicit waits* in **Selenium**. E.g., would include 

   * Wait for `30 seconds` for a particular element to show up before interacting with it.
   * Wait for an additional `x seconds` for a particular element to become clickable.

## Pre-requisites

**Simple Se Page Objects** requires that you use : 
* **JDK 8**.
* **Selenium 3.141.59** or higher. 

## How to use.

**Simple Se Page Objects** is a [Maven](https://maven.apache.org/guides/getting-started/) artifact. In order to 
consume it, you merely need to add the following as a dependency in your pom file.

```xml
<dependency>
    <groupId>com.rationaleemotions</groupId>
    <artifactId>simple-se-pageobjects</artifactId>
    <version>1.0.6</version>
</dependency>
```


## The JSON Structure

At the heart of this project is the JSON file that contains all your locators information.
The intent is to define one `JSON` file per page. So if your UI automation flow has 10 pages, then effectively you 
would define *10 pages* respectively.
Here's how a typical JSON file that represents the locators could look like :

```json
{
  "name": "FooPage",
  "defaultLocale": "en_US",
  "elements": [
    {
      "name": "bar",
      "type": "button",
      "list": true,
      "locale": [
        {
          "name": "en_US",
          "locator": "xpath=//h1"
        },
        {
          "name": "en_FR",
          "locator": "xpath=//h1"
        }
      ],
      "wait": {
        "until": "visible",
        "for": 45
      }
    }
  ]
}
```

### What does each of those attributes mean ?

1. `name` - **(Mandatory)** This is a user friendly name that you would be giving to your page.
Names have to be unique so that there's no clash. 
2. `defaultLocale` - **(Mandatory)** You would provide a fall back locale here. The expectation is that there would 
be atleast one locale definition within `elements` that matches the value of `defaultLocale`. 
Typical values could be `en_US` _(which suggests that for a particular element if there are no country specific 
locators defined, then `en_US` is to be used as the fall back locale and `en_US` specific locators could be used.)_
3. `elements` - **(Mandatory)** All your individual html elements that make up your page would go here. Every element
 would have the following attributes.
    1. `name` - **(Mandatory)** This is a user friendly name that you would be giving to your html element. Here again 
  names have to be unique so that there's no clash. Typical e.g., could include `loginBtn` (or) `userNameTxtField`.
    2. `locale` - **(Mandatory)** Represents the list of countries and their corresponding locators. Its attributes are
   described as below.
       1. `name` - **(Mandatory)** The name/key of the country for which the locator is being defined.
       2. `type` - **(Optional). Defaults to `generic`** Valid values are `(button|checkbox|form|generic|image|label|link|radio|select|text)`. Helps in identifying the type of an element. Mostly useful for support tools such as code generators.
       3. `list` -  **(Optional). Defaults to `false`**. When specified and set to `true` indicates that a particular entry represents a list of elements. Mostly useful for support tools such as code generators.
       4. `locator` - **(Mandatory)** The actual locator which can be an xPath (or) css locator (or) id/name. Here's 
       some conventions associated with this :
          1. **xpath format** : Can be of the form `xpath=./input[@name='first_name']` (or) `xpath=//input[@name='first_name']` 
          (or) `xpath=/input[@name='first_name']` (or) `xpath=(//input[@class='btn'])[1]`
          (or) `./input[@name='first_name']` (or) `//input[@name='first_name']` (or) `/input[@name='first_name']`
          2. **css format** : Can be of the form `css=input[name='first_name']`
          3. **id (or) name format** : Can be of the form `first_name` { here `first_name` can either be the `id` of 
          an element (or) the `name` of an element. }
          4. **class format** : Can be of the form `class=btn`
          5. **linkText format** : Can be of the form `linkText=Checkboxes`
          6. **partialLinkText format** : Can be of the form `parialLinkText=Check`
          7. **tagName format** : Can be of the form `tagName=input`
    3. `wait` - **(Optional)** If you feel that a particular element is either a slow loading element (or) you would 
    need to have some extra waits defined, then those go here. It can have the following attributes.
       1. `until` - We currently support only one of the following values: 
          * `Available` - **(case in-sensitive)** Waits for the element to be **available** in the Dom. Its 
          functionally 
          equivalent to `ExpectedConditions#presenceOfElementLocated` (or) 
          `ExpectedConditions#presenceOfAllElementsLocatedBy`.
          * `Visible` - **(case in-sensitive)** Waits for the element to be **available** in the Dom and also to be 
          **visible**. 
          Its functionally equivalent to `ExpectedConditions#visibilityOfElementLocated` (or) `ExpectedConditions#visibilityOfAllElementsLocatedBy`.
          * `Clickable` - **(case in-sensitive)** Waits for the element to be **available** in the Dom and also to be
           **clickable**. Its functionally equivalent to `ExpectedConditions#elementToBeClickable`.
       2. `for` - An integer that represents the wait time in **seconds**. If no value is specified (or) if this 
       attribute is omitted but `until` is included, the system falls back to `45 seconds.`
          * This value can be altered
        globally via the JVM argument `simplese.default.waittime` (for e.g., `-Dsimplese.default.waittime=30`).
          * If for any reason wait is to be completely ignored, then the wait node can be excluded from the json file and instruct **SimpleSe** to ignore using a default wait mechanism by setting the JVM argument `-Dsimplese.default.waitstrategy=false`

### Handling Waits.

There are basically 3 types of scenarios with waits.

1. **Scenario #1 (You want a global implicit wait to be applied across all page objects without you explicitly mentioning it in your json file)**

The json could look like below:

```json
{
  "name": "FooPage",
  "defaultLocale": "en_US",
  "elements": [
    {
      "name": "bar",
      "locale": [
        {
          "name": "en_US",
          "locator": "xpath=//h1"
        },
        {
          "name": "en_FR",
          "locator": "xpath=//h1"
        }
      ]
    }
  ]
}
```
In this case, `SimpleSe` will default to a wait fo 45 seconds for the element to be present in the DOM before timing out. This default value can be changed using the JVM argument `-Dsimplese.default.waittime=30` (we changed the default wait time from 45 seconds to 30 seconds) 

2. **Scenario #2 (You want to globally disable implicit wait by not having it in your json files)**

In this case, for all elements wherein the `wait` attribute is missing in your json file, then wait will be skipped from being applied only for that element. Make sure you turn on this behavior via the JVM argument `-Dsimplese.default.waitstrategy=false`.

The json could look like below:

```json
{
  "name": "FooPage",
  "defaultLocale": "en_US",
  "elements": [
    {
      "name": "bar",
      "locale": [
        {
          "name": "en_US",
          "locator": "xpath=//h1"
        },
        {
          "name": "en_FR",
          "locator": "xpath=//h1"
        }
      ]
    }
  ]
}
```

3. **Scenario #3 (You want to turn off implicit waits only for a few elements**

In this case, for some elements, you want to use the implicit wait strategy, for some elements, you want to use an explicit wait strategy and for some elements you want to disable the wait strategy completely.

Here's how the json would look like:

```json
{
  "name": "FooPage",
  "defaultLocale": "en_US",
  "elements": [
    {
      "name": "bar",
      "locale": [
        {
          "name": "en_US",
          "locator": "xpath=//h1"
        },
        {
          "name": "en_FR",
          "locator": "xpath=//h1"
        }
      ]
    },
    {
      "name": "foo",
      "locale": [
        {
          "name": "en_US",
          "locator": "xpath=//h1"
        },
        {
          "name": "en_FR",
          "locator": "xpath=//h1"
        }
      ],
      "wait": {
        "until": "clickable",
        "for": 20
      }
    },
    {
      "name": "foobar",
      "locale": [
        {
          "name": "en_US",
          "locator": "xpath=//h1"
        },
        {
          "name": "en_FR",
          "locator": "xpath=//h1"
        }
      ],
      "wait": {
        "until": "clickable",
        "for": -1
      }
    }
  ]
}
```

Here:

* The `bar` element uses the implicit wait strategy (wait for 45 seconds for the element to be available in DOM)
* The `foo` element uses the explicit wait strategy (wait for 20 seconds for the element to be clickable)
* The `foobar` element disables the wait strategy by mentioning the wait time as `-1`


## Some code samples.

Lets say we have the below json sample which is located in `src/test/resources/HomePage.json`. 

```json
{
  "name": "HomePage",
  "defaultLocale": "en_US",
  "elements": [
    {
      "name": "heading",
      "locale": [
        {
          "name": "en_US",
          "locator": "xpath=//h1[@class='heading']"
        }
      ],
      "wait": {
        "until": "visible",
        "for": 45
      }
    },
    {
      "name": "heading1",
      "locale": [
        {
          "name": "en_US",
          "locator": "//h1[@class='heading1']"
        }
      ],
      "wait": {
        "until": "visible",
        "for": 45
      }
    },
    {
      "name": "checkboxesLink",
      "locale": [
        {
          "name": "en_US",
          "locator": "xpath=//a[text()='Checkboxes']"
        }
      ],
      "wait": {
        "until": "clickable",
        "for": 45
      }
    }
  ]
}
```

A sample code that would work with the above mentioned would look like below :

```java
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class HerokkuAppTest {
    private RemoteWebDriver driver;

    @BeforeClass
    public void setup() {
        driver = new ChromeDriver();
    }

    @Test
    public void testMethod() {
        driver.get("http://the-internet.herokuapp.com/");

        PageObject homePage = new PageObject(driver, "src/test/resources/HomePage.json");
        Label heading = homePage.getLabel("heading");
        Assert.assertEquals(heading.getText(), "Welcome to the Internet");

        Link checkbox = homePage.getLink("checkboxesLink");
        Assert.assertEquals(checkbox.isDisplayed(), true);
    }

    @AfterClass
    public void cleanup() {
        if (driver != null) {
            driver.quit();
        }
    }
}

```

#### A sample that shows how to work with list of elements using `Simple Se Page Objects`

Lets say we have a json that looks like below located at `src/test/resources/CheckboxPage.json`

```json
{
  "name": "CheckboxPage",
  "defaultLocale": "en_US",
  "elements": [
    {
      "name": "checkbox",
      "locale": [
        {
          "name": "en_US",
          "locator": "xpath=//input[@type='checkbox']"
        }
      ],
      "wait": {
        "until": "visible",
        "for": 45
      }
    }
  ]
}
```

A sample code that would work with the above mentioned would look like below (working with list of elements) :

```java
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class HerokkuAppTest {
    private RemoteWebDriver driver;

    @BeforeClass
    public void setup() {
        driver = new ChromeDriver();
    }

    @Test
    public void testMethod() {
        driver.get("http://the-internet.herokuapp.com/checkboxes");
        PageObject checkboxPage = new PageObject(driver, "src/test/resources/CheckboxPage.json");
        List<Checkbox> checkboxList = checkboxPage.getCheckboxes("checkbox");
        Assert.assertEquals(checkboxList.size(), 2);
        int uncheckedCount = 0;
        int checkedCount = 0;
        for (Checkbox checkbox : checkboxList) {
            if (checkbox.isChecked()) {
                checkedCount += 1;
            } else {
                uncheckedCount += 1;
            }
        }
        Assert.assertEquals(checkedCount, 1);
        Assert.assertEquals(uncheckedCount, 1);
    }

    @AfterClass
    public void cleanup() {
        if (driver != null) {
            driver.quit();
        }
    }
}
```

#### How to work with a different locale ?

Here's a sample that shows how to create a `PageObject` for the locale `en_FR`.

```java
public class HerokkuAppTest {

    @Test
    public void testMethod() {
        //Here we are creating a page object instance for the locale "en_FR"
        PageObject homePage = new PageObject(driver, "src/test/resources/HomePage.json").forLocale("en_FR");
        //More source code goes here.
        // ..
    }
}
```

#### How to work with a generic element ?

Many times we may have to work with generic elements (such as date picker or a scroll bar etc) for which `Simple Se` 
may not have anything to offer as a ready made object. But you can still regard them as `GenericElement` and work 
with them. Here's a sample:

```java
public class HerokkuAppTest {

    @Test
    public void testMethod() {
        PageObject homePage = new PageObject(driver, "src/test/resources/HomePage.json").forLocale("en_FR");
        GenericElement datePicker = homePage.getGenericElement("datePickerControl");
    }
}
```
