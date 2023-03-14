package az.enjooy.util;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public class ListUtil {
    public static <T> Page<T> convertToPage(List<T> list, int size, int page) {
        int totalPages = list.size() / size;
        PageRequest pageable = PageRequest.of(page, size);
        int max = page >= totalPages ? list.size() : size * (page + 1);
        int min = page > totalPages ? max : size * page;
        return new PageImpl<>(list.subList(min, max), pageable, list.size());
    }
}