package com.lambda;

import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        // Create consumer that input Stream of strings, and print items in a line
        Consumer<Stream<String>> printStream = s -> System.out.println(s.collect(Collectors.joining(", ")));

        // Scenario: Print all evens
        // Output: 2, 4, 6, 8
        printStream.accept(IntStream.range(1, 10)
                                      .filter(x -> x % 2 == 0)
                                      .mapToObj(String::valueOf));

        // Scenario: Print squares
        // Output: 1, 4, 9, 16, 25, 36, 49, 64, 81
        printStream.accept(IntStream.range(1, 10)
                                     .map(x -> x * x)
                                     .mapToObj(String::valueOf));

        // Scenario: Combine and sort in descending order
        // Output: 6, 5, 4, 3, 2, 1
        printStream.accept(Stream.concat(Stream.of(1, 3, 5),
                                         Stream.of(2, 4, 6))
                                  .sorted(Comparator.reverseOrder())
                                  .map(String::valueOf));

        // Scenario: Calculate binary by coefficients
        // Example: If the input is 1, 0, 1, 1
        //          output should be 1*2^0 + 1*2^2 + 1*2^3
        // Output: 13
        {
            var coeffs = new int[] {1, 0, 1, 1};
            var powers = IntStream.range(0, coeffs.length)
                    .map(x -> (int) Math.pow(2, x))
                    .toArray();
            var result = IntStream.range(0, coeffs.length)
                    .map(i -> coeffs[i] * powers[i])
                    .sum();
            System.out.println(result);
        }

        // Scenario: List employees by gender
        // Output: {Female=Jane, Robert, Male=John, Crystal, Sharon}
        {
            class Employee{
                private String name;
                private int age;
                private String gender;

                public Employee(String name, int age, String gender){
                    this.name = name;
                    this.age = age;
                    this.gender = gender;
                }

                public String getName() {return name;}
                public int getAge() {return age;}
                public String getGender() {return gender;}

                @Override
                public String toString() {
                    return String.format("Name: %s, Age: %d", name, age);
                }

            }

            var list = List.of(
                    new Employee("John", 32, "Male"),
                    new Employee("Jane", 24, "Female"),
                    new Employee("Crystal", 30, "Male"),
                    new Employee("Robert", 50, "Female"),
                    new Employee("Sharon", 41, "Male")
            );

            var result = list.stream()
                             .collect(Collectors.groupingBy(
                                x -> x.getGender(),
                                Collectors.mapping(
                                        x -> x.getName(),
                                        Collectors.joining(", ")
                                )
                             ));

            System.out.println(result);
        }
    }
}
