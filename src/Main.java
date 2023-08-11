import java.beans.Customizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class Main {


    public static void main(String[] args) {

        String name = "Tommy";
        Function<String, String> uCase = String::toUpperCase;
        System.out.println(uCase.apply(name));

        Function<String, String> lastName = s -> s.concat("& Jerry");
        Function<String, String> uCaseLastName = uCase.andThen(lastName);
        System.out.println(uCaseLastName.apply(name));

        uCaseLastName = uCase.compose(lastName);
        System.out.println(uCaseLastName.apply(name));

        Function<String, String[]> f0 = uCase
                .andThen(s -> s.concat(" & Jerry"))
                .andThen(s -> s.split(" "));
        System.out.println(Arrays.toString(f0.apply(name)));

        Function<String, String> f1 = uCase
                .andThen(s -> s.concat(" Jerry"))
                .andThen(s -> s.split(" "))
                .andThen(s -> s[1].toUpperCase() + ", " + s[0]);
        System.out.println(f1.apply(name));

        Function<String, Integer> f2 = uCase
                .andThen(s -> s.concat(" & Jerry"))
                .andThen(s -> s.split(" "))
                .andThen(s -> String.join(", ", s))
                .andThen(String::length);
        System.out.println(f2.apply(name));


        String[] names = {"John", "Ben", "Android"};
        Consumer<String> s0 = s -> System.out.print(s.charAt(0));
        Consumer<String> s1 = System.out::println;
        Arrays.asList(names).forEach(s0
                .andThen(s -> System.out.print(" - "))
                .andThen(s1));

        Predicate<String> p1 = s -> s.equals("Tommy");
        Predicate<String> p2 = s -> s.equalsIgnoreCase("Tommy");
        Predicate<String> p3 = s -> s.startsWith("t");
        Predicate<String> p4 = s -> s.endsWith("n");

        Predicate<String> combine1 = p1.or(p2);
        System.out.println("combined1 = "+ combine1.test(name));

        Predicate<String> combine2 = p3.or(p4);
        System.out.println("combined2 = "+ combine2.test(name));

        Predicate<String> combine3 = p3.or(p4).negate();
        System.out.println("combined3 = "+ combine3.test(name));

        record Person(String firstName, String lastName){}
        List<Person> list = new ArrayList<>(Arrays.asList(
                new Person("Donald","Duck"),
                new Person("Petter","Pan"),
                new Person("Mickey","Mouse")
        ));

        list.sort((o1,o2)-> o1.lastName.compareTo(o2.lastName));
        list.forEach(System.out::println);

        System.out.println("-".repeat(35));
        list.sort(Comparator.comparing(Person::lastName));
        list.forEach(System.out::println);

        System.out.println("-".repeat(35));
        list.sort(Comparator.comparing(Person::lastName)
                .thenComparing(Person::firstName));
        list.forEach(System.out::println);

        System.out.println("-".repeat(35));
        list.sort(Comparator.comparing(Person::lastName)
                .thenComparing(Person::firstName).reversed());
        list.forEach(System.out::println);
    }

}
