package com.molt3nrock.racingcar;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.Collections;
import org.junit.Test;

public class CarTest {

    @Test
    public void carFormat() {
        Car car = new Car("foo");
        setIntField(car, "position", 3);
        String actual = car.format(1);
        assertEquals("foo : ---", actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void toStringIllegal() {
        new Car("foobar");
    }

    @Test
    public void move() {
        String name = "foo";
        Car car = new Car(name);
        car.move();
        int position = getPosition(car);
        String expected = name + " : " + String.join("", Collections.nCopies(position, "-"));
        String actual = car.format(1);
        assertEquals(expected, actual);
    }

    @Test
    public void compareTo() {
        Car fooCar = new Car("foo");
        Car barCar = new Car("bar");
        fooCar.move();
        barCar.move();
        int fooPosition = getPosition(fooCar);
        int barPosition = getPosition(barCar);
        assertEquals(fooCar.compareTo(barCar), Integer.compare(barPosition, fooPosition));
    }

    private int getPosition(Car car) {
        try {
            Field f = car.getClass().getDeclaredField("position");
            f.setAccessible(true);
            return (int) f.get(car);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return 0;
    }


    private Car setIntField(Car car, String fieldName, int position) {
        try {
            Field f = car.getClass().getDeclaredField(fieldName);
            f.setAccessible(true);
            f.set(car, position);
            return car;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}