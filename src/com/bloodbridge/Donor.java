package com.bloodbridge;

public class Donor {
    private int id;
    private String name;
    private String bloodGroup;
    private int age;
    private String contact;

    public Donor(String name, String bloodGroup, int age, String contact) {
        this.name = name;
        this.bloodGroup = bloodGroup;
        this.age = age;
        this.contact = contact;
    }

    public Donor(int id, String name, String bloodGroup, int age, String contact) {
        this.id = id;
        this.name = name;
        this.bloodGroup = bloodGroup;
        this.age = age;
        this.contact = contact;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getBloodGroup() { return bloodGroup; }
    public int getAge() { return age; }
    public String getContact() { return contact; }

    @Override
    public String toString() {
        return id + " | " + name + " | " + bloodGroup + " | age:" + age + " | " + contact;
    }
}
