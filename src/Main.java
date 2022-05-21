import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> surnames = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();

        for (int i = 0; i < 1_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    surnames.get(new Random().nextInt(surnames.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }

        //Найти количество несовершеннолетних (т.е. людей младше 18 лет)
        long count = persons.stream()
                .filter(person -> person.getAge() < 18)
                .count();
        System.out.println(count);


        //Получить список фамилий призывников (т.е. мужчин от 18 и до 27 лет)

        List<String> recruits = persons.stream()
                .filter(person -> person.getSex().name().equals("MAN"))
                .filter(person -> person.getAge() >= 18)
                .filter(person -> person.getAge() <= 27)
                .map(Person::getSurname)
                .collect(Collectors.toList());

        System.out.println(recruits.size());

        //Получить отсортированный по фамилии список потенциально работоспособных людей
        // с высшим образованием в выборке (т.е. людей с высшим образованием
        // от 18 до 60 лет для женщин и до 65 лет для мужчин).


        //Создаем коллекцию для выборки
        Collection<Person> fitToWork = new ArrayList<>();

        //создаем предикаты для последующего отсеивания женщин старше 60
        Predicate<Person> isWoman = person -> person.getSex().name().equals("WOMAN");
        Predicate<Person> overSixty = person -> person.getAge() > 60;

        //получаем отсортированный список согласно указанных условий
        persons.stream()
                .filter(person -> person.getAge() >= 18)
                .filter(person -> person.getAge() <= 65)
                .filter(person -> person.getEducation().name().equals("HIGHER"))
                .filter(isWoman.and(overSixty).negate())
                .sorted(Comparator.comparing(Person::getSurname))
                .forEach(fitToWork::add);

        System.out.println(fitToWork.size());

        for (Person person : fitToWork) {
            System.out.println(person);
        }

    }
}
