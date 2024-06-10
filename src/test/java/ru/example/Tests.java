package ru.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

public class Tests {
    @Test
    public void amountInRange (){
        Account acc1= new Account();
        acc1.setAmtMap(ECurrency.RUB,100);
    }
    @Test
    public void amountNotInRange (){
       Account acc1 = new Account();
       Assertions.assertThrows(IllegalArgumentException.class,()->acc1.setAmtMap(ECurrency.RUB, -100));
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
        etalon.setAmtMap(ECurrency.RUB,100);
        Account acc = new Account();
        Assertions.assertThrows(IllegalArgumentException.class,()->acc.undo()); // Нет изменений проверим ошибку Отмены
        acc.setAmtMap(ECurrency.RUB,100);
        Assertions.assertEquals(acc,etalon);    // Проверяем что acc и etalon содержат одинаковую информацию
        acc.setOwner("Василий Иванов");
        Assertions.assertNotEquals(acc,etalon); // Проверим что измененный acc (+владелец), теперь не равен etalon
        acc.undo();     // теперь откатим последнее изменение, где указывали владельца
        Assertions.assertEquals(acc,etalon); // acc и etalon теперь опять должны содержат одинаковую информацию
    }

    @Test
    public void testSave (){
        Account acc = new Account();
        Account etalon = new Account();
        Manager mng = new Manager();
        acc.setAmtMap(ECurrency.RUB,100);
        acc.setOwner("Василий Иванов");
        etalon.setAmtMap(ECurrency.RUB,100);
        etalon.setOwner("Василий Иванов");
        Assertions.assertEquals(acc,etalon); // Проверим что acc соответствует etalon
        mng.save(acc);      // сохраняем состояние acc
        acc.undo();         // теперь откатим последнее изменение, где указывали владельца
        Assertions.assertNotEquals(acc,etalon); // Теперь acc не соответствует etalon
        mng.restore();     // теперь восстановим acc из сохранения
        Assertions.assertEquals(acc,etalon);   // Теперь они равны

    }
}
