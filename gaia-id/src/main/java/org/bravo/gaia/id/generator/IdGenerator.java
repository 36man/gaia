/*
 *
 *  * Copyright 2020 http://www.hswebframework.org
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *     http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package org.bravo.gaia.id.generator;


import org.bravo.gaia.commons.util.DigestUtil;
import org.bravo.gaia.id.generator.impl.SnowflakeIdGenerator;
import org.bravo.gaia.id.generator.impl.RandomIdGenerator;

@FunctionalInterface
public interface IdGenerator<T> {
    T generate();

    /**
     * 空ID生成器
     */
    IdGenerator<?> NULL = () -> null;

    @SuppressWarnings("unchecked")
    static <T> IdGenerator<T> getNullGenerator() {
        return (IdGenerator<T>) NULL;
    }

    /**
     * 使用UUID生成id
     */
    IdGenerator<String> UUID = () -> java.util.UUID.randomUUID().toString();

    /**
     * 随机字符
     */
    IdGenerator<String> RANDOM = RandomIdGenerator.GLOBAL;

    /**
     * md5(uuid())
     */
    IdGenerator<String> MD5 = () -> DigestUtil.md5Hex(UUID.generate());

    /**
     * 雪花算法
     */
    IdGenerator<Long> SNOW_FLAKE = SnowflakeIdGenerator.getInstance()::nextId;

    /**
     * 雪花算法转String
     */
    IdGenerator<String> SNOW_FLAKE_STRING = () -> String.valueOf(SNOW_FLAKE.generate());

    /**
     * 雪花算法的16进制
     */
    IdGenerator<String> SNOW_FLAKE_HEX = () -> Long.toHexString(SNOW_FLAKE.generate());
}