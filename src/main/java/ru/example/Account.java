package ru.example;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Arrays;
import java.util.Objects;
import java.util.Stack;

interface IMemento {
    String getOwner();
    CurrAmount[] getAmtArr();
}

class AccountMemento implements IMemento {
    private String owner;
    private CurrAmount[] amtArr;

    public AccountMemento(String o, CurrAmount[] a) {
        this.owner = o;
        this.amtArr = a;
    }
    @Override
    public String getOwner(){
        return owner;
    }
    @Override
    public CurrAmount[] getAmtArr(){
        return amtArr;
    }
}

public class Account {
    @Getter @Setter
    private String owner;
    private CurrAmount[] amtArr;
    private Memory memory;
    SaveAcc savedAcc;

    {   memory = new Memory(this);
    }

    public Account (){
        this.owner  = null;
        this.amtArr = null;
        savedAcc    = null;
    }

    public void setOwner(String owner) {
        if (owner == null || owner == "" ) throw new IllegalArgumentException("Не указан владелец счета");
        memory.backup();
        this.owner = owner;
    }

    public void setAmtArr(ECurrency curr, int amount) {
        if(amount<0) throw new IllegalArgumentException("Количество валюты не может быть отрицательным!");
        memory.backup(); //
        if (this.amtArr != null ) {
            for (int i = 0; i < this.amtArr.length; i++) {
                // если валюта уже есть в списке
                if (this.amtArr[i].getCurr() == curr) {
                    this.amtArr[i] = this.amtArr[i].setNewAmount(amount);
                    return;
                }
            }
            // Валюты нету в списке
            CurrAmount[] res = new CurrAmount[this.amtArr.length + 1];
            for (int j=0; j < this.amtArr.length; j++) {
                res[j] = this.amtArr[j];
            }
            res[res.length-1] = new CurrAmount(curr, amount);
            this.amtArr = res;
        }
        else {
            CurrAmount[] res = new CurrAmount[1];
            res[res.length-1] = new CurrAmount(curr, amount);
            this.amtArr = res;
        }
    }

    public boolean canUndo(){
        return memory.canUndo();
    }
    public void undo(){
        memory.undo();
    }

    public void save (){
        savedAcc = new SaveAcc(owner,amtArr);
    }

    public AccountMemento memSave (){
        return new AccountMemento(owner,amtArr);
    }
    public void restore (IMemento accountMemento){
        owner = accountMemento.getOwner();
        amtArr = accountMemento.getAmtArr();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account tmp = (Account) o;
        return Objects.equals(owner, tmp.owner) && Objects.deepEquals(amtArr, tmp.amtArr);
    }

    public boolean equalsSavedAcc(Object o) {
        if (this == o) return true;
        if (o == null ) return false;
        SaveAcc tmp = (SaveAcc) o;
        return Objects.equals(owner, tmp.getOwner()) && Objects.deepEquals(amtArr, tmp.getAmtArr());
    }

    @Override
    public int hashCode() {
        return Objects.hash(owner, Arrays.hashCode(amtArr));
    }

    @Override
    public String toString() {
        return "Account{" +
                "owner='" + owner + '\'' +
                ", amtArr=" + Arrays.toString(amtArr) +
                '}';
    }
}

class Memory{
    private Stack<IMemento> history;
    private Account account;

    public Memory(Account acc) {
        this.account = acc;
        history = new Stack<>();
    }

    public void backup(){
        history.push(account.memSave());
        //System.out.println(" <------- Сохраним это состояние: "+history.size());
    }

    public boolean canUndo(){
        if (history.isEmpty()) return false;
        return true;
    }

    public void undo(){
        if (history.isEmpty()) {throw new IllegalArgumentException("Нет данных для Отмены!");}
        //System.out.print(">------ Восстановим состояние из памяти: "+history.size()+" ");
        account.restore(history.pop());
    }
}