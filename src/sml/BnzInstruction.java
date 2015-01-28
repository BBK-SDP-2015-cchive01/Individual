package sml;

/**
 * Created by chris on 25/01/15.
 */
public class BnzInstruction extends Instruction {

    private int op1;
    private String op2;

    public BnzInstruction(String label, String op) {
        super(label, op);
    }

    public BnzInstruction(String label, int register, String jumpToLabel) {
        this(label, "bnz");
        this.op1 = register;
        this.op2 = jumpToLabel;
    }

    @Override
    public void execute(Machine m) {
        int value1 = m.getRegisters().getRegister(op1);
        if (value1 != 0) {
            int labelIndex = m.getLabels().indexOf(op2);
            m.setPc(labelIndex);
        }

    }

    @Override
    public String toString() {
        //TODO
        return super.toString() + " " + op1 +  " jump to " + op2;
    }
}
