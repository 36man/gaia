/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.bravo.gaia.commons.util;

import com.google.common.base.Predicate;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;
import static java.util.Collections.emptySet;
import static java.util.Collections.unmodifiableSet;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;
import static org.bravo.gaia.commons.util.CollectionUtils.flip;
import static org.bravo.gaia.commons.util.CollectionUtils.ofSet;
import static org.bravo.gaia.commons.util.Streams.filterAll;

public class ClassUtils {

    public static final String ARRAY_SUFFIX = "[]";

    public static final Set<Class<?>> SIMPLE_TYPES = ofSet(
            Void.class,
            Boolean.class,
            Character.class,
            Byte.class,
            Short.class,
            Integer.class,
            Long.class,
            Float.class,
            Double.class,
            String.class,
            BigDecimal.class,
            BigInteger.class,
            Date.class,
            Object.class
    );

    private static final String INTERNAL_ARRAY_PREFIX = "[L";

    private static final Map<String, Class<?>> PRIMITIVE_TYPE_NAME_MAP = new HashMap<>(32);

    private static final Map<Class<?>, Class<?>> PRIMITIVE_WRAPPER_TYPE_MAP = new HashMap<>(16);

    static {
        PRIMITIVE_WRAPPER_TYPE_MAP.put(Boolean.class, boolean.class);
        PRIMITIVE_WRAPPER_TYPE_MAP.put(Byte.class, byte.class);
        PRIMITIVE_WRAPPER_TYPE_MAP.put(Character.class, char.class);
        PRIMITIVE_WRAPPER_TYPE_MAP.put(Double.class, double.class);
        PRIMITIVE_WRAPPER_TYPE_MAP.put(Float.class, float.class);
        PRIMITIVE_WRAPPER_TYPE_MAP.put(Integer.class, int.class);
        PRIMITIVE_WRAPPER_TYPE_MAP.put(Long.class, long.class);
        PRIMITIVE_WRAPPER_TYPE_MAP.put(Short.class, short.class);
        PRIMITIVE_WRAPPER_TYPE_MAP.put(Void.class, void.class);

        Set<Class<?>> primitiveTypeNames = new HashSet<>(32);
        primitiveTypeNames.addAll(PRIMITIVE_WRAPPER_TYPE_MAP.values());
        primitiveTypeNames.addAll(Arrays
                .asList(boolean[].class, byte[].class, char[].class, double[].class,
                        float[].class, int[].class, long[].class, short[].class));
        for (Class<?> primitiveTypeName : primitiveTypeNames) {
            PRIMITIVE_TYPE_NAME_MAP.put(primitiveTypeName.getName(), primitiveTypeName);
        }
    }

    private static final Map<Class<?>, Class<?>> WRAPPER_PRIMITIVE_TYPE_MAP = flip(PRIMITIVE_WRAPPER_TYPE_MAP);

    /**
     * Separator char for package
     */
    private static final char PACKAGE_SEPARATOR_CHAR = '.';

    public static Class<?> forNameWithThreadContextClassLoader(String name)
            throws ClassNotFoundException {
        return forName(name, Thread.currentThread().getContextClassLoader());
    }

    public static Class<?> forNameWithCallerClassLoader(String name, Class<?> caller)
            throws ClassNotFoundException {
        return forName(name, caller.getClassLoader());
    }

    public static ClassLoader getCallerClassLoader(Class<?> caller) {
        return caller.getClassLoader();
    }

    public static ClassLoader getClassLoader(Class<?> clazz) {
        ClassLoader cl = null;
        if (!clazz.getName().startsWith("org.apache.dubbo")) {
            cl = clazz.getClassLoader();
        }
        if (cl == null) {
            try {
                cl = Thread.currentThread().getContextClassLoader();
            } catch (Exception ignored) {
                // Cannot access thread context ClassLoader - falling back to system class loader...
            }
            if (cl == null) {
                // No thread context class loader -> use class loader of this class.
                cl = clazz.getClassLoader();
                if (cl == null) {
                    // getClassLoader() returning null indicates the bootstrap ClassLoader
                    try {
                        cl = ClassLoader.getSystemClassLoader();
                    } catch (Exception ignored) {
                        // Cannot access system ClassLoader - oh well, maybe the caller can live with null...
                    }
                }
            }
        }

        return cl;
    }

