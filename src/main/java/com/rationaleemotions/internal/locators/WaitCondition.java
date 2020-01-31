package com.rationaleemotions.internal.locators;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedCondition;

public interface WaitCondition<T> {

    String getName();

    ExpectedCondition<T> element(By by);

    ExpectedCondition<List<T>> elements(By by);
}
