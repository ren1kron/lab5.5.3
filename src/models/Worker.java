package models;

import managers.CollectionManager;
import utility.Element;
import utility.Validatable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * This class contents information about Worker – element of collection
 * @author ren1kron
 */

public class Worker extends Element implements Validatable {
    public Worker() { }
    public Worker(Integer key, int id, String name, Organization organization, Position position, Status status, float salary,
                  Coordinates coordinates, Date creationDate, LocalDate startDate) {
        this.key = key;
        this.id = id;
        this.name = name;
        this.organization = organization;
        this.position = position;
        this.status = status;
        this.salary = salary;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.startDate = startDate;
    }

//    public Worker(Integer key, int id, String name, Coordinates coordinates, float salary, LocalDate startDate, Position position, Status status, Organization organization) {
//        super();
//    }

public Worker(Integer key, String name, Organization organization, Position position, Status status, float salary, Coordinates coordinates,
              java.time.LocalDate startDate) {
        this.key = key;
        this.id = ++nextId; // как будто можно просто добавлять значение здесь каждый раз и всё будет ок. Но посмотрим,
        // может, нужно будет убрать "++"
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = new Date();
        this.salary = salary;
        this.startDate = startDate;
        this.position = position;
        this.status = status;
        this.organization = organization;
    }

    public Worker(Integer key, int id, String name, Organization organization, Position position, Status status, float salary, Coordinates coordinates,
                  java.time.LocalDate startDate) {
        this.key = key;
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = new Date();
        this.salary = salary;
        this.startDate = startDate;
        this.position = position;
        this.status = status;
        this.organization = organization;
    }

    /**
     * Updates value of next ID
     * @param collectionManager manager of collection which contains workers
     */
    @Deprecated
    public static void updateNextId(CollectionManager collectionManager) {
        nextId = collectionManager.maxId();
    }

    public static String[] toArray(Worker worker) {
        var list = new ArrayList<String>();
        list.add(String.valueOf(worker.getKey()));
        list.add(String.valueOf(worker.getId()));
        list.add(worker.getName());
        list.add(worker.getOrganization() == null ? "null" : worker.getOrganization().toString());
        list.add(worker.getPosition().toString());
        list.add(worker.getStatus() == null ? "null" : worker.getStatus().toString());
        list.add(String.valueOf(worker.getSalary()));
        list.add(worker.getCoordinates().toString());
        list.add(String.valueOf(worker.getCreationDate()));
        list.add(worker.getStartDate().format(DateTimeFormatter.ISO_DATE));
        return list.toArray(new String[0]);
    }

    @Deprecated
    public static Worker fromArray(String[] arr) {
        int key;
        int id;
        String name;
        Organization organization;
        Position position;
        Status status;
        float salary;
        Coordinates coordinates;
        Date creationDate;
        LocalDate startDate;
        try {
            key = Integer.parseInt(arr[0]);
            id = Integer.parseInt(arr[1]);
            name = arr[2];
            organization = new Organization(arr[3]);
            position = Position.valueOf(arr[4]);
            status = Status.valueOf(arr[5]);
            salary = Float.parseFloat(arr[6]);
            coordinates = new Coordinates(arr[7]);
            SimpleDateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");
            try {
                creationDate = formatter.parse(arr[8]);
            } catch (ParseException e) {
                creationDate = null;
            }
            try {
                startDate = LocalDate.parse(arr[9], DateTimeFormatter.ISO_DATE);
            } catch (DateTimeParseException e) {
                startDate = null;
            }
            return new Worker(key, id, name, organization, position, status, salary, coordinates, creationDate, startDate);
        } catch (ArrayIndexOutOfBoundsException e) {return null;}

    }
    private Integer key; // По ТЗ при вводе нового элемента нам необходимо ввести его ключ.
    // ID генерируется автоматически, остальные поля не уникальны. Поэтому я добавил данное поле, которое решит возникшую проблему.
    // Соответственно, key должен быть уникален и, для простоты ввода, быть больше 0

    private static int nextId = 0;
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение
    // этого поля должно генерироваться автоматически
    private String name; //Поле НЕ может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле НЕ может быть null
    private java.util.Date creationDate; //Поле НЕ может быть null, Значение этого поля должно генерироваться автоматически
    private float salary; //Значение поля должно быть больше 0
    private java.time.LocalDate startDate; //Поле НЕ может быть null
    private Position position; //Поле НЕ может быть null
    private Status status; //Поле МОЖЕТ быть null
    private Organization organization; //Поле МОЖЕТ быть null


    public void setId(int id) {
        this.id = id;
    }
    @Deprecated
    public void touchNextId() {
        nextId++;
    }

//    public
    @Override
    public int compareTo(Element element) {
        return Integer.compare(this.id, element.getId());
    }
//    public float compareTo(Worker worker) {
//        return (this.salary - worker.salary);
//    }
    @Override
    public boolean validate() {
        if (key <= 0) return false;
        if (id <= 0) return false;
        if (name==null || name.isEmpty()) return false;
        if (coordinates == null) return false;
        if (creationDate == null) return false;
        if (salary <= 0) return false;
        if (startDate == null) return false;
//        if (organization == null) return false;
        if (organization != null) if (!organization.validate()) return false;
        if (!coordinates.validate()) return false;
//        if (status == null) return false;
        return (position != null);
    }

    @Override
    public int getId() {
        return id;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Worker that = (Worker) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, salary, startDate, position, status, organization);
    }
    @Override
    public String toString() {
        return "key: "+ key + "; id: "+ id +"; name: "+ name + "; organization: {" + organization +"}; coordinates: {" + coordinates + "}; Date of appointment: "+ creationDate +
                "; salary: " + salary + "; Birthday: {" + startDate + "}; position: " + position + "; status: " + status;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreationDate() {
        return creationDate;
    }
//    public int getYear() {
//        return creationDate.getYear();
//    }
    public int getMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(creationDate);
        return calendar.get(Calendar.MONTH);
    }


    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }
}
