package gametest;


import .*;
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
    protected Entity self;
    protected int position;
    
    PartyMember(Entity self, int position)
    {
        this.self = self;
        this.position = position;
    }
}
