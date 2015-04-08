package pippin;

public class Runner {

    public static void main(String[] args) {
        MachineModel model = new MachineModel();
        class Triple {
            int opcode;
            int arg;
            int level;
            public Triple(int opcode, int arg, int level) {
                super();
                this.opcode = opcode;
                this.arg = arg;
                this.level = level;
            }
            public String toString() {
                return opcode + " " + arg + " " + level;
            }
        }
        Triple[] programCode = {
                new Triple(1, 0, 1),
                new Triple(3, 1, 1),
                new Triple(2, 2, 1),
        };

        model.setData(0, 8);
        model.setData(1, 7);

        for(Triple t : programCode) {
            System.out.println(t);
            Instruction instr = model.INSTRUCTION_MAP.get(t.opcode);
            instr.execute(t.arg, t.level);
            System.out.println("memory[0] = " + model.getData(0));
            System.out.println("memory[1] = " + model.getData(1));
            System.out.println("memory[2] = " + model.getData(2));
        }
    }
}
