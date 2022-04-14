package animals;

public class Lion extends Animal{
    private String nickname;

    public Lion(String nickname) {
        this.nickname = nickname;
    }
    @Override
    public String getNickname() {
        return nickname;
    }

    @Override
    public boolean isCompatibleWith(Animal animal) {
        if (animal instanceof Zebra || animal instanceof Gazelle) {
            return false;
        }else if (animal instanceof Buzzard
                || animal instanceof Parrot
                || animal instanceof Seal
                || animal instanceof Shark
                || animal instanceof Starfish) {
            return false;
        }else {
            return true;
        }
    }
}
