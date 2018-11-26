package gametest;

import java.util.*;

public class Action {

    public static void attack(BattlePhaseEntity attacker, Skill attackerSkill, BattlePhaseEntity target) {
        
        //Example
        Random randHit = new Random(System.currentTimeMillis());
        int attackHitRate = attackerSkill.accuracy - target.evasion;

        attacker.MP -= attackerSkill.mpCost;
        if (randHit.nextInt(100) < attackHitRate) {
            //Hit
            target.HP -= (attacker.attack * (attackerSkill.getAtkPower() / 100) - target.defense);
        } else {
            System.out.println(attacker.getCharName() + " missed!");
            attacker.setMissed(true);
            //Miss
            //Do something, IDK
        }
    }

    public static void healing(BattlePhaseEntity healer, Skill healerSkill, BattlePhaseEntity target) {
        target.HP += (healer.attack * healerSkill.skillPower);
        healer.MP -= healerSkill.mpCost;
    }
}