    public static ClassLoader getClassLoader() {
        return getClassLoader(ClassUtils.class);
    }

    public static Class<?> forName(String name) throws ClassNotFoundException {
        return forName(name, getClassLoader());
    }

    public static Class<?> forName(String name, ClassLoader classLoader)
            throws ClassNotFoundException, LinkageError {

        Class<?> clazz = resolvePrimitiveClassName(name);
        if (clazz != null) {
            return clazz;
        }

        // "java.lang.String[]" style arrays
        if (name.endsWith(ARRAY_SUFFIX)) {
            String elementClassName = name.substring(0, name.length() - ARRAY_SUFFIX.length());
            Class<?> elementClass = forName(elementClassName, classLoader);
            return Array.newInstance(elementClass, 0).getClass();
        }

        // "[Ljava.lang.String;" style arrays
        int internalArrayMarker = name.indexOf(INTERNAL_ARRAY_PREFIX);
        if (internalArrayMarker != -1 && name.endsWith(";")) {
            String elementClassName = null;
            if (internalArrayMarker == 0) {
                elementClassName = name
                        .substring(INTERNAL_ARRAY_PREFIX.length(), name.length() - 1);
            } else if (name.startsWith("[")) {
                elementClassName = name.substring(1);
            }
            Class<?> elementClass = forName(elementClassName, classLoader);
            return Array.newInstance(elementClass, 0).getClass();
        }

        ClassLoader classLoaderToUse = classLoader;
        if (classLoaderToUse == null) {
            classLoaderToUse = getClassLoader();
        }
        return classLoaderToUse.loadClass(name);
    }

    public static Class<?> resolvePrimitiveClassName(String name) {
        Class<?> result = null;
        // Most class names will be quite long, considering that they
        // SHOULD sit in a package, so a length check is worthwhile.
        if (name != null && name.length() <= 8) {
            // Could be a primitive - likely.
            result = (Class<?>) PRIMITIVE_TYPE_NAME_MAP.get(name);
        }
        return result;
    }

    public static String toShortString(Object obj) {
        if (obj == null) {
            return "null";
        }
        return obj.getClass().getSimpleName() + "@" + System.identityHashCode(obj);

    }

    public static String simpleClassName(Class<?> clazz) {
        if (clazz == null) {
            throw new NullPointerException("clazz");
        }
        String className = clazz.getName();
        final int lastDotIdx = className.lastIndexOf(PACKAGE_SEPARATOR_CHAR);
        if (lastDotIdx > -1) {
            return className.substring(lastDotIdx + 1);
        }
        return className;
    }

    public static boolean isPrimitive(Class<?> type) {
        return type != null && (type.isPrimitive() || isSimpleType(type));
    }

    public static boolean isSimpleType(Class<?> type) {
        return SIMPLE_TYPES.contains(type);
    }

    public static boolean isTypeMatch(Class<?> type, String value) {
        if ((type == boolean.class || type == Boolean.class)
                && !("true".equals(value) || "false".equals(value))) {
            return false;
        }
        return true;
    }

    public static Set<Class<?>> getAllSuperClasses(Class<?> type, Predicate<Class<?>>... classFilters) {

        Set<Class<?>> allSuperClasses = new LinkedHashSet<>();

        Class<?> superClass = type.getSuperclass();
        while (superClass != null) {
            // add current super class
            allSuperClasses.add(superClass);
            superClass = superClass.getSuperclass();
        }

        return unmodifiableSet(filterAll(allSuperClasses, classFilters));
    }


