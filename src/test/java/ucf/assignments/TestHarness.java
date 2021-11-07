package ucf.assignments;
/*
 *  UCF COP3330 Fall 2021 Application Assignment 1 Solution
 *  Copyright 2021 Srignan Paruchuru
 */
import java.time.LocalDate;
import java.util.ArrayList;


public class TestHarness
{
	private ArrayList<Items> list;
	private ArrayList<Items> listDone;
	private String errorLabel;
	private boolean noDueDateSelected;

	
	public TestHarness()
	{
		reset();
	}
	
	public void reset()
	{
		list = new ArrayList<>();
		listDone = new ArrayList<>();
		errorLabel = "";
		noDueDateSelected = false;

	}
	
	public void addNewItem(Items task)
	{
		if(addItemValidate(task))
		{
			addItemCommit(task);
		}
	}
	
	private boolean addItemValidate(Items task)
	{
		// Check for invalid input
		if( task.getDescription().equals("") )
		{
			errorLabel = "Cannot create an empty task";
			return false;
		}else if( task.getDueDate().isBefore(LocalDate.now()) )
		{
			errorLabel = "Cannot create a task in the past";
			return false;
		}

		// Check for duplicate tasks
		if(isDuplicate(task))
		{
			errorLabel = "Cannot create duplicate tasks";
			return false;
		}
		return true;
	}

	private void addItemCommit(Items task)
	{
		list.add(task);
		sortListByDate(list);

		noDueDateSelected = false;
		errorLabel = "";
	}
	
	private boolean isDuplicate(Items task)
	{
		for (Items Items : list) {
			if (task.getDescription().equals(Items.getDescription())) {
				if (noDueDateSelected) {
					if (Items.getDueDate() == null) {
						return true;
					}
				} else {
					if (Items.getDueDate() != null &&
							Items.getDueDate().isEqual(task.getDueDate())) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public void sortListByDate(ArrayList<Items> list)
	{
		list.sort((t1, t2) -> {
			if (t1.isUrgent()) {
				return -1;
			} else if (t2.isUrgent()) {
				return 1;
			}

			if (t1.getDueDate() == null) {
				return 1;
			} else if (t2.getDueDate() == null) {
				return -1;
			}

			if (t1.getDueDate().isAfter(t2.getDueDate()) ||
					t1.getDueDate().isEqual(t2.getDueDate())) {
				return 1;
			}
			if (t1.getDueDate().isBefore(t2.getDueDate())) {
				return -1;
			}
			return 0;
		});
	}
	
	
	public void deleteItem(int position, boolean deleteCompletedTask)
	{
		if(deleteCompletedTask)
		{
			if(listDone.size() == 0)
			{
				return;
			}

			listDone.remove(position);
		}else
		{
			if(list.size() == 0)
			{
				return;
			}
			list.remove(position);
		}
	}
	
	public void markItemAsComplete(int position, boolean markAsComplete)
	{
		Items task;
		if(markAsComplete)
		{
			if(list.size() == 0)
			{
				return;
			}
			task = list.get(position);
			task.setCompleted(true);
			task.setCompletionDate(LocalDate.now());
			listDone.add(task);
			list.remove(position);
			sortListByDate(listDone);
		}else
		{
			if(listDone.size() == 0)
			{
				return;
			}
			task = listDone.get(position);
			
			if(task.getDueDate() != null && 
			   task.getDueDate().isBefore(LocalDate.now()))
			{
				errorLabel = "Cannot mark a task as incomplete if it's past the due date";
				return;
			}
			task.setCompleted(false);
			task.setCompletionDate(null);
			listDone.remove(task);
			list.add(task);
			sortListByDate(list);
		}
	}

	public ArrayList<Items> getList() {
		return list;
	}

	public ArrayList<Items> getListDone() {
		return listDone;
	}

	public void setListDone(ArrayList<Items> listDone) {
		this.listDone = listDone;
	}

	public String getErrorLabel() {
		return errorLabel;
	}

}
