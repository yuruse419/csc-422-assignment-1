public class Pet {
  // instance fields
  String name;
  int age;

  // constructor
  public Pet(String name, int age) {
    this.name = name;
    this.age = age;
  }

  // name getter
  public String getName() {
    return this.name;
  }

  // age getter
  public int getAge() {
    return this.age;
  }

  // name setter
  public void setName(String name) {
    this.name = name;
  }

  // age setter
  public void setAge(int age) {
    this.age = age;
  }
}