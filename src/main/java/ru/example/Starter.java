package ru.example;

public class Starter {
    public static void main(String[] args) {
        Account acc1= new Account();
        System.out.println(acc1);
        acc1.setAmtMap(ECurrency.USD,100);
        System.out.println(acc1);
        acc1.setOwner("Василий Иванов");
        System.out.println(acc1);
        acc1.setAmtMap(ECurrency.RUB,300);
        System.out.println(acc1);
        acc1.setAmtMap(ECurrency.RUB,100);

        System.out.println("\n"+acc1+"\n");

        if (acc1.canUndo()) {acc1.undo();
        System.out.println(acc1);}
        if (acc1.canUndo()) {acc1.undo();
        System.out.println(acc1);}
        if (acc1.canUndo()) {acc1.undo();
        System.out.println(acc1);}
        if (acc1.canUndo()) { acc1.undo();
        System.out.println(acc1+"\n\n");}

        System.out.println("------------------ SAVE ---------------------");
        Manager mng = new Manager();
        acc1.setOwner("Василий Иванов");
        System.out.println(acc1);
        acc1.setAmtMap(ECurrency.RUB,300);
        System.out.println(acc1);

        mng.save(acc1);
        System.out.println(acc1.toString()+"<------ Сохраненное значение1");
        if (acc1.canUndo()) acc1.undo();
        System.out.println(acc1+"<---Отмена");
        acc1.setAmtMap(ECurrency.USD,500);
        mng.save(acc1);
        System.out.println(acc1.toString()+"<------ Сохраненное значение2");
        if (acc1.canUndo()) acc1.undo();
        System.out.println(acc1+"<---Отмена");
        mng.restore();
        System.out.println(acc1.toString()+"<------ Сохраненное значение2");
        mng.restore();
        System.out.println(acc1.toString()+"<------ Сохраненное значение1");


    }
}
