package ru.example;

import lombok.EqualsAndHashCode;

import java.util.Objects;
@EqualsAndHashCode
public class CurrAmount {
    private ECurrency curr;
    private int amount;

    public CurrAmount(ECurrency curr, int amount) {
        if (amount < 0 ) return;
        this.curr = curr;
        this.amount = amount;
    }

    public CurrAmount setNewAmount (int amount){
        return new CurrAmount (this.curr, amount);
    }

    public ECurrency getCurr() {
        return curr;
    }

    @Override
    public String toString() {
        return "<" +
                "curr=" + curr +
                ", amount=" + amount +
                '>';
    }
}
