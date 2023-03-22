package ru.rest.demo.rest.exception;

import lombok.*;
import org.apache.commons.lang3.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class ErrorMessage {
    @NonNull
    String code;
    @NonNull
    String message;
    List<Object> params = new ArrayList<>();
    List<ErrorMessage> children = new ArrayList<>();

    public void addParam(String msg) {
        this.params.add(msg);
    }

    public void addAllParams(List<Object> msg) {
        if (!ObjectUtils.isEmpty(msg)) this.params.addAll(msg);
    }
}
