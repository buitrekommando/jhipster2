package com.aiep.prueba2.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CentroSaludTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static CentroSalud getCentroSaludSample1() {
        return new CentroSalud().id(1L).name("name1").address("address1");
    }

    public static CentroSalud getCentroSaludSample2() {
        return new CentroSalud().id(2L).name("name2").address("address2");
    }

    public static CentroSalud getCentroSaludRandomSampleGenerator() {
        return new CentroSalud().id(longCount.incrementAndGet()).name(UUID.randomUUID().toString()).address(UUID.randomUUID().toString());
    }
}
