/*
 * InstructionMoving.java
 */

package com.sierra.agi.logic.interpret.instruction;

/**
 * Base Class for Instruction that have the ability to control the logics flows.
 *
 * @author  Dr. Z
 * @version 0.00.00.01
 */
public abstract class InstructionMoving extends Instruction
{
    /**
     * Creates new Moving Instruction. Does absolutly nothing. Serve only has a
     * formal declaration.
     */
    protected InstructionMoving()
    {
    }
    
    /**
     * Retreive the Address which is referenced by this instruction.
     *
     * @return Returns the Address referenced by this instruction.
     */
    public abstract int getAddress();

    /**
     * Determine Destination Instruction Number
     * 
     * @param instructionNum Instruction number of the branching instruction.
     * @param instructionSizes Array containing the size in bytes of each Instruction in the Logic.
     */
    public int getDestination(int instructionNum, int[] instructionSizes)
    {
        int gotoFactor = getAddress() + instructionSizes[instructionNum];

        if (gotoFactor > 0)
        {
            // If the jump is forward, scan forward from the size array.
            while (gotoFactor > 0)
            {
                gotoFactor -= instructionSizes[instructionNum++];
            }
        }
        else
        {
            // If the jump is backward, scan back through the size array.
            while (gotoFactor < 0)
            {
                gotoFactor += instructionSizes[--instructionNum];
            }
        }
        
        return instructionNum;
    }
}