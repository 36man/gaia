package org.bravo.gaia.id.generator.impl;

import java.util.Base64;
import java.util.concurrent.ThreadLocalRandom;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import org.bravo.gaia.id.generator.IdGenerator;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RandomIdGenerator implements IdGenerator<String> {

    // java -Dgenerator.random.instance-id=8
    public static final RandomIdGenerator GLOBAL = new RandomIdGenerator(
            Integer.getInteger("generator.random.instance-id", ThreadLocalRandom.current().nextInt(1,127)).byteValue()
    );

    private final static ThreadLocal<byte[]> HOLDER = new ThreadLocal<byte[]>() {
        @Override
        protected byte[] initialValue() {
            return new byte[24];
        }
    };

    private final byte instanceId;

    public static RandomIdGenerator create(byte instanceId) {
        return new RandomIdGenerator(instanceId);
    }

    public String generate() {
        long now = System.currentTimeMillis();
        byte[] value = HOLDER.get();
        value[0] = instanceId;

        value[1] = (byte) (now >>> 32);
        value[2] = (byte) (now >>> 24);
        value[3] = (byte) (now >>> 16);
        value[4] = (byte) (now >>> 8);
        value[5] = (byte) (now);

        nextBytes(value, 6, 8);
        nextBytes(value, 8, 16);
        nextBytes(value, 16, 24);

        return Base64.getUrlEncoder().encodeToString(value);
    }


    private static void nextBytes(byte[] bytes, int offset, int len) {
        ThreadLocalRandom random = ThreadLocalRandom.current();

        for (int i = offset; i < len; ) {
            for (int rnd = random.nextInt(),
                 n = Math.min(len - i, Integer.SIZE / Byte.SIZE);
                 n-- > 0; rnd >>= Byte.SIZE) {
                bytes[i++] = (byte) rnd;
            }
        }

    }

}