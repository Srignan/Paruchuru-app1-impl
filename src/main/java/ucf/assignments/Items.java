package ucf.assignments;
/*
 *  UCF COP3330 Fall 2021 Application Assignment 1 Solution
 *  Copyright 2021 Srignan Paruchuru
 */
import java.io.Serializable;
import java.time.LocalDate;

public class Items implements Serializable
{
    /*
    Declare the attributes:
    dueDate: LocalDate
    completionDate: LocalDate
    description: String
    completed: boolean
    urgent: boolean
     */

    private final LocalDate dueDate;
    private LocalDate completionDate;
    private final String description;
    private boolean completed;
    private final boolean urgent;

    public Items(String description, LocalDate date, boolean urgent)
    {
        /*
        Constructor to initialize description, date, and urgent based on the received parameters
        set completionDate to null
        set completed to false
        This is because this is a new entry
         */
        this.description = description;
        this.dueDate = date;
        this.completionDate = null;
        this.completed = false;
        this.urgent = urgent;
    }

    @Override
    public String toString()
    {
        /*
        String[] array : dateArray and completionDateArray

        if(dueDate is not null)
        -add dueDate to dateArray (convert to string and split on the basis of "-")

        if(completionDate is not null)
        -add completionDate to completionDateArray (convert to string and split on the basis of "-")

        if(item is urgent)
        -  if(item is completed)
           - return string completionDateArray + description

        -  else
           - return string "urgent" + description

        else(item's not urgent)
        -  if(item is completed)
           - return string completionDateArray + description + dateArray

        -  else
           - return string dateArray + description
                    */
        String[] dateArray = null;
        String[] completionDateArray = null;
        if(dueDate != null)
        {
            dateArray = dueDate.toString().split("-");
        }
        if(completionDate != null)
        {
            completionDateArray = completionDate.toString().split("-");
        }

        if(urgent)
        {
            if(completed)
            {
                assert completionDateArray != null;
                return completionDateArray[0] + "/" + completionDateArray[1] + "/" + completionDateArray[2] +
                        " | URGENT | " + description;
            }else
            {
                return "URGENT | " + description;
            }
        }else
        {
            if(completed)
            {
                assert completionDateArray != null;
                return completionDateArray[0] + "/" + completionDateArray[1] + "/" + completionDateArray[2] +
                        " | "+ description + ((dateArray == null)?"": "   (due " +
                        dateArray[0] + "/" + dateArray[1] + "/" + dateArray[2] + ")");
            }else
            {
                return ((dateArray == null)?"": dateArray[0] + "/" + dateArray[1] + "/" + dateArray[2] + " | ") +
                        description;
            }
        }
    }

    public void setCompletionDate(LocalDate completionDate)
    {

         /*
        initialize completionDate (this)
         */
        this.completionDate = completionDate;
    }

    public boolean isUrgent()
    {
         /*
        return urgent
         */
        return urgent;
    }

    public LocalDate getDueDate()
    {

         /*
        return dueDate
         */
        return dueDate;
    }

    public String getDescription()
    {
         /*
        return description
         */
        return description;
    }

    public boolean isCompleted()
    {    /*
        return completed (this)
         */
        return completed;
    }

    public void setCompleted(boolean completed)
    {

          /*
        initialize completed (this)
         */
        this.completed = completed;
    }
}
