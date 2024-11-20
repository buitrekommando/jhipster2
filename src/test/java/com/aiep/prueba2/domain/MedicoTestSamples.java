package com.aiep.prueba2.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class MedicoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Medico getMedicoSample1() {
        return new Medico().id(1L).name("name1").specialty("specialty1");
    }

    public static Medico getMedicoSample2() {
        return new Medico().id(2L).name("name2").specialty("specialty2");
    }

    public static Medico getMedicoRandomSampleGenerator() {
        return new Medico().id(longCount.incrementAndGet()).name(UUID.randomUUID().toString()).specialty(UUID.randomUUID().toString());
    }
}
