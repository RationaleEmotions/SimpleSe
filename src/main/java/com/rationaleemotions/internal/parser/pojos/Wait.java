package com.rationaleemotions.internal.parser.pojos;

import static com.rationaleemotions.internal.locators.WaitServiceListener.INSTANCE;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rationaleemotions.internal.locators.DefaultWaitConditions;
import com.rationaleemotions.internal.locators.WaitCondition;
import java.util.Objects;

public class Wait {

  private static final int defaultWait = Integer.parseInt(System.getProperty("default.wait.time", "45"));

  @SerializedName("until")
  @Expose
  private String until;

  @SerializedName("for")
  @Expose
  private int duration = defaultWait;

  public WaitCondition getWaitCondition() {
    return INSTANCE.parse(until);
  }

  public void setUntil(String until) {
    this.until = until;
  }

  public int getDuration() {
    return duration;
  }

  public void setDuration(int duration) {
    if (duration > 0) {
      this.duration = duration;
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Wait wait = (Wait) o;
    return duration == wait.duration &&
        Objects.equals(until, wait.until);
  }

  @Override
  public int hashCode() {
    return Objects.hash(until, duration);
  }

  public boolean isValid() {
    return getWaitCondition() != null && getDuration() > 0;
  }

  public boolean isElementConditionValid() {
    return isValid() && getWaitCondition().element(null) != null;
  }

  public boolean isElementsConditionValid() {
    return isValid() && getWaitCondition().elements(null) != null;
  }

  @Override
  public String toString() {
    return "Wait{" +
        "until='" + until + '\'' +
        ", duration=" + duration +
        '}';
  }

  public static Wait defaultInstance() {
    Wait wait = new Wait();
    wait.setUntil(DefaultWaitConditions.AVAILABLE.getName());
    wait.setDuration(defaultWait);
    return wait;
  }
}
