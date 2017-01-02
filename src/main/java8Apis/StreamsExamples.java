package main.java8Apis;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StreamsExamples {

    public static void main(String[] args) {
        // distinct

        // primitive
        System.out.println(IntStream.of(1, 2, 3, 1, 4).distinct().count());

        System.out.println(Arrays.asList(1, 2, 3, 4, 1).parallelStream().distinct().count());

        // Pojos
        class Book {
            private String name;
            private List<String> authors;

            public Book(String name, List<String> authors) {
                this.name = name;
                this.authors = authors;
            }
        }

        final Book book1 = new Book("A", Arrays.asList("Anuj", "Jain", "Someone"));
        final Book book2 = new Book("B", Arrays.asList("Anuj", "Someone"));
        final Book book3 = new Book("C", Arrays.asList("Someone"));

        final List<Book> books = IntStream.range(0, 100000)
                .mapToObj(value -> {
                    final String index = String.valueOf(value);
                    return new Book(index, Arrays.asList("index"));
                }).collect(Collectors.toList());





    }
}
