package ru.example;

public class Starter {
    public static void main(String[] args) {
        Account acc1= new Account();
        System.out.println(acc1);
        acc1.setAmtArr(ECurrency.RUB,100);
        System.out.println(acc1);
        acc1.setOwner("Василий Иванов");
        System.out.println(acc1);
        acc1.setAmtArr(ECurrency.USD,300);
        System.out.println("\n"+acc1+"\n");

        if (acc1.canUndo()) acc1.undo();
        System.out.println(acc1);
        if (acc1.canUndo()) acc1.undo();
        System.out.println(acc1);
        if (acc1.canUndo()) acc1.undo();
        System.out.println(acc1+"\n\n");

        System.out.println("------------------ SAVE ---------------------");
        acc1.setOwner("Василий Иванов");
        System.out.println(acc1);
        acc1.setAmtArr(ECurrency.RUB,300);
        System.out.println(acc1);

        acc1.save();
        System.out.println(acc1.savedAcc.toString()+"<------ Сохраненное значение");
        if (acc1.canUndo()) acc1.undo();
        System.out.println(acc1);
        System.out.println(acc1.savedAcc.toString()+"<------ Сохраненное значение");
    }
}
