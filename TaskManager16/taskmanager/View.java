package taskmanager;

import taskmanager.TaskLog;
import taskmanager.TaskNode;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

/**
 *
 * @author Ilya
 */

public class View implements Serializable {
public View()
{
    
}
    public TaskNode viewForNewTask() throws IOException, ParseException {
        Scanner in = new Scanner(System.in);
        String taskName;
        String taskDescription;
        int years;
        int month;
        int day;
        int hours;
        int minutes;
        String phoneNumber;
        System.out.println(" | NEW TASK   ");

        System.out.print(" | Событие: ");
        taskName = in.nextLine(); // надо

        System.out.print(" | Описание: ");
        taskDescription = in.nextLine(); // надо

        System.out.print(" |Номер телефона: ");
        phoneNumber = in.nextLine();

        //проверки на ввод даты?
        System.out.println(" | Год:");
        years = in.nextInt();
        System.out.println(" | Месяц:");
        month = in.nextInt();
        System.out.println(" | День:");
        day = in.nextInt();
        System.out.println(" | Часы:");
        hours = in.nextInt();
        System.out.println(" | Минуты:");
        minutes = in.nextInt();



        GregorianCalendar taskDate= new GregorianCalendar(years,month,day,hours,minutes);

        return new TaskNode(taskName,taskDescription,taskDate,phoneNumber);
       
        //в файл записать
      
    }
    public void clear()
{
    for(int i=0;i<30;i++)
            {
                System.out.println();
            }
}
public void mainView()
{
            System.out.println("1-Новое событие;");
            System.out.println("2-Просмотр всех событий;");
            System.out.println("3-Удалить событие;");
            System.out.println("0-Закрыто менеджер задач.");
}
    public void viewAllTasks(Log<TaskNode> tmp) {
        for (int i = 0; i < tmp.size(); i++){
            System.out.println( "["+i+"] | TASK |");
            System.out.println("   |Событие: " + tmp.get(i).getTaskName() + ";");
            System.out.println("   |Описание: " + tmp.get(i).getTaskDescription() + ";");
            System.out.println("   |Дата: " + tmp.get(i).getTaskDate().get(Calendar.DAY_OF_MONTH) + "." +
                     tmp.get(i).getTaskDate().get(Calendar.MONTH) +"." + tmp.get(i).getTaskDate().get(Calendar.YEAR) +";");
            System.out.println("   |Время: " + tmp.get(i).getTaskDate().get(Calendar.HOUR)+":"+ tmp.get(i).getTaskDate().get(Calendar.MINUTE) + ";");
            System.out.println("   |Контакты: " + tmp.get(i).getPhoneNumber() + ".");
            System.out.println("   |-------------------------------");

        }
    }
    public int viewForDelete () {
        int number;
        System.out.println(" | Введите что удалить (номер?)");
        Scanner in = new Scanner(System.in);
        number = in.nextInt();
        return number;
    }
}

