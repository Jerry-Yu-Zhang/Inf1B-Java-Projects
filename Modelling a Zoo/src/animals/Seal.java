package animals;

public class Seal extends Animal{
    private String nickname;

    public Seal(String nickname) {
        this.nickname = nickname;
    }
    @Override
    public String getNickname() {
        return nickname;
    }

    @Override
    public boolean isCompatibleWith(Animal animal) {
        if (animal instanceof Shark) {
            return false;
        }else if (animal instanceof Buzzard
                || animal instanceof Gazelle
                || animal instanceof Lion
                || animal instanceof Parrot
                || animal instanceof Zebra) {
            return false;
        }else {
            return true;
        }
    }
}
