package gametest;

public class Action {
    public static void attack(Entity attacker, Skill attackerSkill,  Entity target)
    {
        //Example
        target.HP -= (attacker.attack * attackerSkill.getAtkPower());
        attacker.MP -= attackerSkill.mpCost;
    }
}
