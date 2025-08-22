package fieg.core.util;

import java.util.ArrayList;
import java.util.List;

public class UtilColecoes {

    public static <T> List<List<T>> separaEmBatch(List<T> lista, int batchSize) {
        if (batchSize <= 0) {
            throw new IllegalArgumentException("O tamanho do batch deve ser maior que zero");
        }

        List<List<T>> batched = new ArrayList<>();
        for (int i = 0; i < lista.size(); i += batchSize) {
            int end = Math.min(i + batchSize, lista.size());
            batched.add(new ArrayList<>(lista.subList(i, end)));
        }

        return batched;
    }
}
