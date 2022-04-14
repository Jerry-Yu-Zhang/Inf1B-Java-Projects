package animals;

public class Buzzard extends Animal{
    private String nickname;

    public Buzzard(String nickname) {
        this.nickname = nickname;
    }
    @Override
    public String getNickname() {
        return nickname;
    }

    @Override
    public boolean isCompatibleWith(Animal animal) {
        if (animal instanceof Parrot) {
            return false;
        }else if (animal instanceof Gazelle
                || animal instanceof Lion
                || animal instanceof Seal
                || animal instanceof Shark
                || animal instanceof Starfish) {
            return false;
        }else {
            return true;
        }
    }
}
