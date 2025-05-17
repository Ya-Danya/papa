package ru.masnaviev.university;

class Emu2 {
    String name = "Emmy";

     Feathers createFeathers() {
        return this.new Feathers("grey"); // ❌ НЕ КОМПИЛИРУЕТСЯ
    }

    class Feathers {
        Feathers(String color) {}

        void fly() {
            System.out.print(name + " is flying"); // ✅ Компилируется
        }
    }
}
