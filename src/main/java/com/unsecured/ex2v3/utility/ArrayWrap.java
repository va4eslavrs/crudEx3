package com.unsecured.ex2v3.utility;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ArrayWrap {
    public String operation;
    public List<String> ids = new ArrayList();

    public List<Integer> toIntegerList() {
        return getIds().stream().map(Integer::parseInt).collect(Collectors.toList());
    }
}
