package sml;

/**
 * Created by chris on 25/01/15.
 */
public class OutInstruction extends Instruction {

    private int register;

    public OutInstruction(String label, String opcode) {
        super(label, opcode);
    }

    public OutInstruction(String label, int register) {
        this(label, "out");
        this.register = register;

    }

    @Override
    public void execute(Machine m) {
        System.out.println("Output: " + m.getRegisters().getRegister(register));
    }

    @Override
    public String toString() {
        return super.toString() + " register " + register;
    }
}
