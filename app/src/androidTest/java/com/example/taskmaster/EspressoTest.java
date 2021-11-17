package com.example.taskmaster;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;

public class EspressoTest {
    @Rule
    public ActivityScenarioRule<MyTask> rule = new ActivityScenarioRule<>(MyTask.class);
    //@Test
//public void greeterSaysHello() {
//        onView(withId(R.id.name_field)).perform(typeText("Steve"));
//        onView(withId(R.id.greet_button)).perform(click());
//        onView(withText("Hello Steve!")).check(matches(isDisplayed()));
//        }

    @Test
    public void testAddTest() {
      onView(withId(R.id.AddTask)).perform(click());
      onView(withId(R.id.titlePerson1)).perform(typeText("KHAIR"));
      onView(withId(R.id.bodyPerson)).perform(typeText("JAMAL HATE INTELLIJ :) "));
      onView(withId(R.id.statePerson)).perform(typeText("completed :) "));
        onView(withId(R.id.buttonAdd)).perform(click());

    }
    @Test
    public void testRecycleView() {
        onView(withId(R.id.recyclerViewMain)).check(matches(isDisplayed()));
   }
   @Test
    public void testSettings() {
        onView(withId(R.id.Settings)).perform(click());
       onView(withId(R.id.usernamePlanText)).perform(typeText("Khair"));
       onView(withId(R.id.submitUsername)).perform(click());
       onView(withId(R.id.username)).check(matches(isDisplayed()));
   }
@Test
   public void allTaskTest() {
       onView(withId(R.id.AllTasks)).perform(click());
       onView(withId(R.id.textView4)).check(matches(isDisplayed()));

   }

}
