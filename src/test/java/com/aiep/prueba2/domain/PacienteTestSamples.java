package com.aiep.prueba2.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class PacienteTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Paciente getPacienteSample1() {
        return new Paciente().id(1L).name("name1").age(1);
    }

    public static Paciente getPacienteSample2() {
        return new Paciente().id(2L).name("name2").age(2);
    }

    public static Paciente getPacienteRandomSampleGenerator() {
        return new Paciente().id(longCount.incrementAndGet()).name(UUID.randomUUID().toString()).age(intCount.incrementAndGet());
    }
}
