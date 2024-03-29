package ru.rest.demo.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CustomUtils {

    /**
     * Метод для группировки листа по определенному условию.
     * list - исходный список
     * keyFunction - функция группировки*/
    public static <E, K> Map<K, List<E>> groupListBy(List<E> list, Function<E, K> keyFunction) {
        return Optional.ofNullable(list)
                .orElseGet(ArrayList::new)
                .stream()
                .collect(Collectors.groupingBy(keyFunction));
    }
}
