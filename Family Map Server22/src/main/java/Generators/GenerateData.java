package Generators;

import Models.Person;

import java.util.*;

public class GenerateData {

  private ArrayList<Person> personsFamily;
  private final GenerateNames nameGenerator = new GenerateNames();
  private GenerateEvent eventGenerator;
  private String username;
  private Random r = new Random();

  public GenerationStorage PopulateGenerations(Person userPerson, int numGen) {
    username = userPerson.getAssociatedUsername();
    eventGenerator = new GenerateEvent(username);

    PopulateFamilyTree(userPerson, numGen);

    return new GenerationStorage(personsFamily, eventGenerator.GetEvents());
  }

  private void PopulateFamilyTree(Person root, int numGen) {
    int year = 2000 - (r.nextInt(10));

    personsFamily = new ArrayList<Person>();
    personsFamily.add(root);
    eventGenerator.RootBirth(root, year);

    PopulateParents(root, numGen - 1, year);
  }

  private void PopulateParents(Person rootPerson, int currentGen, int year) {
    int ageGap = 40;
    year = year - ageGap;
    Person father = PopulateFather(rootPerson);
    Person mother = PopulateMother(rootPerson);
    JoinFamily(rootPerson, father, mother);
    GenerateLifeEvents(father, mother, year);

    personsFamily.add(father);
    personsFamily.add(mother);

    if (currentGen != 0) {
      PopulateParents(father, currentGen - 1, year);
      PopulateParents(mother, currentGen - 1, year);
    }

  }

  private Person PopulateFather(Person child) {
    Person father = new Person();

    father.setAssociatedUsername(username);
    father.setFirstName(nameGenerator.MaleName());
    father.setLastName(child.getLastName());
    father.setGender("M");

    return father;
  }

  private Person PopulateMother(Person child) {
    Person mother = new Person();

    mother.setAssociatedUsername(username);
    mother.setFirstName(nameGenerator.FemaleName());
    mother.setLastName(child.getLastName());
    mother.setGender("F");

    return mother;
  }

  private void JoinFamily(Person child, Person father, Person mother) {
    father.setSpouseID(mother.getPersonID());
    mother.setSpouseID(father.getPersonID());
    child.setFatherID(father.getPersonID());
    child.setMotherID(mother.getPersonID());
  }

  private void GenerateLifeEvents(Person father, Person mother, int year) {
    eventGenerator.Birth(father, year);
    eventGenerator.Birth(mother, year);
    eventGenerator.Marriage(father, mother, year);
    eventGenerator.Death(father, year);
    eventGenerator.Death(mother, year);

    int randomEventChance = r.nextInt(2);
    if (randomEventChance == 0) {
      eventGenerator.Random(mother, year);
    } else {
      eventGenerator.Random(father, year);
    }
  }
}
