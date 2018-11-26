/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gametest;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 *
 * @author DELL
 */
public class Party {
    protected LinkedList<PartyMember> memberList;
    
    Party()
    {
        memberList = new LinkedList(){};
    }
    
    public void addMember(BattlePhaseEntity e, int position)
    {
        PartyMember p = new PartyMember(e, position);
        memberList.add(p);
    }
    
    public BattlePhaseEntity searchMemberByPartyPosition(int position)
    {
        return memberList.get(position).entity;
    }
    
    public void deleteMember(int position)
    {
        memberList.remove(position);
    }
    
    public int getTotalHP()
    {
        int totalHP = 0;
        for(PartyMember p : memberList)
            totalHP += p.entity.getHP();
        
        return totalHP;
    }
}
