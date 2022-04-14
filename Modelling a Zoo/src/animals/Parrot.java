package animals;

public class Parrot extends Animal{
    private String nickname;

    public Parrot(String nickname) {
        this.nickname = nickname;
    }
    @Override
    public String getNickname() {
        return nickname;
    }

    @Override
    public boolean isCompatibleWith(Animal animal) {
        if (animal instanceof Buzzard) {
            return false;
        }else if (animal instanceof Gazelle
                || animal instanceof Lion
                || animal instanceof Seal
                || animal instanceof Shark
                || animal instanceof Starfish
                || animal instanceof Zebra) {
            return false;
        }else {
            return true;
        }
    }
}
