package ucf.assignments;
/*
 *  UCF COP3330 Fall 2021 Application Assignment 1 Solution
 *  Copyright 2021 Srignan Paruchuru
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AppData implements Serializable
{
    /*
     Declare 2 ArrayList of type <Items>:
    -list: store incomplete todolist
    -listDone: store complete todolist
     */
    private final ArrayList<Items> list;
    private final ArrayList<Items> listDone;


    public AppData(ArrayList<Items> list, ArrayList<Items> listDone)
    {
         /*
        This is a constructor
        Initialize list and listDone (this)
         */
        this.list = (ArrayList<Items>) list;
        this.listDone = (ArrayList<Items>) listDone;

    }

    public List<Items> getList()
    {
         /*
        return list
         */
        return list;
    }

    public List<Items> getListDone()
    {
        /*
        return listDone
         */
        return listDone;
    }
}
