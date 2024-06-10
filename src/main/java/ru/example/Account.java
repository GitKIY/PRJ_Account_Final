package ru.example;

import java.util.*;

class Manager{
    //Map<Date, List<Save> > saves;
    Deque<Save> saves = new ArrayDeque<>();
    public void save (Account acc){
        saves.push(acc.save());
    }
    public void restore (){
        saves.pop().load();
    }
}

public class Account {
    private String owner;
    private final Map<ECurrency,Integer> amtMap = new HashMap<>();
    private Deque<Command> memory = new ArrayDeque<>();

    public Account (){
        this.owner  = null;
        this.amtMap.clear();
    }

    public Account (String owner){
        setOwner (owner);
        this.amtMap.clear();
    }

    public String getOwner() {
        return owner;
    }

    public Map<ECurrency, Integer> getAmtMap() {
        return amtMap;
    }

    public void setOwner(String owner) {
        if (owner == null || owner.isBlank()) throw new IllegalArgumentException("Не указан владелец счета");
        String tmp = Account.this.owner;
        memory.push(()->Account.this.owner=tmp);
        this.owner = owner;
    }

    public Map<ECurrency, Integer> getCurrenc(){return new HashMap<>(amtMap);}

    public void setAmtMap(ECurrency curr, int amount) {
        if (curr == null) throw new IllegalArgumentException();
        if (amount<0) throw new IllegalArgumentException("Количество валюты не может быть отрицательным!");
        if (amtMap.get(curr) == null) { // добавление валюты+сумма NewValueReverse
            memory.push(new NewValueReverse(curr));
        } else {// замена суммы
            int tmp= amtMap.get(curr);
            memory.push(new ChangeValueReverse(curr));
        }
        amtMap.put(curr,amount);
    }

    public Save save(){return new AccSave();}

    public void undo(){
        if (memory.isEmpty()) {throw new IllegalArgumentException("Нет данных для Отмены!");}
        memory.pop().make();
    }

    private class AccSave implements Save {
        private String owner=Account.this.owner;
        private Map<ECurrency,Integer> amtArr = new HashMap<>(Account.this.amtMap);

        public void load (){
            Account.this.owner = owner;
            Account.this.amtMap.clear();
            Account.this.amtMap.putAll(amtArr);
        }
    }
    public boolean canUndo(){
        return !memory.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account tmp = (Account) o;
        return Objects.equals(owner, tmp.owner) && Objects.deepEquals(amtMap, tmp.amtMap);
    }

    public boolean equalsSavedAcc(Object o) {
        if (this == o) return true;
        if (o == null ) return false;
        Account tmp = (Account) o;
        return Objects.equals(owner, tmp.getOwner()) && Objects.deepEquals(amtMap, tmp.getAmtMap());
    }

    @Override
    public String toString() {
        return "Account{" +
                "owner='" + owner + '\'' +
                ", amtMap=" + amtMap +
                //", saves=" + saves +
                '}';
    }

    class ChangeValueReverse implements Command {
        int tmp;
        ECurrency curr;
        ChangeValueReverse(ECurrency cur){
            this.curr = cur;
            this.tmp = amtMap.get(cur);
        }
        public void make() {
            amtMap.put(curr,tmp);
        }
    }

    class NewValueReverse implements Command{
        ECurrency tmpCurr;
        NewValueReverse(ECurrency cur){
            this.tmpCurr = cur;
        }
        public void make() {
            amtMap.remove(tmpCurr);
        }
    }

}

interface Command {
    public void make();
}
