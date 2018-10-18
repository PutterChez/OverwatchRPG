package gametest;
import java.util.*;

public class Action {
    public static void attack(Entity attacker, Skill attackerSkill,  Entity target)
    {
        //Example
        Random randHit = new Random(System.currentTimeMillis());
        int attackHitRate = attackerSkill.accuracy - target.evasion;

        if (randHit.nextInt(100) < attackHitRate)
        {
            //Hit
            target.HP -= (attacker.attack * attackerSkill.getAtkPower());
            attacker.MP -= attackerSkill.mpCost;
        }
        else
        {
            //Miss
            //Do something IDK
        }
    }
}
