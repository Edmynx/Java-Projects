public class CharacterNode {
    private Character character;
    private Integer frequency;

    public CharacterNode(Character character, Integer frequency) {
        this.character = character;
        this.frequency = frequency;
    }

    public CharacterNode(Integer frequency){
        this.frequency = frequency;
    }


    public void setCharacter(Character character) {
        this.character = character;
    }

    public Character getCharacter() {
        return character;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    public Integer getFrequency() {
        return frequency;
    }
}
