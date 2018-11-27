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
        
        //Prevent Overheal
        if(target.HP > target.maxHP){
            target.HP = target.maxHP;
        }
    }
    
    public static void groupHealing(BattlePhaseEntity healer, Skill healerSkill, Party party){
        for(int i = 0; i < party.memberList.size();i++){
            party.memberList.get(i).entity.HP += (healer.attack * healerSkill.skillPower);
            
            //Prevent Overheal
            if(party.memberList.get(i).entity.HP > party.memberList.get(i).entity.maxHP){
                party.memberList.get(i).entity.HP = party.memberList.get(i).entity.maxHP;
            }
        
            //Prevent Revive
            if(party.memberList.get(i).entity.HP < 0){
                party.memberList.get(i).entity.HP = 0;
            }
        }
        
        healer.MP -= healerSkill.mpCost;
    }
}