    public static Set<Class<?>> getAllInterfaces(Class<?> type, Predicate<Class<?>>... interfaceFilters) {
        if (type == null || type.isPrimitive()) {
            return emptySet();
        }

        Set<Class<?>> allInterfaces = new LinkedHashSet<>();
        Set<Class<?>> resolved = new LinkedHashSet<>();
        Queue<Class<?>> waitResolve = new LinkedList<>();

        resolved.add(type);
        Class<?> clazz = type;
        while (clazz != null) {

            Class<?>[] interfaces = clazz.getInterfaces();

            if (isNotEmpty(interfaces)) {
                // add current interfaces
                Arrays.stream(interfaces)
                        .filter(resolved::add)
                        .forEach(cls -> {
                            allInterfaces.add(cls);
                            waitResolve.add(cls);
                        });
            }

            // add all super classes to waitResolve
            getAllSuperClasses(clazz)
                    .stream()
                    .filter(resolved::add)
                    .forEach(waitResolve::add);

            clazz = waitResolve.poll();
        }

        return filterAll(allInterfaces, interfaceFilters);
    }

    public static Set<Class<?>> getAllInheritedTypes(Class<?> type, Predicate<Class<?>>... typeFilters) {
        // Add all super classes
        Set<Class<?>> types = new LinkedHashSet<>(getAllSuperClasses(type, typeFilters));
        // Add all interface classes
        types.addAll(getAllInterfaces(type, typeFilters));
        return unmodifiableSet(types);
    }


    public static boolean isAssignableFrom(Class<?> superType, Class<?> targetType) {
        // any argument is null
        if (superType == null || targetType == null) {
            return false;
        }
        // equals
        if (Objects.equals(superType, targetType)) {
            return true;
        }
        // isAssignableFrom
        return superType.isAssignableFrom(targetType);
    }

    public static boolean isPresent(String className, ClassLoader classLoader) {
        try {
            forName(className, classLoader);
        } catch (Exception ignored) { // Ignored
            return false;
        }
        return true;
    }

    public static Class<?> resolveClass(String className, ClassLoader classLoader) {
        Class<?> targetClass = null;
        try {
            targetClass = forName(className, classLoader);
        } catch (Exception ignored) { // Ignored
        }
        return targetClass;
    }

    public static boolean isGenericClass(Class<?> type) {
        return type != null && !void.class.equals(type) && !Void.class.equals(type);
    }

    public static boolean hasMethods(Method[] methods) {
        if (methods == null || methods.length == 0) {
            return false;
        }
        for (Method m : methods) {
            if (m.getDeclaringClass() != Object.class) {
                return true;
            }
        }
        return false;
    }

    private static final String[] OBJECT_METHODS = new String[]{"getClass", "hashCode", "toString", "equals"};

    public static String[] getMethodNames(Class<?> tClass) {
        if (tClass == Object.class) {
            return OBJECT_METHODS;
        }
        Method[] methods = Arrays.stream(tClass.getMethods())
            .collect(Collectors.toList())
            .toArray(new Method[] {});
        List<String> mns = new ArrayList<>(); // method names.
        boolean hasMethod = hasMethods(methods);
        if (hasMethod) {
            for (Method m : methods) {
                //ignore Object's method.
                if (m.getDeclaringClass() == Object.class) {
                    continue;
                }
                String mn = m.getName();
                mns.add(mn);
            }
        }
        return mns.toArray(new String[0]);
    }

    public static String[] getDeclaredMethodNames(Class<?> tClass) {
        if (tClass == Object.class) {
            return OBJECT_METHODS;
        }
        Method[] methods = Arrays.stream(tClass.getMethods())
            .collect(Collectors.toList())
            .toArray(new Method[] {});
        List<String> dmns = new ArrayList<>(); // method names.
        boolean hasMethod = hasMethods(methods);
        if (hasMethod) {
            for (Method m : methods) {
                //ignore Object's method.
                if (m.getDeclaringClass() == Object.class) {
                    continue;
                }
                String mn = m.getName();
                if (m.getDeclaringClass() == tClass) {
                    dmns.add(mn);
                }
            }
        }
        dmns.sort(Comparator.naturalOrder());
        return dmns.toArray(new String[0]);
    }
}
