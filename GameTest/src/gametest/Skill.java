package gametest;

public class Skill {
    private String skillName;
    private double atkPower; // Percentage
    private int mpCost;
    private int accuracy;
    private String description = "";

    public Skill(String name, double atkPower, int mpCost, int accuracy){
        this.skillName = name;
        this.atkPower = atkPower;
        this.mpCost = mpCost;
        this.accuracy = accuracy;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public double getAtkPower() {
        return atkPower;
    }

    public void setAtkPower(double atkPower) {
        this.atkPower = atkPower;
    }

    public int getMpCost() {
        return mpCost;
    }

    public void setMpCost(int mpCost) {
        this.mpCost = mpCost;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void act(){
        
    }
}
