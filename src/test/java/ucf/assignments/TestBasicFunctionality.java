package ucf.assignments;
/*
 *  UCF COP3330 Fall 2021 Application Assignment 1 Solution
 *  Copyright 2021 Srignan Paruchuru
 */
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestBasicFunctionality
{
	private static TestHarness app;
	private static Random random;

	@BeforeAll
	static void setUpBeforeClass() {
		app = new TestHarness();
		random = new Random();
	}

	@AfterAll
	static void tearDownAfterClass() {
		app = null;
	}

	@BeforeEach
	void setUp() {
		app.addNewItem(new Items("Task1", LocalDate.now().plusDays(3), false));
		app.addNewItem(new Items("Task2", LocalDate.now().plusDays(21), false));
		app.addNewItem(new Items("Task3", LocalDate.now().plusDays(5), false));
		app.addNewItem(new Items("Task4", LocalDate.now().plusWeeks(3), false));
		app.addNewItem(new Items("Task5", LocalDate.now(), false));
		app.sortListByDate(app.getList());
	}

	@AfterEach
	void tearDown() {
		app.reset();
	}

	@Test
	void testAddItems()
	{
		System.out.println("testAddTasks start");
		
		app.reset();
		assertEquals(0, app.getList().size());
		app.addNewItem(new Items("Task1", LocalDate.now(), false));
		app.addNewItem(new Items("Task2", LocalDate.now(), false));
		app.addNewItem(new Items("Task3", LocalDate.now(), false));
		app.addNewItem(new Items("Task4", LocalDate.now(), false));
		app.addNewItem(new Items("Task5", LocalDate.now(), false));
		assertEquals(5, app.getList().size());
		
		System.out.println("testAddTasks end");
	}

	@Test
	void testRemoveItems()
	{
		System.out.println("testRemoveTasks start");

		assertEquals(5, app.getList().size());

		app.deleteItem(random.nextInt(app.getList().size()), false);
		app.deleteItem(random.nextInt(app.getList().size()), false);
		app.deleteItem(random.nextInt(app.getList().size()), false);
		app.deleteItem(random.nextInt(app.getList().size()), false);

		assertEquals(1, app.getList().size());

		System.out.println("testRemoveTasks end");
	}

	@Test
	void testMarkItemsAsCompleteAndIncomplete()
	{
		System.out.println("testMarkTasksAsCompleteAndIncomplete start");

		assertEquals(5, app.getList().size());
		assertEquals(0, app.getListDone().size());

		app.markItemAsComplete(random.nextInt(app.getList().size()), true);
		app.markItemAsComplete(random.nextInt(app.getList().size()), true);
		app.markItemAsComplete(random.nextInt(app.getList().size()), true);

		assertEquals(2, app.getList().size());
		assertEquals(3, app.getListDone().size());

		app.markItemAsComplete(random.nextInt(app.getListDone().size()), false);
		app.markItemAsComplete(random.nextInt(app.getListDone().size()), false);

		assertEquals(4, app.getList().size());
		assertEquals(1, app.getListDone().size());

		System.out.println("testMarkTasksAsCompleteAndIncomplete end");
	}


	@Test
	void testDeleteCompleteItems()
	{
		System.out.println("testDeleteCompleteTasks start");

		assertEquals(5, app.getList().size());
		assertEquals(0, app.getListDone().size());

		app.markItemAsComplete(random.nextInt(app.getList().size()), true);
		app.markItemAsComplete(random.nextInt(app.getList().size()), true);
		app.markItemAsComplete(random.nextInt(app.getList().size()), true);

		assertEquals(2, app.getList().size());
		assertEquals(3, app.getListDone().size());

		app.deleteItem(random.nextInt(app.getListDone().size()), true);
		app.deleteItem(random.nextInt(app.getListDone().size()), true);

		assertEquals(2, app.getList().size());
		assertEquals(1, app.getListDone().size());

		System.out.println("testDeleteCompleteTasks end");
	}

	@Test
	void testAddNullItems()
	{
		System.out.println("testAddNullTask start");

		assertEquals("", app.getErrorLabel());
		assertEquals(5, app.getList().size());

		app.addNewItem(new Items("", LocalDate.now(), false));

		assertEquals("Cannot create an empty task", app.getErrorLabel());
		assertEquals(5, app.getList().size());

		System.out.println("testAddNullTask end");
	}

	@Test
	void testAddItemInPast()
	{
		System.out.println("testAddTaskInPast start");

		assertEquals("", app.getErrorLabel());
		assertEquals(5, app.getList().size());

		app.addNewItem(new Items("Task6", LocalDate.now().minusDays(1), false));

		assertEquals("Cannot create a task in the past", app.getErrorLabel());
		assertEquals(5, app.getList().size());

		System.out.println("testAddTaskInPast end");
	}

	@Test
	void testAddDuplicateItem()
	{
		System.out.println("testAddDuplicateTask start");
		app.reset();

		assertEquals("", app.getErrorLabel());
		assertEquals(0, app.getList().size());

		app.addNewItem(new Items("Task1", LocalDate.now(), false));

		assertEquals(1, app.getList().size());
		assertEquals("", app.getErrorLabel());

		app.addNewItem(new Items("Task1", LocalDate.now(), false));

		assertEquals(1, app.getList().size());
		assertEquals("Cannot create duplicate tasks", app.getErrorLabel());

		app.addNewItem(new Items("Task2", LocalDate.now(), false));

		assertEquals(2, app.getList().size());
		assertEquals("", app.getErrorLabel());

		System.out.println("testAddDuplicateTask end");
	}

	/**
	 * This verifies that it is not permitted to mark a complete task as incomplete, if it's due date is in the past
	 */
	@Test
	void testMarkingCompleteItemFromPastAsIncomplete()
	{
		System.out.println("testMarkingCompleteTaskFromPastAsIncomplete start");

		// Create a complete task with due date in the past
		ArrayList<Items> listDone = app.getListDone();
		listDone.add(new Items("Task1", LocalDate.now().minusWeeks(1), false));
		app.setListDone(listDone);

		assertEquals(5, app.getList().size());
		assertEquals(1, app.getListDone().size());

		app.markItemAsComplete(0, false);

		assertEquals("Cannot mark a task as incomplete if it's past the due date", app.getErrorLabel());
		assertEquals(5, app.getList().size());
		assertEquals(1, app.getListDone().size());

		System.out.println("testMarkingCompleteTaskFromPastAsIncomplete end");
	}


	@Test
	void testCheckSorting()
	{
		System.out.println("testCheckSorting start");

		for(int i = 0 ; i < app.getList().size() ; i++)
		{
			if(i == 0)
			{
				continue;
			}
			assertFalse(app.getList().get(i).getDueDate().isBefore(app.getList().get(i-1).getDueDate()));
		}

		System.out.println("testCheckSorting end");
	}
}