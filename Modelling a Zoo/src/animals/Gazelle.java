package animals;

public class Gazelle extends Animal{
    private String nickname;

    public Gazelle(String nickname) {
        this.nickname = nickname;
    }
    @Override
    public String getNickname() {
        return nickname;
    }

    @Override
    public boolean isCompatibleWith(Animal animal) {
        if (animal instanceof Lion) {
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
