public class Team {
    private String mascotName;
    private int currentScore;

    public Team(String mascotName){
        this.mascotName = mascotName;
        currentScore = 0;
    }

    public String getMascotName(){
        return  mascotName;
    }

    public int getCurrentScore(){
        return currentScore;
    }

    public void addToScore(){
        currentScore += 2;
    }

    public static void main(String[] args) {
        Team alpha = new Team("alpha");
        Team beta = new Team("beta");
        alpha.addToScore();
        beta.addToScore();
        beta.addToScore();
        if (alpha.getCurrentScore() > beta.getCurrentScore()){
            System.out.println("Team " + alpha.getMascotName() + " is leading with " + alpha.getCurrentScore() + " points.");
        }
        else{
            System.out.println("Team " + beta.getMascotName() + " is leading with " + beta.getCurrentScore() + " points.");
        }
    }
}
