package ru.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

public class Tests {
    @Test
    public void amountInRange (){
        Account acc1= new Account();
        acc1.setAmtArr(ECurrency.RUB,100);
    }
    @Test
    public void amountNotInRange (){
       Account acc1 = new Account();
       Assertions.assertThrows(IllegalArgumentException.class,()->acc1.setAmtArr(ECurrency.RUB, -100));
    }
    @Test
    public void nameIsNotNull (){
        Account acc1= new Account();
        acc1.setOwner("Василий Иванов");
    }

    @Test
    public void nameIsNullOrEmpty (){
        Account acc1 = new Account();
        Assertions.assertThrows(IllegalArgumentException.class,()->acc1.setOwner(null));
        Assertions.assertThrows(IllegalArgumentException.class,()->acc1.setOwner(""));
    }

    @Test
    public void testUndo (){
        Account etalon = new Account();
        etalon.setAmtArr(ECurrency.RUB,100);
        Account acc = new Account();
        Assertions.assertThrows(IllegalArgumentException.class,()->acc.undo()); // Нет изменений проверим ошибку Отмены
        acc.setAmtArr(ECurrency.RUB,100);
        Assertions.assertEquals(acc,etalon);    // Проверяем что acc и etalon содержат одинаковую информацию
        acc.setOwner("Василий Иванов");
        Assertions.assertNotEquals(acc,etalon); // Проверим что измененный acc (+владелец), теперь не равен etalon
        acc.undo();     // теперь откатим последнее изменение, где указывали владельца
        Assertions.assertEquals(acc,etalon); // acc и etalon теперь опять должны содержат одинаковую информацию
    }

    @Test
    public void testSave (){
        Account acc = new Account();
        acc.setAmtArr(ECurrency.RUB,100);
        acc.setOwner("Василий Иванов");
        if (acc.equalsSavedAcc(acc.savedAcc)) throw new AssertionFailedError("Ошибка теста"); // Проверяем что acc не соответствует savedAcc
        acc.save();     // сохраняем состояние
        if (!acc.equalsSavedAcc(acc.savedAcc)) throw new AssertionFailedError("Ошибка теста"); // Теперь acc соответствует savedAcc
        acc.undo(); // теперь откатим последнее изменение, где указывали владельца
        if (acc.equalsSavedAcc(acc.savedAcc)) throw new AssertionFailedError("Ошибка теста");   // Теперь они не равны так как,
                                                                                            // SavedAcc содержит сохраненное состояние,
                                                                                            // в acc отменено последнее изменение

    }
}
