package ru.example;

public enum ECurrency {
    RUB("Рубль"),CNY("Юань"),USD("Доллар"),EUR("Евро"),GPB("Фунт"),JPY("Йена");
    private String name;

    ECurrency(String nmae) {
        this.name = name;
    }
    public void  m(){
        System.out.print(name);
    }
}