package gametest;


import gametest.Entity;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author DELL
 */
public class PartyMember {
    protected Entity entity;
    protected int position;
    
    PartyMember(Entity entity, int position)
    {
        this.entity= entity;
        this.position = position;
    }
}
