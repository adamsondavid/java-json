package io.github.adamsondavid.json.util;

public class People{
    private String name;
    private float age;
    private boolean adult;

    public People(){
    }

    public People(String name, float age, boolean adult){
        this.name = name;
        this.age = age;
        this.adult = adult;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        People that = (People) obj;
        return name.equals(that.name) && age == age && adult == adult;
    }
}
