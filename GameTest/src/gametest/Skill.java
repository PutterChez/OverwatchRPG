package gametest;

//Skills for entities to use

import java.awt.Color;

public class Skill {

    protected String skillName;
    protected double skillPower; // Percentage
    protected int mpCost;
    protected int accuracy;
    protected String description = "";
    protected Color color = Color.WHITE;
    protected boolean ultimate = false;

    public Skill(String name, double atkPower, int mpCost, int accuracy) {
        this.skillName = name;
        this.skillPower = atkPower;
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
        return skillPower;
    }

    public void setAtkPower(double atkPower) {
        this.skillPower = atkPower;
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

    public void act() {

    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isUltimate() {
        return ultimate;
    }

    public void setUltimate(boolean ultimate) {
        this.ultimate = ultimate;
    }
    
}
