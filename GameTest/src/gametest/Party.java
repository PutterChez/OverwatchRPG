/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gametest;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author DELL
 */
public class Party {
    protected List<PartyMember> memberList;
    
    Party()
    {
        ArrayList memberList = new ArrayList(){};
    }
    
    public void addMember(Entity e, int position)
    {
        PartyMember p = new PartyMember(e, position);
        memberList.add(p);
    }
    
    public Entity searchMember(int position)
    {
        return memberList.get(position).entity;
    }
    
    public void deleteMember(int position)
    {
        memberList.remove(position);
    }
}
