package pippin;
import java.util.*;

public class MachineModel {
	public final Map<Integer, Instruction> INSTRUCTION_MAP = new TreeMap<>();
	private Registers cpu = new Registers();
	private Memory memory = new Memory();
	
	public class Registers{
		private int accumulator;
		private int programCounter;
	}


	public MachineModel(){
		//INSTRUCTION_MAP entry for "ADD"
		INSTRUCTION_MAP.put(0x3,(arg,level) -> {
			if(level< 0 || level > 2){
				throw new IllegalArgumentException("Illegal indirection level in ADD instruction");
			}
			if(level > 0){
				INSTRUCTION_MAP.get(0x3).execute(memory.getData(arg), level-1);
			}else{
				cpu.accumulator += arg;
				cpu.programCounter ++;
			}
		});

		//INSTRUCTION_MAP entry for "NOP"
		INSTRUCTION_MAP.put(0x0, (arg, level) ->{
			if(level == 0){
				cpu.programCounter ++;
			}else{
				throw new IllegalArgumentException("Illegal indirection level in NOP instruction");
			}
		});

		//INSTRUCTION_MAP entry for "LOD"
		INSTRUCTION_MAP.put(0x1, (arg, level) -> {
			 if(level< 0 || level > 2){
				throw new IllegalArgumentException("Illegal indirection level in LOD instruction");
			}
			if(level > 0){
				INSTRUCTION_MAP.get(0x1).execute(memory.getData(arg), level-1);
			}else{
				cpu.accumulator = arg;
				cpu.programCounter ++;
			}
		});

		//INSTUCTION_MAP entry for "STO"
		INSTRUCTION_MAP.put(0x2, (arg, level) ->{
			if(level < 1 || level > 2){
				throw new IllegalArgumentException("Illegal indirection level in STO instruction");
			}
			if(level == 1){
				memory.setData(arg, cpu.accumulator);
				cpu.programCounter ++;
			}else{
				INSTRUCTION_MAP.get(0x2).execute(memory.getData(arg), level-1);
			}
		});
		
		//INSTRUCTION_MAP entry for "CMPZ"
		INSTRUCTION_MAP.put(0x9, (arg, level) ->{
			if(level == 1){
				if(memory.getData(arg) == 0){
					cpu.accumulator = 1;
					cpu.programCounter ++;
				}else{
					cpu.accumulator = 0;	
					cpu.programCounter ++;
				}
			}else{
				throw new IllegalArgumentException("Illegal indirection level in CMPZ instruction");
			}
		});
		
		//INSTRUCTION_MAP entry for "CMPL"
		INSTRUCTION_MAP.put(0xA, (arg, level) ->{
			if(level == 1){
				if(memory.getData(arg) < 0){
					cpu.accumulator = 1;
					cpu.programCounter ++;
				}else{
					cpu.accumulator = 0;
					cpu.programCounter ++;
				}
			}else{
				throw new IllegalArgumentException("Illegal indirection level in CMPL instruction");
			}
		});
		
		
	}			

	public int getData(int index){
		return memory.getData(index);
	}

	public void setData(int index, int value){
		memory.setData(index, value);
	}

	public Instruction get(Object key){
		return INSTRUCTION_MAP.get(key);
	}

	int[]getData(){
		return memory.getData();
	}

	int getProgramCounter(){
		return cpu.programCounter;
	}

	int getAccumulator(){
		return cpu.accumulator;
	}

	void setAccumulator(int i){
		cpu.accumulator = i;
	}
}
