package Phecda.NBT;

public enum NBTSlotType {
    MainHand 	("mainhand"),
    OffHand 	("offhand"),
    Head 		("head"),
    Torso 		("torso"),
    Legs 		("legs"),
    Feet 		("feet");

    private final String name;       

    private NBTSlotType(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        // (otherName == null) check is not needed because name.equals(null) returns false 
        return name.equals(otherName);
    }

    public String toString() {
       return this.name;
    }
}