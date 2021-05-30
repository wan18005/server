package FakeDataAlgo;
import Models.Person;
import java.util.*;
/**
 * Make fake data for the server
 */
public class FakeData
{
  private ArrayList<Person> personArrayList;
  private final FakeName fakeName = new FakeName();
  private FakeEvent fakeEvent;
  private String username;
  private Random r = new Random();

  public FakeTemp Generatrion(Person person, int Gen)
  {
    username = person.getAssociatedUsername();
    fakeEvent = new FakeEvent(username);

    FamilyTree(person,Gen);

    return new FakeTemp(personArrayList,fakeEvent.GetEvents());
  }

  private void FamilyTree(Person root, int Gen)
  {
    int year = 2000 - (r.nextInt(10));
    personArrayList = new ArrayList<Person>();
    personArrayList.add(root);
    fakeEvent.RootBirth(root,year);

    Parents(root, Gen -1 , year);
  }
  private void Parents(Person Rperson, int now , int year)
  {
    int gap = 40;
    year = year - gap;
    Person father = Father(Rperson);
    Person mother = Mother(Rperson);
    Family (Rperson , father,mother);
    LifeEvents (father,mother,year);

    personArrayList.add(father);
    personArrayList.add(mother);
    if (now != 0)
    {
      Parents(father,now -1,year);
      Parents(mother, now -1 ,year);
    }
  }

  private Person Father(Person child)
  {
    Person dad = new Person();
    dad.setAssociatedUsername(username);
    dad.setFirstName(fakeName.FakeMale());
    dad.setLastName(child.getLastName());
    dad.setGender("M");
    return  dad;
  }

  private Person Mother(Person child)
  {
    Person mum = new Person();
    mum.setAssociatedUsername(username);
    mum.setFirstName(fakeName.FakeFemale());
    mum.setLastName(child.getLastName());
    mum.setGender("F");
    return  mum;
  }

  private void Family(Person child, Person father, Person mother)
  {
    father.setSpouseID(mother.getPersonID());
    mother.setSpouseID(father.getPersonID());
    child.setFatherID(father.getPersonID());
    child.setMotherID(mother.getPersonID());
  }

  private void LifeEvents(Person father, Person mother , int year)
  {
    fakeEvent.Birth(father,year);
    fakeEvent.Birth(mother,year);
    fakeEvent.Marriage(father,mother,year);
    fakeEvent.Death(father,year);
    fakeEvent.Death(mother,year);

    int Chance = r.nextInt(2);
    if (Chance == 0)
    {
      fakeEvent.Random(mother,year);
    }
    else
    {
      fakeEvent.Random(father,year);
    }

  }

}
